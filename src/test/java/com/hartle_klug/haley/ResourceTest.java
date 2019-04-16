package com.hartle_klug.haley;

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

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hartle_klug.haley.builder.LinkBuilder;
import com.hartle_klug.haley.builder.PropertyBuilder;
import com.hartle_klug.haley.builder.RepresentationBuilder;
import com.hartle_klug.haley.builder.ResourceBuilder;
import com.hartle_klug.haley.model.Resource;

public class ResourceTest {
	final static String LINK_HREF = "http://web.service/api";
	
	@Test
	public void testSerialization() throws JsonProcessingException {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.property(PropertyBuilder.use("name", "value").build())
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.link("link", LinkBuilder.use(LINK_HREF).build())
				.embedd("embedd",
						ResourceBuilder.use(
								RepresentationBuilder.use()
								.property(PropertyBuilder.use("name", "value").build())
								.build())
						.build())
				.build();
		
		ObjectMapper objectMapper = new ObjectMapper();
		assertEquals("{\"name\":\"value\",\"_links\":{\"link\":{\"href\":\"http://web.service/api\"}},\"_embedded\":{\"embedd\":{\"name\":\"value\"}}}", objectMapper.writeValueAsString(resource));
	}
	
	@Test
	@Ignore
	public void testAPI() throws JsonProcessingException {
		final Resource accountResource =
                ResourceBuilder.use(
                        RepresentationBuilder.use()
                        .property(PropertyBuilder.use("name", "John Smith").build())
                        .build())
                .linkSelf(
                		LinkBuilder.use("http://company.com/account/jsmith")
                		.profile("http://company.com/ns/account/account")
                		.build())
                .curie(
                		LinkBuilder.use("http://company.com/ns/account/{rel}")
                		.templated(true)
                		.name("account")
                		.build())
                .link("account:update",
                		LinkBuilder.use("http://company.com/account/jsmith/update")
                		.build())
                .embedd("repositories",
                        ResourceBuilder.use(
                                RepresentationBuilder.use()
                                .property(PropertyBuilder.use("name", "One Sample Repo").build())
                                .build())
                        .linkSelf(
                        		LinkBuilder.use("http://company.com/account/jsmith/repo/one-sample-repo")
                        		.profile("http://company.com/ns/repository")
                        		.build())
                        .build(),
                        
                        ResourceBuilder.use(
	                        RepresentationBuilder.use()
	                        .property(PropertyBuilder.use("name", "Another Sample Repo").build())
	                        .build())
                        .linkSelf(
                        		LinkBuilder.use("http://company.com/account/jsmith/repo/another-sample-repo")
                        		.profile("http://company.com/ns/repository")
                        		.build())
                        .build())
                .build();

		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(objectMapper.writeValueAsString(accountResource));
	}
}
