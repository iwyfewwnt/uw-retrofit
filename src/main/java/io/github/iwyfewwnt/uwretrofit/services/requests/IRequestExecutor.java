/*
 * Copyright 2023 iwyfewwnt
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

package io.github.iwyfewwnt.uwretrofit.services.requests;

import io.github.iwyfewwnt.uwutils.UwArray;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * A request executor interface.
 *
 * <p>Defines methods for executing requests.
 *
 * @param <T>	request type
 * @param <R>	response type
 */
@SuppressWarnings("unused")
public interface IRequestExecutor<T extends IRequest, R> {

	/**
	 * Execute retrofit service method.
	 *
	 * @param request	request object
	 * @return			response body that wrapped in {@link Call}
	 */
	Call<R> call(T request);

	/**
	 * Asynchronously execute retrofit service method.
	 *
	 * @param request	request object
	 * @return			response body that wrapped in {@link CompletableFuture}
	 */
	default CompletableFuture<R> enqueue(T request) {
		CompletableFuture<R> future = new CompletableFuture<>();

		Call<R> call = this.call(request);

		if (call == null) {
			future.completeExceptionally(new NullPointerException(
					"Call<?> mustn't be <null>"));
			return future;
		}

		call.enqueue(new Callback<R>() {
			@Override
			public void onResponse(Call<R> call, Response<R> response) {
				Throwable[] throwables = new Throwable[1];

				R body = unwrap(response, throwables);

				Throwable throwable = throwables[0];

				if (throwable != null) {
					future.completeExceptionally(throwable);
					return;
				}

				future.complete(body);
			}

			@Override
			public void onFailure(Call<R> call, Throwable throwable) {
				future.completeExceptionally(throwable);
			}
		});

		return future;
	}

	/**
	 * Synchronously execute retrofit service method.
	 *
	 * @param request		request object to pass in call
	 * @param throwables	array to fill thrown exception with
	 * @return				response body or {@code null}
	 */
	default R execute(T request, Throwable[] throwables) {
		try {
			Call<R> call = this.call(request);

			if (call == null) {
				throw new NullPointerException("Call<?> mustn't be <null>");
			}

			return unwrap(call.execute(), throwables);
		} catch (IOException | RuntimeException e) {
			UwArray.propagate(e, throwables);
		}

		return null;
	}

	/**
	 * Synchronously execute retrofit service method.
	 *
	 * <p>Wraps {@link IRequestExecutor#execute(IRequest, Throwable[])}
	 * w/ {@code null} as the throwables parameter.
	 *
	 * @param request	request object to pass in call
	 * @return			response body or {@code null}
	 */
	default R execute(T request) {
		return execute(request, null);
	}

	/**
	 * Get response body from the provided response object.
	 *
	 * <p>Checks for response and body nullability, and the code of supposed.
	 *
	 * @param response		response object to unwrap
	 * @param throwables 	array to fill thrown exception with
	 * @param <R>			response body type
	 * @return				response body or {@code null}
	 */
	static <R> R unwrap(Response<R> response, Throwable[] throwables) {
		try {
			if (response == null) {
				throw new NullPointerException("Response mustn't be <null>");
			}

			if (!response.isSuccessful()) {
				throw new IllegalStateException("Response code <" + response.code() + ">");
			}

			R body = response.body();

			if (body == null) {
				throw new NullPointerException("Response body mustn't be <null>");
			}

			return body;
		} catch (NullPointerException | IllegalStateException e) {
			UwArray.propagate(e, throwables);
		}

		return null;
	}

	/**
	 * Get response body from the provided response object.
	 *
	 * <p>Checks for response and body nullability, and the code of supposed.
	 *
	 * @param response	response object to unwrap
	 * @param <R>		response body type
	 * @return			response body or {@code null}
	 */
	static <R> R unwrap(Response<R> response) {
		return unwrap(response, null);
	}
}
