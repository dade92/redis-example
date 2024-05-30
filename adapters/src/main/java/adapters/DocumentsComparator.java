package adapters;

import redis.clients.jedis.search.Document;

import java.util.Comparator;

public class DocumentsComparator implements Comparator<Document> {
    @Override
    public int compare(Document o1, Document o2) {
        return Integer.compare(
            Integer.parseInt((String) o1.get("score")),
            Integer.parseInt((String) o2.get("score"))
        );
    }
}
