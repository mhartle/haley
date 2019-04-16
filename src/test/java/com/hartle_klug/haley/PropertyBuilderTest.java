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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hartle_klug.haley.builder.PropertyBuilder;
import com.hartle_klug.haley.model.Property;

public class PropertyBuilderTest {
	final static String PROPERTY_NAME = "name";
	final static Object PROPERTY_VALUE = "value";
	
	@Test
	public void testProperty() {
		final Property property = PropertyBuilder.use(PROPERTY_NAME, PROPERTY_VALUE).build();
		assertEquals(PROPERTY_NAME, property.getName());
		assertEquals(PROPERTY_VALUE, property.getValue());
	}
}
