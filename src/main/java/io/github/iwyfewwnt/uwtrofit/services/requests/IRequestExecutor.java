/*
 * Copyright 2023 u004
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.iwyfewwnt.uwtrofit.services.requests;

import io.vavr.control.Try;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.concurrent.CompletableFuture;

/**
 * A request executor interface.
 *
 * <p>Defines methods for executing requests.
 *
 * @param <T>	request type
 * @param <R>	response type
 */
public interface IRequestExecutor<T extends IRequest, R> {

	/**
	 * Execute retrofit service method.
	 *
	 * @param request	request object
	 * @return			unwrapped response that wrapped in {@link Call}
	 */
	Call<R> call(T request);

	/**
	 * Asynchronously execute retrofit service method.
	 *
	 * @param request	request object
	 * @return			unwrapped response that wrapped in {@link CompletableFuture}
	 */
	default CompletableFuture<R> enqueue(T request) {
		CompletableFuture<R> completableFuture = new CompletableFuture<>();

		this.call(request)
				.enqueue(new Callback<R>() {
					@Override
					public void onResponse(Call<R> call, Response<R> response) {
						unwrap(response)
								.onSuccess(completableFuture::complete)
								.onFailure(completableFuture::completeExceptionally);
					}

					@Override
					public void onFailure(Call<R> call, Throwable t) {
						completableFuture.completeExceptionally(t);
					}
				});

		return completableFuture;
	}

	/**
	 * Synchronously execute retrofit service method.
	 *
	 * @param request	request object
	 * @return			unwrapped response that wrapped in {@link Try}
	 */
	default Try<R> execute(T request) {
		return Try.of(() -> this.call(request))
				.flatMap(call -> Try.of(call::execute))
				.flatMap(IRequestExecutor::unwrap);
	}

	/**
	 * Asynchronously execute retrofit service method.
	 *
	 * <p>Wraps response using <i>Spring Reactor</i>.
	 *
	 * @param request	request object
	 * @return			unwrapped response that wrapped in {@link Mono}
	 */
	default Mono<R> rxEnqueue(T request) {
		Sinks.Many<R> sink = Sinks.many()
				.multicast()
				.onBackpressureBuffer();

		this.enqueue(request)
				.whenComplete((result, throwable) -> {
					if (throwable != null) {
						sink.tryEmitError(throwable);
					}

					if (result != null) {
						sink.tryEmitNext(result);
					}

					sink.tryEmitComplete();
				});

		return sink.asFlux()
				.single();
	}

	/**
	 * Synchronously execute retrofit service method.
	 *
	 * <p>Wraps response using <i>Spring Reactor</i>.
	 *
	 * @param request	request object
	 * @return			unwrapped response that wrapped in {@link Mono}
	 */
	default Mono<R> rxExecute(T request) {
		Try<R> resultTry = this.execute(request);

		if (resultTry.isFailure()) {
			return Mono.error(resultTry.getCause());
		}

		return Mono.justOrEmpty(resultTry.getOrNull());
	}

	/**
	 * Unwrap response object.
	 *
	 * <p>Checks for response and body nullability, and the code of supposed.
	 *
	 * @param response		response object
	 * @param <R>			response type
	 * @return				unwrapped response that wrapped in {@link Try}
	 */
	static <R> Try<R> unwrap(Response<R> response) {
		if (response == null) {
			return Try.failure(new NullPointerException("Response mustn't be <null>"));
		}

		if (!response.isSuccessful()) {
			return Try.failure(new IllegalStateException("Response code <" + response.code() + ">"));
		}

		R body = response.body();

		if (body == null) {
			return Try.failure(new NullPointerException("Response body mustn't be <null>"));
		}

		return Try.success(body);
	}
}
