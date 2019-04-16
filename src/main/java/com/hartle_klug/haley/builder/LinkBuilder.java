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

import java.util.HashMap;
import java.util.Map;

import com.hartle_klug.haley.model.Link;

/**
 * Builds HAL links
 * 
 * @author Michael Hartle <mhartle@hartle-klug.com>
 *
 */
public class LinkBuilder {
	private String href;
	private Boolean templated;
	private String type;
	private String deprecation;
	private String name;
	private String profile;
	private String title;
	private String hreflang;
	
	private LinkBuilder() {
	}
	
	public static LinkBuilder use(String href) {
		LinkBuilder linkBuilder = new LinkBuilder();
		linkBuilder.href = href;
		return linkBuilder;
	}
	
	public LinkBuilder templated(boolean templated) {
		this.templated = templated;
		return this;
	}
	
	public LinkBuilder type(String type) {
		this.type = type;
		return this;
	}
	
	public LinkBuilder deprecation(String deprecation) {
		this.deprecation = deprecation;
		return this;
	}
	
	public LinkBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public LinkBuilder profile(String profile) {
		this.profile = profile;
		return this;
	}
	
	public LinkBuilder title(String title) {
		this.title = title;
		return this;
	}
	
	public LinkBuilder hreflang(String hreflang) {
		this.hreflang = hreflang;
		return this;
	}
	
	public Link build() {
		final Map<String, Object> properties = new HashMap<>();
		
		if (href != null) {
			properties.put(Link.PROPERTY_HREF, href);
		}
		
		if (templated != null) {
			properties.put(Link.PROPERTY_TEMPLATED, templated);
		}
		
		if (type != null) {
			properties.put(Link.PROPERTY_TYPE, type);
		}
		
		if (deprecation != null) {
			properties.put(Link.PROPERTY_DEPRECATION, deprecation);
		}
		
		if (name != null) {
			properties.put(Link.PROPERTY_NAME, name);
		}
		
		if (profile != null) {
			properties.put(Link.PROPERTY_PROFILE, profile);
		}
		
		if (title != null) {
			properties.put(Link.PROPERTY_TITLE, title);
		}
		
		if (hreflang != null) {
			properties.put(Link.PROPERTY_HREFLANG, hreflang);
		}
		
		final Link result = new Link(properties);
		return result;
	}
}
