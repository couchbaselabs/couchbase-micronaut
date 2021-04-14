# Micronaut Couchbase Configuration #

[![Build Status](https://travis-ci.org/micronaut-projects/micronaut-couchbase.svg?branch=master)](https://travis-ci.org/micronaut-projects/micronaut-couchbase)
[![Download](https://api.bintray.com/packages/micronaut/core-releases-local/couchbase/images/download.svg)](https://bintray.com/micronaut/core-releases-local/couchbase/_latestVersion)

This is a configuration for using Couchbase in Micronaut applications.
  
## Documentation ##

See the [Documentation](https://micronaut-projects.github.io/micronaut-couchbase/latest/guide/index.html) for more information.


## Running the Test ##
The tests use the Testcontainers [Couchbase Module](https://www.testcontainers.org/modules/databases/couchbase/). To run the tests, you need to have docker installed and running. 
```sh
./gradlew test
```

## Usage ##
Once the integration is published as a feature by Micronaut, users should be able to generate Micronaut projects using the [Micronaut Launch Tool.](https://micronaut.io/launch/)

## Licenses ##
The original licenses that [this repository](https://github.com/micronaut-projects/micronaut-couchbase) have been forked from have not been changed.


## Update Files ##
This repo has been forked from https://github.com/micronaut-projects/micronaut-couchbase.git with the following updates:
8 changed files  with 129 additions and 107 deletions.

README.md   
couchbase/build.gradle   
couchbase/src/main/java/io/micronaut/configuration/couchbase/CouchbaseSettings.java  
couchbase/src/main/java/io/micronaut/configuration/couchbase/DefaultCouchbaseClientFactory.java  
couchbase/src/main/java/io/micronaut/configuration/couchbase/DefaultCouchbaseConfiguration.java  
couchbase/src/test/java/DefaultTest.java  
couchbase/src/test/java/util/TestUtil.java    
docs-examples/example-java/src/main/java/example/Example.java  
