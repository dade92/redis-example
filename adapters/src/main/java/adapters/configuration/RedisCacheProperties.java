package adapters.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("redis")
public class RedisCacheProperties {

    public String host;
    public int port;
    public String user;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
