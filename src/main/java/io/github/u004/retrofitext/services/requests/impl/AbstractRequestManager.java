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

package io.github.u004.retrofitext.services.requests.impl;

import io.github.u004.retrofitext.services.requests.IRequest;
import io.github.u004.retrofitext.services.requests.IRequestManager;
import io.vavr.control.Try;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * An {@link IRequestManager} implementation.
 *
 * @param <T>	request type
 * @param <R>	response type
 */
@SuppressWarnings("unused")
public abstract class AbstractRequestManager<T extends IRequest, R> implements IRequestManager<T, R> {

	/**
	 * Initialize an {@code AbstractRequestManager} instance.
	 */
	protected AbstractRequestManager() {
	}

	/**
	 * Wrap {@link IRequestManager#enqueue()} and make it final.
	 */
	@Override
	public final CompletableFuture<R> enqueue() {
		return IRequestManager.super.enqueue();
	}

	/**
	 * Wrap {@link IRequestManager#execute()} and make it final.
	 */
	@Override
	public final Try<R> execute() {
		return IRequestManager.super.execute();
	}

	/**
	 * Wrap {@link IRequestManager#rxEnqueue()} and make it final.
	 */
	@Override
	public final Mono<R> rxEnqueue() {
		return IRequestManager.super.rxEnqueue();
	}

	/**
	 * Wrap {@link IRequestManager#rxExecute()} and make it final.
	 */
	@Override
	public final Mono<R> rxExecute() {
		return IRequestManager.super.rxExecute();
	}

	/**
	 * Wrap {@link IRequestManager#enqueue(IRequest)} and make it final.
	 */
	@Override
	public final CompletableFuture<R> enqueue(T request) {
		return IRequestManager.super.enqueue(request);
	}

	/**
	 * Wrap {@link IRequestManager#execute(IRequest)} and make it final.
	 */
	@Override
	public final Try<R> execute(T request) {
		return IRequestManager.super.execute(request);
	}

	/**
	 * Wrap {@link IRequestManager#rxEnqueue(IRequest)} and make it final.
	 */
	@Override
	public final Mono<R> rxEnqueue(T request) {
		return IRequestManager.super.rxEnqueue(request);
	}

	/**
	 * Wrap {@link IRequestManager#rxExecute(IRequest)} and make it final.
	 */
	@Override
	public final Mono<R> rxExecute(T request) {
		return IRequestManager.super.rxExecute(request);
	}
}
