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

package io.github.u004.retrofitext.services;

import retrofit2.Retrofit;

/**
 * A service interface implementation wrapper.
 *
 * <p>{@code ServiceWrapper} is the class that
 * wraps retrofit service and extends it to base
 * classes.
 *
 * @param <T>	retrofit service type
 */
@SuppressWarnings("unused")
public class ServiceWrapper<T> {

	/**
	 * Retrofit service implementation instance.
	 */
	protected final T service;

	/**
	 * Initialize a {@code ServiceWrapper} instance.
	 *
	 * @param retrofit		{@link Retrofit} instance
	 * @param clazz			retrofit service class type
	 */
	protected ServiceWrapper(Retrofit retrofit, Class<T> clazz) {
		this.service = retrofit.create(clazz);
	}
}
