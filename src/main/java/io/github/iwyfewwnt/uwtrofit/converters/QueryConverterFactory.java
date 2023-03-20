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

package io.github.iwyfewwnt.uwtrofit.converters;

import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A query converter factory.
 *
 * <p>Can be used to convert custom query parameters to string.
 * Extends from {@link Converter.Factory}.
 */
@SuppressWarnings("unused")
public final class QueryConverterFactory extends Converter.Factory {

	/**
	 * {@link Converter} cache.
	 */
	private final Map<String, Converter<?, String>> converters;

	/**
	 * Initialize a {@code QueryConverterFactory} instance.
	 */
	private QueryConverterFactory() {
		this.converters = new ConcurrentHashMap<>();
	}

	/**
	 * Factory create method to match retrofit library style.
	 *
	 * <p>Wraps {@code QueryConverterFactory} default constructor.
	 *
	 * @return	{@code QueryConverterFactory} instance
	 */
	public static QueryConverterFactory create() {
		return new QueryConverterFactory();
	}

	/**
	 * Register string converter.
	 *
	 * @param clazz			query parameter class type
	 * @param converter		string converter
	 * @param <T>			query parameter type
	 * @return				this {@code QueryConverterFactory} instance
	 */
	public <T> QueryConverterFactory registerStringConverter(Class<T> clazz, Converter<T, String> converter) {
		if (clazz != null && converter != null) {
			this.converters.putIfAbsent(clazz.getTypeName(), converter);
		}

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		return this.converters.get(type.getTypeName());
	}
}
