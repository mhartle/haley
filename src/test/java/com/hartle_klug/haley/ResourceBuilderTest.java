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

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.hartle_klug.haley.builder.LinkBuilder;
import com.hartle_klug.haley.builder.PropertyBuilder;
import com.hartle_klug.haley.builder.RepresentationBuilder;
import com.hartle_klug.haley.builder.ResourceBuilder;
import com.hartle_klug.haley.model.Link;
import com.hartle_klug.haley.model.Resource;

public class ResourceBuilderTest {
	final static String LINK_HREF_FIRST = "http://web.service/api/first";
	final static String LINK_HREF_MIDDLE = "http://web.service/api/middle";
	final static String LINK_HREF_LAST = "http://web.service/api/last";
	
	@Test
	public void testRepresentation() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnregisteredCuriePrefixException() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Link link =
				LinkBuilder.use(LINK_HREF_FIRST)
				.name("first")
				.build();
		
		ResourceBuilder.use(representation)
		.link("fail:test", link)
		.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnnamedCurieException() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Link link =
				LinkBuilder.use(LINK_HREF_FIRST)
				.build();
		
		ResourceBuilder.use(representation)
		.curie(link)
		.build();
	}

	@Test
	public void testCurie() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Link link =
				LinkBuilder.use(LINK_HREF_FIRST)
				.name("first")
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.curie(link)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertEquals(Arrays.asList(link), resource.getLinks().get("curies"));
	}

	@Test
	public void testCuries() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Link firstLink =
				LinkBuilder.use(LINK_HREF_FIRST)
				.name("first")
				.build();
		
		final Link lastLink =
				LinkBuilder.use(LINK_HREF_LAST)
				.name("last")
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.curie(firstLink, lastLink)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertThat(Arrays.asList(firstLink, lastLink), equalTo(resource.getLinks().get("curies")));
	}

	@Test
	public void testCuriesList() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Link firstLink =
				LinkBuilder.use(LINK_HREF_FIRST)
				.name("first")
				.build();
		
		final Link lastLink =
				LinkBuilder.use(LINK_HREF_LAST)
				.name("last")
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.curie(Arrays.asList(firstLink, lastLink))
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertThat(Arrays.asList(firstLink, lastLink), equalTo(resource.getLinks().get("curies")));
	}

	@Test
	public void testLinkSelf() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Link link =
				LinkBuilder.use(LINK_HREF_FIRST)
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.linkSelf(link)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertEquals(link, resource.getLinks().get("self"));
	}

	@Test
	public void testLink() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Link link =
				LinkBuilder.use(LINK_HREF_FIRST)
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.link("link", link)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertEquals(link, resource.getLinks().get("link"));
	}

	@Test
	public void testLinks() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Link firstLink =
				LinkBuilder.use(LINK_HREF_FIRST)
				.build();
		
		final Link lastLink =
				LinkBuilder.use(LINK_HREF_LAST)
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.link("link", firstLink, lastLink)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertThat(Arrays.asList(firstLink, lastLink), equalTo(resource.getLinks().get("link")));
	}

	@Test
	public void testTwoSequentialLinks() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Link firstLink =
				LinkBuilder.use(LINK_HREF_FIRST)
				.build();
		
		final Link lastLink =
				LinkBuilder.use(LINK_HREF_LAST)
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.link("link", firstLink)
				.link("link", lastLink)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertThat(Arrays.asList(firstLink, lastLink), equalTo(resource.getLinks().get("link")));
	}

	@Test
	public void testThreeSequentialLinks() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Link firstLink =
				LinkBuilder.use(LINK_HREF_FIRST)
				.build();
		
		final Link middleLink =
				LinkBuilder.use(LINK_HREF_MIDDLE)
				.build();
		
		final Link lastLink =
				LinkBuilder.use(LINK_HREF_LAST)
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.link("link", firstLink)
				.link("link", middleLink)
				.link("link", lastLink)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertThat(Arrays.asList(firstLink, middleLink, lastLink), equalTo(resource.getLinks().get("link")));
	}

	@Test
	public void testLinksList() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();
		
		final Link firstLink =
				LinkBuilder.use(LINK_HREF_FIRST)
				.build();
		
		final Link lastLink =
				LinkBuilder.use(LINK_HREF_LAST)
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.link("link", Arrays.asList(firstLink, lastLink))
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertThat(Arrays.asList(firstLink, lastLink), equalTo(resource.getLinks().get("link")));
	}

	@Test
	public void testEmbedded() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();

		final Resource embeddedResource =
				ResourceBuilder.use(RepresentationBuilder.use().build())
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.embedd("embedded", embeddedResource)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertEquals(embeddedResource, resource.getEmbeddeds().get("embedded"));		
	}

	@Test
	public void testEmbeddeds() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();

		final Resource firstEmbeddedResource =
				ResourceBuilder.use(
						RepresentationBuilder.use()
						.property(PropertyBuilder.use("first", "value").build())
						.build())
				.build();
		
		final Resource lastEmbeddedResource =
				ResourceBuilder.use(
						RepresentationBuilder.use()
						.property(PropertyBuilder.use("last", "value").build())
						.build())
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.embedd("embedded", firstEmbeddedResource, lastEmbeddedResource)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertEquals(Arrays.asList(firstEmbeddedResource, lastEmbeddedResource), resource.getEmbeddeds().get("embedded"));		
	}

	@Test
	public void testTwoSequentialEmbeddeds() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();

		final Resource firstEmbeddedResource =
				ResourceBuilder.use(
						RepresentationBuilder.use()
						.property(PropertyBuilder.use("first", "value").build())
						.build())
				.build();
		
		final Resource lastEmbeddedResource =
				ResourceBuilder.use(
						RepresentationBuilder.use()
						.property(PropertyBuilder.use("last", "value").build())
						.build())
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.embedd("embedded", firstEmbeddedResource)
				.embedd("embedded", lastEmbeddedResource)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertEquals(Arrays.asList(firstEmbeddedResource, lastEmbeddedResource), resource.getEmbeddeds().get("embedded"));		
	}

	@Test
	public void testThreeSequentialEmbeddeds() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();

		final Resource firstEmbeddedResource =
				ResourceBuilder.use(
						RepresentationBuilder.use()
						.property(PropertyBuilder.use("first", "value").build())
						.build())
				.build();
		
		final Resource middleEmbeddedResource =
				ResourceBuilder.use(
						RepresentationBuilder.use()
						.property(PropertyBuilder.use("middle", "value").build())
						.build())
				.build();
		
		final Resource lastEmbeddedResource =
				ResourceBuilder.use(
						RepresentationBuilder.use()
						.property(PropertyBuilder.use("last", "value").build())
						.build())
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.embedd("embedded", firstEmbeddedResource)
				.embedd("embedded", middleEmbeddedResource)
				.embedd("embedded", lastEmbeddedResource)
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertEquals(Arrays.asList(firstEmbeddedResource, middleEmbeddedResource, lastEmbeddedResource), resource.getEmbeddeds().get("embedded"));		
	}

	@Test
	public void testEmbeddedsList() {
		final Map<String, Object> representation =
				RepresentationBuilder.use()
				.build();

		final Resource firstEmbeddedResource =
				ResourceBuilder.use(
						RepresentationBuilder.use()
						.property(PropertyBuilder.use("first", "value").build())
						.build())
				.build();
		
		final Resource lastEmbeddedResource =
				ResourceBuilder.use(
						RepresentationBuilder.use()
						.property(PropertyBuilder.use("last", "value").build())
						.build())
				.build();
		
		final Resource resource =
				ResourceBuilder.use(representation)
				.embedd("embedded", Arrays.asList(firstEmbeddedResource, lastEmbeddedResource))
				.build();
		
		assertEquals(representation, resource.getRepresentation());
		assertEquals(Arrays.asList(firstEmbeddedResource, lastEmbeddedResource), resource.getEmbeddeds().get("embedded"));		
	}
}
