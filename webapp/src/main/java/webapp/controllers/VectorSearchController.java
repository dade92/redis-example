package webapp.controllers;

import adapters.VectorSearchImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class VectorSearchController {

    private final VectorSearchImpl vectorSearch;

    public VectorSearchController(
        VectorSearchImpl vectorSearch
    ) {
        this.vectorSearch = vectorSearch;
    }

    @PostMapping("/search")
    public void search() {
        try {
            vectorSearch.execute();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
