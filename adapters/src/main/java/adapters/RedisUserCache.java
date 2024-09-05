package adapters;

import adapters.utils.SerializationUtils;
import data.User;
import redis.clients.jedis.Jedis;
import repository.UserCache;

import java.io.IOException;

public class RedisUserCache implements UserCache {

    private final Jedis jedis;

    public RedisUserCache(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public void add(User user) {
        try {
            jedis.set(user.name().getBytes(), SerializationUtils.serialize(user));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User retrieve(String name) {
        try {
            byte[] user = jedis.get(name.getBytes());
            if (user != null) {
                return (User) SerializationUtils.deserialize(user);
            } else {
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

