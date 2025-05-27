package com.realworld.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "app")
@ConstructorBinding
public class Config {
    private final String secretKey;
    private final HttpConfig http;
    private final DatabaseConfig database;

    public Config(String secretKey, HttpConfig http, DatabaseConfig database) {
        this.secretKey = secretKey;
        this.http = http;
        this.database = database;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public HttpConfig getHttp() {
        return http;
    }

    public DatabaseConfig getDatabase() {
        return database;
    }

    @ConfigurationProperties(prefix = "app.http")
    public static class HttpConfig {
        private final String host;
        private final int port;

        public HttpConfig(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }
    }

    @ConfigurationProperties(prefix = "app.database")
    public static class DatabaseConfig {
        private final String host;
        private final String port;
        private final String db;
        private final String username;
        private final String password;

        public DatabaseConfig(String host, String port, String db, String username, String password) {
            this.host = host;
            this.port = port;
            this.db = db;
            this.username = username;
            this.password = password;
        }

        public String getHost() {
            return host;
        }

        public String getPort() {
            return port;
        }

        public String getDb() {
            return db;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}