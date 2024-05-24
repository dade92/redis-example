package adapters;

import data.User;
import redis.clients.jedis.Jedis;
import repository.UserCache;

public class RedisUserCache implements UserCache {

    private final Jedis jedis;

    public RedisUserCache(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public User retrieve(String key) {
        return new User(
            jedis.get(key)
        );
    }
}

