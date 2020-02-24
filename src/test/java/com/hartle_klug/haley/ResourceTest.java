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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hartle_klug.haley.builder.LinkBuilder;
import com.hartle_klug.haley.builder.PropertyBuilder;
import com.hartle_klug.haley.builder.RepresentationBuilder;
import com.hartle_klug.haley.builder.ResourceBuilder;
import com.hartle_klug.haley.model.Link;
import com.hartle_klug.haley.model.Resource;

public class ResourceTest {
	final static String LINK_ALPHA_HREF = "http://web.service/api/alpha";
	final static String LINK_BETA_HREF = "http://web.service/api/beta";
	final static String LINK_GAMMA_HREF = "http://web.service/api/gamma";

	@Test
	public void testSerialization() throws JsonProcessingException {
		final Map<String, Object> representation = RepresentationBuilder.use()
				.property(PropertyBuilder.use("name", "value").build()).build();
		final Resource resource = ResourceBuilder.use(representation)
				.link("link", LinkBuilder.use(LINK_ALPHA_HREF).build())
				.embedd("embedd", ResourceBuilder
						.use(RepresentationBuilder.use().property(PropertyBuilder.use("name", "value").build()).build())
						.build())
				.build();
		final ObjectMapper objectMapper = new ObjectMapper();
		final String serialization = objectMapper.writeValueAsString(resource);
		assertEquals(
				"{\"name\":\"value\",\"_links\":{\"link\":{\"href\":\"http://web.service/api/alpha\"}},\"_embedded\":{\"embedd\":{\"name\":\"value\"}}}",
				serialization);
	}

	@Test
	public void testDeserialization() throws JsonMappingException, JsonProcessingException {
		final String serialization = "{\"name\":\"value\",\"_links\":{\"link\":{\"href\":\"http://web.service/api/alpha\"}},\"_embedded\":{\"embedd\":{\"name\":\"value\"}}}";
		final ObjectMapper objectMapper = new ObjectMapper();
		final Resource resource = objectMapper.readValue(serialization, Resource.class);
		assertEquals("value", resource.getRepresentation().get("name"));
		assertEquals(LINK_ALPHA_HREF, ((Link) resource.getLinks().get("link")).getHref());
		assertEquals("value", ((Resource) resource.getEmbeddeds().get("embedd")).getRepresentation().get("name"));
	}

	@Test
	public void testLinkSerializationDeserialization() throws JsonMappingException, JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		final Link link = LinkBuilder.use(LINK_ALPHA_HREF).build();
		final Resource resource = ResourceBuilder.use(RepresentationBuilder.use().build()).link("link", link).build();
		final String serialization = objectMapper.writeValueAsString(resource);
		final Resource otherResource = objectMapper.readValue(serialization, Resource.class);
		final Link otherLink = (Link) otherResource.getLinks().get("link");
		assertEquals(link.getHref(), otherLink.getHref());
	}

	@Test
	public void testLinkListSerializationDeserialization() throws JsonMappingException, JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		final List<Link> links = Arrays.asList(LinkBuilder.use(LINK_ALPHA_HREF).build(),
				LinkBuilder.use(LINK_BETA_HREF).build());
		final Resource resource = ResourceBuilder.use(RepresentationBuilder.use().build()).link("link", links).build();
		final String serialization = objectMapper.writeValueAsString(resource);
		final Resource otherResource = objectMapper.readValue(serialization, Resource.class);
		@SuppressWarnings("unchecked")
		final List<Link> otherLinks = (List<Link>) otherResource.getLinks().get("link");
		assertEquals(links, otherLinks);
	}

	@Test
	public void testEmbeddedSerializationDeserialization() throws JsonMappingException, JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		final Resource embeddedResource = ResourceBuilder.use(RepresentationBuilder.use().build())
				.linkSelf(LinkBuilder.use(LINK_BETA_HREF).build()).build();
		final Resource resource = ResourceBuilder.use(RepresentationBuilder.use().build())
				.linkSelf(LinkBuilder.use(LINK_ALPHA_HREF).build()).embedd("elements", embeddedResource).build();
		final String serialization = objectMapper.writeValueAsString(resource);
		final Resource otherResource = objectMapper.readValue(serialization, Resource.class);
		final Resource otherEmbeddedResource = (Resource) otherResource.getEmbeddeds().get("elements");
		assertEquals(embeddedResource, otherEmbeddedResource);
	}

	@Test
	public void testEmbeddedListSerializationDeserialization() throws JsonMappingException, JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		final List<Resource> embeddedResources = Arrays.asList(
				ResourceBuilder.use(RepresentationBuilder.use().build())
						.linkSelf(LinkBuilder.use(LINK_BETA_HREF).build()).build(),
				ResourceBuilder.use(RepresentationBuilder.use().build())
						.linkSelf(LinkBuilder.use(LINK_GAMMA_HREF).build()).build());
		final Resource resource = ResourceBuilder.use(RepresentationBuilder.use().build())
				.linkSelf(LinkBuilder.use(LINK_ALPHA_HREF).build()).embedd("elements", embeddedResources).build();
		final String serialization = objectMapper.writeValueAsString(resource);
		final Resource otherResource = objectMapper.readValue(serialization, Resource.class);
		@SuppressWarnings({ "unchecked" })
		final List<Resource> otherEmbeddedResources = (List<Resource>) otherResource.getEmbeddeds().get("elements");
		assertEquals(embeddedResources, otherEmbeddedResources);
	}

	@Test
	@Ignore
	public void testAPI() throws JsonProcessingException {
		final Resource accountResource = ResourceBuilder
				.use(RepresentationBuilder.use().property(PropertyBuilder.use("name", "John Smith").build()).build())
				.linkSelf(LinkBuilder.use("http://company.com/account/jsmith")
						.profile("http://company.com/ns/account/account").build())
				.curie(LinkBuilder.use("http://company.com/ns/account/{rel}").templated(true).name("account").build())
				.link("account:update", LinkBuilder.use("http://company.com/account/jsmith/update").build())
				.embedd("repositories",
						ResourceBuilder
								.use(RepresentationBuilder.use()
										.property(PropertyBuilder.use("name", "One Sample Repo").build()).build())
								.linkSelf(
										LinkBuilder.use("http://company.com/account/jsmith/repo/one-sample-repo")
												.profile("http://company.com/ns/repository").build())
								.build(),

						ResourceBuilder
								.use(RepresentationBuilder.use()
										.property(PropertyBuilder.use("name", "Another Sample Repo").build()).build())
								.linkSelf(LinkBuilder.use("http://company.com/account/jsmith/repo/another-sample-repo")
										.profile("http://company.com/ns/repository").build())
								.build())
				.build();

		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(objectMapper.writeValueAsString(accountResource));
	}
}
