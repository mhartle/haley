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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hartle_klug.haley.model.Resource;

/**
 * Serializes HAL resources through Jackson
 * 
 * @author Michael Hartle <mhartle@hartle-klug.com>
 *
 */
public class ResourceSerializer extends StdSerializer<Resource> {
	private static final long serialVersionUID = 1L;

	public ResourceSerializer() {
		this(null);
	}
	
	public ResourceSerializer(Class<Resource> t) {
		super(t);
	}

	@Override
	public void serialize(Resource resource, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        
        if (resource.getRepresentation() != null) {
        	for(String name : resource.getRepresentation().keySet()) {
        		generator.writeObjectField(name, resource.getRepresentation().get(name));
        	}
        }
        
        if (resource.getLinks() != null && !resource.getLinks().isEmpty()) {
	        generator.writeFieldName("_links");
	        provider.defaultSerializeValue(resource.getLinks(), generator);
        }

        if (resource.getEmbeddeds() != null && !resource.getEmbeddeds().isEmpty()) {
	        generator.writeFieldName("_embedded");
	        provider.defaultSerializeValue(resource.getEmbeddeds(), generator);
        }
        
        generator.writeEndObject();
	}
}
