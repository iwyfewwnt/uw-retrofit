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

package io.github.u004.retrofitext.converters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * A query converter factory.
 *
 * <p>Same as {@link QueryConverterFactory} but uses {@link Gson} object
 * to make conversions.
 */
@SuppressWarnings("unused")
public final class GsonQueryConverterFactory extends Converter.Factory {

	/**
	 * {@link Gson} instance.
	 */
	private final Gson gson;

	/**
	 * Initialize a {@code GsonQueryConverterFactory} instance.
	 *
	 * @param gson		{@code Gson} instance
	 */
	private GsonQueryConverterFactory(Gson gson) {
		this.gson = gson;
	}

	/**
	 * Factory create method to match retrofit library style.
	 *
	 * @param gson		{@code Gson} instance
	 * @return			{@code GsonQueryConverterFactory} instance
	 */
	public static GsonQueryConverterFactory create(Gson gson) {
		return new GsonQueryConverterFactory(gson);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		return new GsonStringConverter<>(this.gson.getAdapter(TypeToken.get(type)));
	}

	private static final class GsonStringConverter<T> implements Converter<T, String> {
		private final TypeAdapter<T> typeAdapter;

		public GsonStringConverter(TypeAdapter<T> typeAdapter) {
			this.typeAdapter = typeAdapter;
		}

		@Override
		public String convert(T value) {
			String str = this.typeAdapter.toJson(value);

			if (str.startsWith("\"") && str.endsWith("\"")) {
				str = str.substring(1, str.length() - 1);
			}

			return str;
		}
	}
}
