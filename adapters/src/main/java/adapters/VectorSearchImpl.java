package adapters;

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.Query;

import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;

public class VectorSearchImpl {

    private final String url;
    private final String username;
    private final String password;

    public VectorSearchImpl(
        String url,
        String username,
        String password
    ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static byte[] floatArrayToByteArray(float[] input) {
        byte[] bytes = new byte[Float.BYTES * input.length];
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer().put(input);
        return bytes;
    }

    public static byte[] longArrayToByteArray(long[] input) {
        return floatArrayToByteArray(longArrayToFloatArray(input));
    }

    public static float[] longArrayToFloatArray(long[] input) {
        float[] floats = new float[input.length];
        for (int i = 0; i < input.length; i++) {
            floats[i] = input[i];
        }
        return floats;
    }

    public void execute(String sentence) throws URISyntaxException {
        // Connect to Redis
        UnifiedJedis unifiedjedis = new UnifiedJedis(
            "redis://" + username + ":" + password + "@" + url + ":10412"
        );

        // Create the embedding model
        Map<String, String> options = Map.of("maxLength", "768", "modelMaxLength", "768");
        HuggingFaceTokenizer sentenceTokenizer = HuggingFaceTokenizer.newInstance("sentence-transformers/all-mpnet-base-v2", options);

        // Create the index
//        IndexDefinition definition = new IndexDefinition().setPrefixes(new String[]{"doc:"});
//        Map<String, Object> attr = new HashMap<>();
//        attr.put("TYPE", "FLOAT32");
//        attr.put("DIM", 768);
//        attr.put("DISTANCE_METRIC", "L2");
//        attr.put("INITIAL_CAP", 3);
//        Schema schema = new Schema().addTextField("content", 1).addTagField("genre").addHNSWVectorField("embedding", attr);
//
//        // Catch exceptions if the index exists
//        try {
//            unifiedjedis.ftCreate("vector_idx", IndexOptions.defaultOptions().setDefinition(definition), schema);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//
//
//        // Train with sentences
//        String sentence1 = "That is a very happy person";
//        unifiedjedis.hset("doc:1", Map.of("content", sentence1, "genre", "persons"));
//        unifiedjedis.hset("doc:1".getBytes(), "embedding".getBytes(), longArrayToByteArray(sentenceTokenizer.encode(sentence1).getIds()));
//
//        String sentence2 = "That is a happy dog";
//        unifiedjedis.hset("doc:2", Map.of("content", sentence2, "genre", "pets"));
//        unifiedjedis.hset("doc:2".getBytes(), "embedding".getBytes(), longArrayToByteArray(sentenceTokenizer.encode(sentence2).getIds()));
//
//        String sentence3 = "Today is a sunny day";
//        Map<String, String> doc3 = Map.of("content", sentence3, "genre", "weather");
//        unifiedjedis.hset("doc:3", doc3);
//        unifiedjedis.hset("doc:3".getBytes(), "embedding".getBytes(), longArrayToByteArray(sentenceTokenizer.encode(sentence3).getIds()));

        int K = 3;
        Query query = new Query("*=>[KNN $K @embedding $BLOB AS score]")
            .returnFields("content", "score")
            .addParam("K", K)
            .addParam("BLOB", longArrayToByteArray(sentenceTokenizer.encode(sentence).getIds()))
            .dialect(2);

        // Execute the query
        List<Document> docs = unifiedjedis.ftSearch("vector_idx", query).getDocuments();
        System.out.println(docs);
    }

}
