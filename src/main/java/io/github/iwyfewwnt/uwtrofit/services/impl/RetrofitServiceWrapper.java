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

package io.github.iwyfewwnt.uwtrofit.services.impl;

import io.github.iwyfewwnt.uwtrofit.services.IServiceWrapper;
import retrofit2.Retrofit;

/**
 * A retrofit service wrapper.
 *
 * <p>{@code RetrofitServiceWrapper} is the class that
 * wraps retrofit service and extends it to base classes.
 *
 * @param <T>	retrofit service type
 */
@SuppressWarnings("unused")
public class RetrofitServiceWrapper<T> implements IServiceWrapper<T> {

	/**
	 * Retrofit service instance.
	 */
	protected final T service;

	/**
	 * Initialize a {@code RetrofitServiceWrapper} instance.
	 *
	 * @param retrofit		retrofit instance
	 * @param clazz			retrofit service class
	 */
	protected RetrofitServiceWrapper(Retrofit retrofit, Class<T> clazz) {
		this.service = retrofit.create(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final T getService() {
		return this.service;
	}
}
