package adapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.User;
import redis.clients.jedis.Jedis;
import repository.UserCache;

public class RedisUserCache implements UserCache {

    private final Jedis jedis;
    private final ObjectMapper objectMapper;

    public RedisUserCache(Jedis jedis, ObjectMapper objectMapper) {
        this.jedis = jedis;
        this.objectMapper = objectMapper;
    }

    @Override
    public void add(User user) {
        try {
            jedis.set(user.name(), objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {

        }
    }

    @Override
    public User retrieve(String name) {
        try {
            return objectMapper.readValue(jedis.get(name), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

