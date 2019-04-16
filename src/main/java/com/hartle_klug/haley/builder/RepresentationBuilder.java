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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hartle_klug.haley.model.Property;

/**
 * Builds HAL resource representations
 * 
 * @author Michael Hartle <mhartle@hartle-klug.com>
 *
 */
public class RepresentationBuilder {
	private Map<String, Object> representation;
	
	protected RepresentationBuilder() {
		this.representation = new LinkedHashMap<>();
	}
	
	public static RepresentationBuilder use() {
		RepresentationBuilder builder = new RepresentationBuilder();
		
		return builder;
	}
	
	@SuppressWarnings("rawtypes")
	public static RepresentationBuilder use(Object instance) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RepresentationBuilder builder = new RepresentationBuilder();
		
		if (instance instanceof Map) {
			for (Object key : ((Map)instance).keySet()) {
				builder.representation.put(key.toString(), ((Map)instance).get(key));
			}
		} else {
			
			BeanInfo beanInfo = Introspector.getBeanInfo(instance.getClass(), Object.class);
			for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
				Method getterMethod = propertyDescriptor.getReadMethod();
				builder.representation.put(propertyDescriptor.getName(), getterMethod.invoke(instance));				
			}
		}
		
		return builder;
	}
	
	public RepresentationBuilder property(Property... properties) {
		for(Property property : properties) {
			if (property != null) {
				this.representation.put(property.getName(), property.getValue());
			}
		}		
		
		return this;
	}
	
	public RepresentationBuilder property(List<Property> properties) {
		return property(properties.toArray(new Property[0]));
	}
	
	public Map<String, Object> build() {
		return this.representation;
	}
}
