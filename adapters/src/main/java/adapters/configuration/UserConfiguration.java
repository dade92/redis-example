package adapters.configuration;

import adapters.MemcachedUserCache;
import adapters.RedisUserCache;
import net.spy.memcached.MemcachedClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import repository.UserCache;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@EnableConfigurationProperties({RedisCacheProperties.class, MemcachedCacheProperties.class})
public class UserConfiguration {

    @Bean
    @ConditionalOnProperty(name = "enabledCache", havingValue = "redis")
    public UserCache redisUserCache(
        RedisCacheProperties redisCacheProperties
    ) {
        JedisPool jedisPool = new JedisPool(redisCacheProperties.host, redisCacheProperties.port);

        return new RedisUserCache(jedisPool.getResource());
    }

    @Bean
    @ConditionalOnProperty(name = "enabledCache", havingValue = "memcached")
    public UserCache memcachedUserCache(
        MemcachedCacheProperties memcachedCacheProperties
    ) throws IOException {
        MemcachedClient client = new MemcachedClient(
            new InetSocketAddress(memcachedCacheProperties.host, memcachedCacheProperties.port)
        );

        return new MemcachedUserCache(client);
    }

}
