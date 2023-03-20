/*
 * Copyright 2022 u004
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

package io.github.u004.retrofitext.services.requests;

import io.vavr.control.Try;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * A request manager interface.
 *
 * <p>Composer of an {@link IRequestBuilder} and {@link IRequestExecutor}.
 *
 * @param <T>	request type
 * @param <R>	response type
 */
@SuppressWarnings("unused")
public interface IRequestManager<T extends IRequest, R> extends IRequestBuilder<T>, IRequestExecutor<T, R> {

	/**
	 * Wrap {@link IRequestExecutor#enqueue(IRequest)}.
	 */
	default CompletableFuture<R> enqueue() {
		return this.enqueue(this.build());
	}

	/**
	 * Wrap {@link IRequestExecutor#execute(IRequest)}.
	 */
	default Try<R> execute() {
		return this.execute(this.build());
	}

	/**
	 * Wrap {@link IRequestExecutor#rxEnqueue(IRequest)}..
	 */
	default Mono<R> rxEnqueue() {
		return this.rxEnqueue(this.build());
	}

	/**
	 * Wrap {@link IRequestExecutor#rxExecute(IRequest)}.
	 */
	default Mono<R> rxExecute() {
		return this.rxExecute(this.build());
	}
}
