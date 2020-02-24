package com.hartle_klug.haley.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hartle_klug.haley.jackson.LinkDeserializer;

/**
 * Represents a HAL link
 * 
 * @author Michael Hartle <mhartle@hartle-klug.com>
 *
 */
@JsonDeserialize(using = LinkDeserializer.class)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}
}
