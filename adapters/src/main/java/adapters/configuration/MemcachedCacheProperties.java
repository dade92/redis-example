package adapters.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("memcached")
public class MemcachedCacheProperties {

    public String host;

    public void setHost(String host) {
        this.host = host;
    }
}
