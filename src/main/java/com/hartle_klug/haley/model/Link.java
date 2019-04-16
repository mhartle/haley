package com.hartle_klug.haley.model;

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

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a HAL link
 * 
 * @author Michael Hartle <mhartle@hartle-klug.com>
 *
 */
public class Link {
	public static final String PROPERTY_DEPRECATION = "deprecation";
	public static final String PROPERTY_HREF = "href";
	public static final String PROPERTY_HREFLANG = "hreflang";
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_PROFILE = "profile";
	public static final String PROPERTY_TEMPLATED = "templated";
	public static final String PROPERTY_TITLE = "title";
	public static final String PROPERTY_TYPE = "type";
	
	private final Map<String, Object> properties;
	
	public Link(Map<String, Object> properties) {
		this.properties = new HashMap<>(properties);
	}
	
	@JsonAnyGetter
	public Map<String, Object> getProperties() {
		return this.properties;
	}
	
	@JsonIgnore
	public String getName() {
		return (String) this.properties.get(PROPERTY_NAME);
	}

	@JsonIgnore
	public String getHref() {
		return (String) this.properties.get(PROPERTY_HREF);
	}
}
