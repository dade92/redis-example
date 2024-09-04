package adapters.configuration;

import adapters.RedisUserCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import repository.UserCache;

@Configuration
@EnableConfigurationProperties(RedisCacheProperties.class)
public class UserConfiguration {

    @Bean
    public UserCache userCache(
        RedisCacheProperties redisCacheProperties
    ) {
        JedisPool jedisPool = new JedisPool(
            redisCacheProperties.host,
            6379
        );

        return new RedisUserCache(jedisPool.getResource(), new ObjectMapper());
    }

}
