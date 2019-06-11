package com.hartle_klug.haley.model;

import java.util.LinkedHashMap;
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
		this.properties = new LinkedHashMap<>(properties);
	}
	
	@JsonAnyGetter
	public Map<String, Object> getProperties() {
		return this.properties;
	}
	
	@JsonIgnore
	public String getDeprecation() {
		return (String) this.properties.get(PROPERTY_DEPRECATION);
	}
	
	@JsonIgnore
	public String getHrefLang() {
		return (String) this.properties.get(PROPERTY_HREFLANG);
	}

	@JsonIgnore
	public String getHref() {
		return (String) this.properties.get(PROPERTY_HREF);
	}

	@JsonIgnore
	public String getName() {
		return (String) this.properties.get(PROPERTY_NAME);
	}

	@JsonIgnore
	public String getProfile() {
		return (String) this.properties.get(PROPERTY_PROFILE);
	}

	@JsonIgnore
	public String isTemplated() {
		return (String) this.properties.get(PROPERTY_TEMPLATED);
	}

	@JsonIgnore
	public String getTitle() {
		return (String) this.properties.get(PROPERTY_TITLE);
	}

	@JsonIgnore
	public String getType() {
		return (String) this.properties.get(PROPERTY_TYPE);
	}
}
