package test;
import static com.couchbase.client.java.query.QueryOptions.queryOptions;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

import org.junit.Test;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.ReactiveBucket;
import com.couchbase.client.java.ReactiveCluster;
import com.couchbase.client.java.ReactiveCollection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;

import io.micronaut.context.ApplicationContext;
import util.TestUtil;

/**
 * Tests related to the default Couchbase configuration.
 *
 * @author Graham Pople
 * @since 1.0.0
 */
public class DefaultTest {
	@Test
	public void injectCluster() throws IOException {
		ApplicationContext applicationContext = ApplicationContext.run();
		Cluster cluster = applicationContext.getBean(Cluster.class);
		assertNotNull(cluster);
	}

	@Test
	public void basicKeyValueOperations() throws IOException, InterruptedException {

		ApplicationContext applicationContext = TestUtil.applicationContext;
		Cluster cluster = applicationContext.getBean(Cluster.class);

		Collection collection = cluster.bucket(TestUtil.COUCHBASE_BUCKET_NAME).defaultCollection();

		collection.upsert("id", JsonObject.create().put("foo", "bar"));

		Optional<GetResult> result = Optional.ofNullable(collection.get("id"));

		assertTrue(result.isPresent());
		assertEquals("bar", result.get().contentAs(JsonObject.class).getString("foo"));
	}

	@Test
	public void basicKeyValueOperationsReactive() throws IOException, InterruptedException {
		ApplicationContext applicationContext = TestUtil.applicationContext;
		ReactiveCluster cluster = applicationContext.getBean(Cluster.class).reactive();

		ReactiveBucket testBucket = cluster.bucket(TestUtil.COUCHBASE_BUCKET_NAME);
		testBucket.waitUntilReady(Duration.ofSeconds(60));
		ReactiveCollection collection = testBucket.defaultCollection();

		collection.upsert("id", JsonObject.create().put("foo", "bar")).block();
		JsonObject document = collection.get("id").block().contentAsObject();

		assertEquals("bar", document.getString("foo"));
	}

	@Test
	public void basicN1QLOperations() throws IOException, InterruptedException {
		ApplicationContext applicationContext = TestUtil.applicationContext;
		Cluster cluster = applicationContext.getBean(Cluster.class);

		Collection collection = cluster.bucket(TestUtil.COUCHBASE_BUCKET_NAME).defaultCollection();

		collection.upsert("id", JsonObject.create().put("foo", "bar"));

		QueryResult result = cluster.query("select * from `default` where id = $id",
				queryOptions().parameters(JsonObject.create().put("id", "foo")));

		for (JsonObject jsonObject : result.rowsAs(JsonObject.class)) {
			assertEquals("bar", jsonObject.getString("foo"));
		}
	}
}