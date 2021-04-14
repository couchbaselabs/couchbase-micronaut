## Couchbase Micronaut Demo

This sample application makes use of the updated [micronaut-couchbase package](https://github.com/couchbaselabs/couchbase-micronaut). 

### Note
The updated micronaut package has not been realased yet. To use it in your micronaut application, download the micronaut-couchbase repository and run the following command to publish the package to your local Maven repository.

```sh
./gradlew publishToMavenLocal
```

Include the pacakage in your build.gradle file by adding the following

```java
  repositories {
      mavenLocal()
  }
  ...
  ...
  ...
  
  dependencies {
    compile("io.micronaut.configuration:micronaut-couchbase:1.0.0.BUILD-SNAPSHOT")
}
```


## Sample App Usage
Once you have set up the local maven repository, you can configure your Couchbase cluster settings in the resources/application.yml file by adding the couchbase section as follows:

```sh
couchbase:
  uri: "localhost"
  username: "Administrator"
  password: "password"
```

## Running the Application
```sh
./gradlew run
```
