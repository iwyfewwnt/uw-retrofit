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

package io.github.iwyfewwnt.uwretrofit.services.requests.impl;

import io.github.iwyfewwnt.uwretrofit.services.requests.IRequest;

/**
 * A base class for request managers hierarchy.
 *
 * <p>Extends from {@link AbstractRequestManager}.
 *
 * @param <T>	manager type
 * @param <U>	request type
 * @param <R>	response type
 */
@SuppressWarnings("unused")
public abstract class BaseRequestManager<T extends BaseRequestManager<T, U, R>, U extends IRequest, R> extends AbstractRequestManager<U, R> {

	/**
	 * This instance as a generic type.
	 */
	protected final T asT;

	/**
	 * Initialize a {@link BaseRequestManager} instance.
	 */
	@SuppressWarnings("unchecked")
	protected BaseRequestManager() {
		this.asT = (T) this;
	}
}
