package adapters;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

class VectorSearchImplTest {


    private VectorSearchImpl vectorSearch = new VectorSearchImpl(
        "redis-10412.c293.eu-central-1-1.ec2.redns.redis-cloud.com",
        "default",
        "2h45mE827C31rCbEEMl1HmPzLfkC3MBl"
    );

    @Test
    void execute() throws URISyntaxException {
        vectorSearch.execute("That is a dog");
    }
}