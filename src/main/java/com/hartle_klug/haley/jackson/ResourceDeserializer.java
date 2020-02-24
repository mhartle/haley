package com.hartle_klug.haley.jackson;

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

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hartle_klug.haley.model.Link;
import com.hartle_klug.haley.model.Resource;

/**
 * Deserializes HAL resources through Jackson
 * 
 * @author Michael Hartle <mhartle@hartle-klug.com>
 *
 */
public class ResourceDeserializer extends StdDeserializer<Resource> {
	private static final long serialVersionUID = 1L;
	private static final String FIELD_LINKS = "_links";
	private static final String FIELD_EMBEDDED = "_embedded";

	public ResourceDeserializer() {
		super(Resource.class);
	}

	@Override
	public Resource deserialize(final JsonParser resourceParser, final DeserializationContext context)
			throws IOException, JsonProcessingException {
		final JsonNode resourceNode = resourceParser.readValueAsTree();
		final Map<String, Object> links = new LinkedHashMap<>();
		final Map<String, Object> embedded = new LinkedHashMap<>();
		final Map<String, Object> representation = new LinkedHashMap<>();

		final Iterator<Map.Entry<String, JsonNode>> resourceFieldIterator = resourceNode.fields();
		while (resourceFieldIterator.hasNext()) {
			final Map.Entry<String, JsonNode> resourceField = resourceFieldIterator.next();
			final String key = resourceField.getKey();

			switch (key) {
			case FIELD_LINKS:
				final Iterator<Map.Entry<String, JsonNode>> linkIterator = resourceField.getValue().fields();
				while (linkIterator.hasNext()) {
					final Map.Entry<String, JsonNode> linkField = linkIterator.next();
					final JsonParser linkParser = linkField.getValue().traverse(resourceParser.getCodec());
					links.put(linkField.getKey(),
							linkField.getValue().isArray() ?
									linkParser.readValueAs(new TypeReference<List<Link>>() {}) :
										linkParser.readValueAs(new TypeReference<Link>() {}));
				}
				break;

			case FIELD_EMBEDDED:
				final Iterator<Map.Entry<String, JsonNode>> embeddedIterator = resourceField.getValue().fields();
				while (embeddedIterator.hasNext()) {
					final Map.Entry<String, JsonNode> embeddedField = embeddedIterator.next();
					final JsonParser embeddedParser = embeddedField.getValue().traverse(resourceParser.getCodec());
					embedded.put(embeddedField.getKey(),
							embeddedField.getValue().isArray() ?
									embeddedParser.readValueAs(new TypeReference<List<Resource>>() {}) :
										embeddedParser.readValueAs(new TypeReference<Resource>() {}));
				}
				break;

			default:
				final JsonParser representationParser = resourceField.getValue().traverse(resourceParser.getCodec());
				representation.put(resourceField.getKey(), representationParser.readValueAs(Object.class));
			}
		}

		final Resource result = new Resource(representation, links, embedded);

		return result;
	}
}
