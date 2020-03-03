# Haley HAL Resource Builder for Java

## Overview

* Haley is a builder for Hypertext Application Language (HAL) resources as
  per [draft-kelly-json-hal-08](https://www.ietf.org/archive/id/draft-kelly-json-hal-08.txt)
  for web service controllers written in Java. It uses Jackson for the
  serialization and deserialization of HAL JSON resources.

* The library is useful for Hypertext As The Engine Of Application State
  (HATEOAS)-based applications. It allows you to build HAL resources
  through a fluent builder API including support for links, embedded
  resources and CURIEs.

## Dependency

### Maven

```
	<dependency>
		<groupId>com.hartle-klug</groupId>
		<artifactId>haley</artifactId>
		<version>1.0.1</version>
	</dependency>
```

### Gradle

```
	compile group: 'com.hartle-klug', name: 'haley', version: '1.0.1'
``

## Usage

* Import the respective builder classes to your Java class

```
	import com.hartle_klug.haley.builder.ResourceBuilder;
	import com.hartle_klug.haley.builder.RepresentationBuilder;
	import com.hartle_klug.haley.builder.PropertyBuilder;
	import com.hartle_klug.haley.builder.LinkBuilder;
```

* Use the builders accordingly; eg. to create an fictious Account HAL
  resource for a user John Smith who has two repositories, the following
  code snippet

```
	final Resource accountResource =
		ResourceBuilder.use(
			RepresentationBuilder.use()
			.property(PropertyBuilder.use("name", "John Smith").build())
			.build())
		.linkSelf(
			LinkBuilder.use("http://company.com/account/jsmith")
			.profile("http://company.com/ns/account")
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
```

produces a HAL resource that Jackson (either implicitly in a Spring / Jersey controller or explicitly
through the use of the ObjectMapper method writeValueAsString) serializes as

```
{
  "name": "John Smith",
  "_links": {
    "self": {
      "profile": "http://company.com/ns/account/account",
      "href": "http://company.com/account/jsmith"
    },
    "account:update": {
      "href": "http://company.com/account/jsmith/update"
    },
    "curies": [
      {
        "name": "account",
        "href": "http://company.com/ns/account/{rel}",
        "templated": true
      }
    ]
  },
  "_embedded": {
    "repositories": [
      {
        "name": "One Sample Repo",
        "_links": {
          "self": {
            "profile": "http://company.com/ns/repository",
            "href": "http://company.com/account/jsmith/repo/one-sample-repo"
          }
        }
      },
      {
        "name": "Another Sample Repo",
        "_links": {
          "self": {
            "profile": "http://company.com/ns/repository",
            "href": "http://company.com/account/jsmith/repo/another-sample-repo"
          }
        }
      }
    ]
  }
}
```


