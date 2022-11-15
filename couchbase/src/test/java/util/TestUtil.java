package util;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

import org.testcontainers.couchbase.BucketDefinition;
import org.testcontainers.couchbase.CouchbaseContainer;

import io.micronaut.configuration.couchbase.CouchbaseSettings;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.PropertySource;
import io.micronaut.core.util.CollectionUtils;

@SuppressWarnings({ "resource", "unchecked" })
public class TestUtil {

	private static final String COUCHBASE_USERNAME = "admin";
	private static final String COUCHBASE_PASSWORD = "test123";
	public static final String COUCHBASE_BUCKET_NAME = "default";
	private static final String COUCHBASE_CONTAINER_BASE_IMAGE = "couchbase/server:7.1.1";
	private static final String TEST_PROPERTY = "test";
	private static CouchbaseContainer container;
	public static ApplicationContext applicationContext;
	
	static {
		BucketDefinition bucketDefinition = new BucketDefinition(COUCHBASE_BUCKET_NAME).withPrimaryIndex(true);
		container = new CouchbaseContainer(COUCHBASE_CONTAINER_BASE_IMAGE)
				.withCredentials(COUCHBASE_USERNAME, COUCHBASE_PASSWORD)
				.withExposedPorts(8091, 8092, 8093, 8094, 8095, 8096, 9100, 9101, 9102, 9103, 9104, 9105,
						9119, 9999, 21200, 21100, 21150, 21250, 21300, 21350)
				.withBucket(bucketDefinition).withStartupAttempts(2).withStartupTimeout(Duration.ofMinutes(2));
		container.setPortBindings(Arrays.asList("8091:8091", "8092:8092", "8093:8093", "8094:8094", "8095:8095",
				"8096:8096", "9100:9100", "9101:9101", "9102:9102", "9103:9103", "9104:9104", "9105:9105", "11210:11210", "9119:9119", "9999:9999", "21200:21200", "21250:21250",
				"21300:21300", "21350:21350", "21100:21100", "21150:21150"));
		container.start();
		Map<String, Object> settings = CollectionUtils.mapOf(CouchbaseSettings.PREFIX + "." + CouchbaseSettings.URI,
				container.getConnectionString(), CouchbaseSettings.PREFIX + "." + CouchbaseSettings.USERNAME,
				container.getUsername(), CouchbaseSettings.PREFIX + "." + CouchbaseSettings.PASSWORD,
				container.getPassword());
		applicationContext = ApplicationContext.run(PropertySource.of(TEST_PROPERTY, settings), TEST_PROPERTY);
	}
}