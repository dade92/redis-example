package adapters;

import data.User;
import net.spy.memcached.MemcachedClient;
import repository.UserCache;

public class MemcachedUserCache implements UserCache {

    private final MemcachedClient memcachedClient;

    public MemcachedUserCache(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    @Override
    public void add(User user) {
        memcachedClient.set(user.name(), 100, user);
    }

    @Override
    public User retrieve(String name) {
        return (User) memcachedClient.get(name);
    }

}

