package com.urlshortener.repository;

import redis.clients.jedis.Jedis;

public class RedisRepository {
    private final Jedis jedis;

    public RedisRepository(Jedis jedis) {
        this.jedis = jedis;
    }

    public void save(String code, String url) {
        jedis.set(code, url);
    }

    public String get(String code) {
        return jedis.get(code);
    }

    public void delete(String code) {
        jedis.del(code);
    }
}
