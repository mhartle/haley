package com.hartle_klug.haley;

/*
 * Haley HAL Resource Builder
 *
 * Copyright 2018 Hartle & Klug Consulting GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import com.hartle_klug.haley.builder.PropertyBuilder;
import com.hartle_klug.haley.builder.RepresentationBuilder;

public class RepresentationBuilderTest {
	@Test
	public void testRepresentationFromObject() {
		try {
			final Map<String, Object> representation = RepresentationBuilder.use(new Thing("example")).build();
			
			assertThat(Stream.of("name").collect(Collectors.toSet()), equalTo(representation.keySet()));
			assertThat(Stream.of("example").collect(Collectors.toList()), equalTo(representation.values().stream().collect(Collectors.toList())));
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | IntrospectionException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testRepresentationFromMap() {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("name", "value");
			final Map<String, Object> representation = RepresentationBuilder.use(map).build();

			assertThat(Stream.of("name").collect(Collectors.toSet()), equalTo(representation.keySet()));
			assertThat(Stream.of("value").collect(Collectors.toList()), equalTo(representation.values().stream().collect(Collectors.toList())));
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | IntrospectionException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testRepresentationProperty() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.property(PropertyBuilder.use("name",  "value").build())
				.build();
		
		assertThat(Stream.of("name").collect(Collectors.toSet()), equalTo(representation.keySet()));
		assertThat(Stream.of("value").collect(Collectors.toList()), equalTo(representation.values().stream().collect(Collectors.toList())));
	}
	
	@Test
	public void testRepresentationProperties() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.property(
						PropertyBuilder.use("name",  "value").build(),
						PropertyBuilder.use("name2",  "value2").build())
				.build();
		
		assertThat(Stream.of("name", "name2").collect(Collectors.toSet()), equalTo(representation.keySet()));
		assertThat(Stream.of("value", "value2").collect(Collectors.toList()), equalTo(representation.values().stream().collect(Collectors.toList())));
	}
	
	@Test
	public void testRepresentationPropertiesList() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.property(
						Arrays.asList(
							PropertyBuilder.use("name",  "value").build(),
							PropertyBuilder.use("name2",  "value2").build()))
				.build();
		
		assertThat(Stream.of("name", "name2").collect(Collectors.toSet()), equalTo(representation.keySet()));
		assertThat(Stream.of("value", "value2").collect(Collectors.toList()), equalTo(representation.values().stream().collect(Collectors.toList())));
	}
	
	public static class Thing {
		private String name;
		
		public Thing(final String name) {
			this.name = name;
		}
				
		public String getName() {
			return name;
		}
	}
}
