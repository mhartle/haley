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

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hartle_klug.haley.jackson.ResourceDeserializer;
import com.hartle_klug.haley.jackson.ResourceSerializer;

/**
 * Represents a HAL resource
 * 
 * @author Michael Hartle <mhartle@hartle-klug.com>
 *
 */
@JsonSerialize(using = ResourceSerializer.class)
@JsonDeserialize(using = ResourceDeserializer.class)
public class Resource {
	protected final Map<String, Object> representation;
	protected final Map<String, Object> links;
	protected final Map<String, Object> embeddeds;

	public Resource(Map<String, Object> representation, Map<String, Object> links, Map<String, Object> embeddeds) {
		this.representation = representation;
		this.links = links;
		this.embeddeds = embeddeds;
	}
	
	public Map<String, Object> getRepresentation() {
		return representation;
	}
	
	public Map<String, Object> getLinks() {
		return links;
	}
	
	public Map<String, Object> getEmbeddeds() {
		return embeddeds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((embeddeds == null) ? 0 : embeddeds.hashCode());
		result = prime * result + ((links == null) ? 0 : links.hashCode());
		result = prime * result + ((representation == null) ? 0 : representation.hashCode());
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
		Resource other = (Resource) obj;
		if (embeddeds == null) {
			if (other.embeddeds != null)
				return false;
		} else if (!embeddeds.equals(other.embeddeds))
			return false;
		if (links == null) {
			if (other.links != null)
				return false;
		} else if (!links.equals(other.links))
			return false;
		if (representation == null) {
			if (other.representation != null)
				return false;
		} else if (!representation.equals(other.representation))
			return false;
		return true;
	}
}
