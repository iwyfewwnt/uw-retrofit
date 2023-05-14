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

package io.github.iwyfewwnt.uwretrofit.converters;

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
	 * A {@link Converter} cache.
	 */
	private final Map<String, Converter<?, String>> converters;

	/**
	 * Initialize a {@link QueryConverterFactory} instance.
	 */
	private QueryConverterFactory() {
		this.converters = new ConcurrentHashMap<>();
	}

	/**
	 * Factory create method to match retrofit library style.
	 *
	 * <p>Wraps {@link #QueryConverterFactory()}.
	 *
	 * @return	new {@link QueryConverterFactory} instance
	 */
	public static QueryConverterFactory create() {
		return new QueryConverterFactory();
	}

	/**
	 * Register the provided string converter.
	 *
	 * @param type			query parameter type
	 * @param converter		string converter to register
	 * @return				this {@link QueryConverterFactory} instance
	 */
	public QueryConverterFactory registerStringConverter(Type type, Converter<?, String> converter) {
		if (type != null && converter != null) {
			this.converters.putIfAbsent(type.getTypeName(), converter);
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
