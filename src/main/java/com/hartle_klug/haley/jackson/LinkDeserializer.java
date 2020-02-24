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
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hartle_klug.haley.model.Link;

/**
 * Deserializes HAL resources through Jackson
 * 
 * @author Michael Hartle <mhartle@hartle-klug.com>
 *
 */
public class LinkDeserializer extends StdDeserializer<Link> {
	private static final long serialVersionUID = 1L;

	public LinkDeserializer() {
		super(Link.class);
	}

	@Override
	public Link deserialize(JsonParser linkParser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		final JsonNode linkNode = linkParser.readValueAsTree();
		final Map<String, Object> properties = new LinkedHashMap<>();

		final Iterator<Map.Entry<String, JsonNode>> propertyFieldIterator = linkNode.fields();
		while (propertyFieldIterator.hasNext()) {
			final Map.Entry<String, JsonNode> propertyField = propertyFieldIterator.next();
			final JsonParser propertyParser = propertyField.getValue().traverse(linkParser.getCodec());
			properties.put(propertyField.getKey(), propertyParser.readValueAs(Object.class));
		}

		final Link link = new Link(properties);

		return link;
	}
}
