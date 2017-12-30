package cn.don9cn.blog.autoconfigure.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: liuxindong
 * @Description: mongo客户端基础配置
 * @Create: 2017/10/30 8:24
 * @Modify:
 */
@Component
@ConfigurationProperties(prefix = "mongo")
public class MongoClientProperties {

    private String host;

    private int port;

    private String database;

    private String username;

    private String password;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
