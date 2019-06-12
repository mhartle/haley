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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.hartle_klug.haley.builder.LinkBuilder;
import com.hartle_klug.haley.model.Link;

public class LinkBuilderTest {
	final static String LINK_HREF = "http://web.service/api";
	final static String LINK_DEPRECATION = "http://web.service/api/deprecation-notice";
	final static String LINK_HREFLANG = "de-DE";
	final static String LINK_NAME = "basic-api";
	final static String LINK_PROFILE = "http://web.service/api/profile";
	final static String LINK_TITLE = "Some API";
	final static String LINK_TYPE = "application/hal+json";
	final static String LINK_FIRST_PROPERTY_KEY = "first-link-property-key";
	final static String LINK_FIRST_PROPERTY_VALUE = "first-link-property-value";
	final static String LINK_LAST_PROPERTY_KEY = "last-link-property-key";
	final static String LINK_LAST_PROPERTY_VALUE = "last-link-property-value";
	
	@Test
	public void testLinkHref() {
		final Link link = LinkBuilder.use(LINK_HREF).build();
		assertEquals(LINK_HREF, link.getHref());
	}

	@Test
	public void testLinkDeprecation() {
		final Link link = LinkBuilder.use(LINK_HREF).deprecation(LINK_DEPRECATION).build();
		assertEquals(LINK_DEPRECATION, link.getProperties().get(Link.PROPERTY_DEPRECATION));
	}
	
	@Test
	public void testLinkHreflang() {
		final Link link = LinkBuilder.use(LINK_HREF).hreflang(LINK_HREFLANG).build();
		assertEquals(LINK_HREFLANG, link.getProperties().get(Link.PROPERTY_HREFLANG));
	}
	
	@Test
	public void testLinkName() {
		final Link link = LinkBuilder.use(LINK_HREF).name(LINK_NAME).build();
		assertEquals(LINK_NAME, link.getName());		
	}
	
	@Test
	public void testLinkProfile() {
		final Link link = LinkBuilder.use(LINK_HREF).profile(LINK_PROFILE).build();
		assertEquals(LINK_PROFILE, link.getProperties().get(Link.PROPERTY_PROFILE));		
	}
	
	@Test
	public void testLinkTemplated() {
		final Link link = LinkBuilder.use(LINK_HREF).templated(true).build();
		assertEquals(true, link.getProperties().get(Link.PROPERTY_TEMPLATED));		
	}

	@Test
	public void testLinkTitle() {
		final Link link = LinkBuilder.use(LINK_HREF).title(LINK_TITLE).build();
		assertEquals(LINK_TITLE, link.getProperties().get(Link.PROPERTY_TITLE));		
	}

	@Test
	public void testLinkType() {
		final Link link = LinkBuilder.use(LINK_HREF).type(LINK_TYPE).build();
		assertEquals(LINK_TYPE, link.getProperties().get(Link.PROPERTY_TYPE));		
	}
	
	@Test
	public void testLinkProperty() {
		final Link link = LinkBuilder.use(LINK_HREF).property(LINK_FIRST_PROPERTY_KEY, LINK_FIRST_PROPERTY_VALUE).build();
		assertEquals(LINK_FIRST_PROPERTY_VALUE, link.getProperties().get(LINK_FIRST_PROPERTY_KEY));		
	}
	
	@Test
	public void testLinkProperties() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(LINK_FIRST_PROPERTY_KEY, LINK_FIRST_PROPERTY_VALUE);
		properties.put(LINK_LAST_PROPERTY_KEY, LINK_LAST_PROPERTY_VALUE);
		final Link link = LinkBuilder.use(LINK_HREF).properties(properties).build();
		assertEquals(LINK_FIRST_PROPERTY_VALUE, link.getProperties().get(LINK_FIRST_PROPERTY_KEY));
		assertEquals(LINK_LAST_PROPERTY_VALUE, link.getProperties().get(LINK_LAST_PROPERTY_KEY));
	}
}
