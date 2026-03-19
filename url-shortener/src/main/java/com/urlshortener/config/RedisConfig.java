package com.urlshortener.config;

import redis.clients.jedis.Jedis;

public class RedisConfig {
    private static final String HOST = "localhost";
    private static final String PORT = "6379";

    public static Jedis getConnection() throws Exception {
        return new Jedis(HOST, Integer.parseInt(PORT));
    }
}
