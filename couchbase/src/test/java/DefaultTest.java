import com.couchbase.client.java.*;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;
import io.micronaut.context.ApplicationContext;

import static com.couchbase.client.java.query.QueryOptions.queryOptions;

import org.junit.Test;
import util.TestUtil;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

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
        String bucketName = "default";
        String imageName = "couchbase/server";

        ApplicationContext applicationContext = TestUtil.initCouchbaseTestContainer(bucketName, imageName);
        Cluster cluster = applicationContext.getBean(Cluster.class);

        Collection collection = cluster.bucket(bucketName).defaultCollection();

        collection.upsert("id", JsonObject.create().put("foo", "bar"));

        Optional<GetResult> result = Optional.ofNullable(collection.get("id"));

        assertTrue(result.isPresent());
        assertEquals("bar", result.get().contentAs(JsonObject.class).getString("foo"));
    }

    @Test
    public void basicKeyValueOperationsReactive() throws IOException, InterruptedException {
        String bucketName = "default";
        String imageName = "couchbase/server";

        ApplicationContext applicationContext = TestUtil.initCouchbaseTestContainer(bucketName, imageName);
        ReactiveCluster cluster = applicationContext.getBean(Cluster.class).reactive();

        ReactiveCollection collection = cluster.bucket(bucketName).defaultCollection();

        collection.upsert("id", JsonObject.create().put("foo", "bar")).block();
        JsonObject document = collection.get("id").block().contentAsObject();

        assertEquals("bar", document.getString("foo"));
    }

    @Test
    public void basicN1QLOperations() throws IOException, InterruptedException {
        String bucketName = "default";
        String imageName = "couchbase/server";

        ApplicationContext applicationContext = TestUtil.initCouchbaseTestContainer(bucketName, imageName);
        Cluster cluster = applicationContext.getBean(Cluster.class);

        Collection collection = cluster.bucket(bucketName).defaultCollection();

        collection.upsert("id", JsonObject.create().put("foo", "bar"));

        QueryResult result = cluster.query(
                "select * from `default` where id = $id",
                queryOptions().parameters(JsonObject.create().put("id", "foo"))
        );

        for (JsonObject jsonObject : result.rowsAs(JsonObject.class)) {
            assertEquals("bar", jsonObject.getString("foo"));
        }
    }
}