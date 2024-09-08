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
    public UserCache userCache(
        RedisCacheProperties redisCacheProperties
    ) {
        JedisPool jedisPool = new JedisPool(redisCacheProperties.host, 6379);

        return new RedisUserCache(jedisPool.getResource());
    }

    @Bean
    @ConditionalOnProperty(name = "enabledCache", havingValue = "memcached")
    public UserCache userCache2(
        MemcachedCacheProperties memcachedCacheProperties
    ) throws IOException {
        MemcachedClient client = new MemcachedClient(new InetSocketAddress(memcachedCacheProperties.host, 11211));

        return new MemcachedUserCache(client);
    }

}
