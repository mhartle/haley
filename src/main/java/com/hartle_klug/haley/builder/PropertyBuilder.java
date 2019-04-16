package com.hartle_klug.haley.builder;

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

import com.hartle_klug.haley.model.Property;

/**
 * Builds HAL resource representation properties
 * 
 * @author Michael Hartle <mhartle@hartle-klug.com>
 *
 */
public class PropertyBuilder {
	private String name;
	private Object value;
	
	protected PropertyBuilder() {
	}
	
	public static PropertyBuilder use(final String name, final Object value) {
		final PropertyBuilder result = new PropertyBuilder();
		result.name = name;
		result.value = value;
		
		return result;
	}

	public Property build() {
		final Property result = new Property(name, value);
		
		return result;
	}
}
