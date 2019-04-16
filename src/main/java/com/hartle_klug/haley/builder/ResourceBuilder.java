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

import java.util.List;
import java.util.Map;

import com.hartle_klug.haley.model.Link;
import com.hartle_klug.haley.model.Resource;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Builds HAL resources
 * 
 * @author Michael Hartle <mhartle@hartle-klug.com>
 *
 */
public class ResourceBuilder {
	protected Map<String, Object> representation;
	protected Map<String, Link> curies;
	protected Map<String, Object> links;
	protected Map<String, Object> embedded;
	
	private ResourceBuilder() {
		this.representation = new LinkedHashMap<>();
		this.curies = new LinkedHashMap<>();
		this.links = new LinkedHashMap<>();
		this.embedded = new LinkedHashMap<>();
	}

	public static ResourceBuilder use(Map<String, Object> representation) {
		final ResourceBuilder resourceBuilder = new ResourceBuilder();
		resourceBuilder.representation = representation;
		return resourceBuilder;
	}
	
	public ResourceBuilder curie(Link... curies) {
		for(Link curie : curies) {
			// Ensure CURIE has a name
			if (curie.getName() == null) {
				throw new IllegalArgumentException("Use of unnamed CURIE");
			}
			
			this.curies.put(curie.getName(), curie);
		}
		
		return this;
	}
	
	public ResourceBuilder curie(List<Link> curies) {
		return curie(curies.toArray(new Link[0]));
	}
	
	public ResourceBuilder linkSelf(Link link) {
		return link("self", link);
	}
	
	@SuppressWarnings("unchecked")
	public ResourceBuilder link(String reference, Link... links) {
		// Ensure prefix is registered, if any
		ensureCuriePrefixRegistration(reference);
		
		Object previous = this.links.get(reference);
		if (previous instanceof List<?>) {
			List<Link> previousList = (List<Link>) previous;
			previousList.addAll(Arrays.asList(links));
		} else if (previous != null) {
			List<Link> previousList = new LinkedList<>();
			previousList.add((Link)previous);
			previousList.addAll(Arrays.asList(links));
			this.links.put(reference, previousList);
		} else {
			if (links.length > 1) {
				this.links.put(reference, Arrays.asList(links));
			} else if (links.length == 1 && links[0] != null) {
				this.links.put(reference, links[0]);
			}
		}
		
		return this;
	}
	
	public ResourceBuilder link(String reference, List<Link> links) {
		return link(reference, links.toArray(new Link[0]));
	}	
	
	@SuppressWarnings("unchecked")
	public ResourceBuilder embedd(String reference, Resource... embedded) {
		// Ensure prefix is registered, if any
		ensureCuriePrefixRegistration(reference);

		Object previous = this.embedded.get(reference);
		if (previous instanceof List<?>) {
			List<Resource> previousList = (List<Resource>) previous;
			previousList.addAll(Arrays.asList(embedded));
		} else if (previous != null) {
			List<Resource> previousList = new LinkedList<>();
			previousList.add((Resource)previous);
			previousList.addAll(Arrays.asList(embedded));
			this.embedded.put(reference, previousList);
		} else {
			if (embedded.length > 1) {
				this.embedded.put(reference, Arrays.asList(embedded));
			} else if (embedded.length == 1 && embedded[0] != null) {
				this.embedded.put(reference, embedded[0]);
			}
		} 
		
		return this;
	}
	
	public ResourceBuilder embedd(String reference, List<Resource> embedded) {
		return embedd(reference, embedded.toArray(new Resource[0]));
	}	
	
	public Resource build() {
		if (!this.curies.isEmpty()) {
			final List<Link> curiesList = new LinkedList<>();
			curiesList.addAll(curies.values());
			this.links.put("curies", curiesList);
		}
		
		Resource result = new Resource(representation, links, embedded);
		
		return result;
	}
	
	private void ensureCuriePrefixRegistration(final String reference) {
		if (reference.indexOf(":") != -1) {
			final String prefix = reference.substring(0, reference.indexOf(":"));
			if (this.curies == null || !this.curies.containsKey(prefix)) {
				throw new IllegalArgumentException("Reference to unregistered CURIE prefix \"" + prefix + "\"");
			}
		}
	}
}

