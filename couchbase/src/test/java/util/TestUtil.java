package util;

import io.micronaut.configuration.couchbase.CouchbaseSettings;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.PropertySource;
import io.micronaut.core.util.CollectionUtils;
import org.testcontainers.couchbase.BucketDefinition;
import org.testcontainers.couchbase.CouchbaseContainer;

import java.io.IOException;
import java.util.Map;

public class TestUtil {
    private TestUtil() {
    }

    /**
     * The Couchbase Mock is a Java implementation of the Couchbase cluster used for testing.
     *
     * @return the ApplicationContext, configured appropriately for connecting to a constructed running Couchbase mock
     */
    public static ApplicationContext initCouchbaseTestContainer(String bucketName, String imageName) throws IOException, InterruptedException {
        BucketDefinition bucketDefinition = new BucketDefinition(bucketName);
        CouchbaseContainer container = new CouchbaseContainer(imageName)
                .withBucket(bucketDefinition);

        container.start();

        Map<String, Object> settings = CollectionUtils.mapOf(
                CouchbaseSettings.PREFIX + "." + CouchbaseSettings.URI, container.getConnectionString(),
                CouchbaseSettings.PREFIX + "." + CouchbaseSettings.USERNAME, container.getUsername(),
                CouchbaseSettings.PREFIX + "." + CouchbaseSettings.PASSWORD, container.getPassword());

        ApplicationContext applicationContext = ApplicationContext.run(
                PropertySource.of("test", settings),
                "test");

        return applicationContext;
    }
}
