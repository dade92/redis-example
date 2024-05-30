package adapters.configuration;

import adapters.VectorSearchImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RedisCacheProperties.class)
public class VectorSearchConfiguration {


    @Bean
    public VectorSearchImpl vectorSearch(
        RedisCacheProperties redisCacheProperties
    ) {
        return new VectorSearchImpl(
            redisCacheProperties.host,
            redisCacheProperties.user,
            redisCacheProperties.password
        );
    }

}
