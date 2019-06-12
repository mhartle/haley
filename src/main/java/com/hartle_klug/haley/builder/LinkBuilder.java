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

import java.util.LinkedHashMap;
import java.util.Map;

import com.hartle_klug.haley.model.Link;

/**
 * Builds HAL links
 * 
 * @author Michael Hartle <mhartle@hartle-klug.com>
 *
 */
public class LinkBuilder {
	private Map<String, Object> properties;
	
	private LinkBuilder() {
	}
	
	public static LinkBuilder use(String href) {
		LinkBuilder linkBuilder = new LinkBuilder();
		linkBuilder.properties = new LinkedHashMap<>();
		linkBuilder.properties.put(Link.PROPERTY_HREF, href);
		return linkBuilder;
	}
	
	public LinkBuilder templated(boolean templated) {
		this.properties.put(Link.PROPERTY_TEMPLATED, templated);
		return this;
	}
	
	public LinkBuilder type(String type) {
		this.properties.put(Link.PROPERTY_TYPE, type);
		return this;
	}
	
	public LinkBuilder deprecation(String deprecation) {
		this.properties.put(Link.PROPERTY_DEPRECATION, deprecation);
		return this;
	}
	
	public LinkBuilder name(String name) {
		this.properties.put(Link.PROPERTY_NAME, name);
		return this;
	}
	
	public LinkBuilder profile(String profile) {
		this.properties.put(Link.PROPERTY_PROFILE, profile);
		return this;
	}
	
	public LinkBuilder title(String title) {
		this.properties.put(Link.PROPERTY_TITLE, title);
		return this;
	}
	
	public LinkBuilder hreflang(String hreflang) {
		this.properties.put(Link.PROPERTY_HREFLANG, hreflang);
		return this;
	}
	
	public LinkBuilder properties(Map<String, Object> properties) {
		if (properties != null) {
			this.properties.putAll(properties);
		}
		
		return this;
	}
	
	public LinkBuilder property(String key, Object value) {
		this.properties.put(key, value);
		return this;
	}
	
	public Link build() {
		final Link result = new Link(properties);
		return result;
	}
}
