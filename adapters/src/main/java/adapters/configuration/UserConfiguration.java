package adapters.configuration;

import adapters.RedisUserCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import repository.UserCache;

@Configuration
public class UserConfiguration {

    @Bean
    public UserCache userCache() {
        JedisPool jedisPool = new JedisPool(
            "redis-10412.c293.eu-central-1-1.ec2.redns.redis-cloud.com",
            10412,
            "default",
            "2h45mE827C31rCbEEMl1HmPzLfkC3MBl"
        );

        return new RedisUserCache(jedisPool.getResource());
    }

}
