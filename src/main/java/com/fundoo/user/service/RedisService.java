package com.fundoo.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisService {

    private static Jedis jedis = new Jedis();

    @Value("${redis.token}")
    private static String TOKEN;

    public static void setToken(String clientId, String jwtToken) {
        jedis.hset(clientId, TOKEN, jwtToken);
    }

    public String getToken(String clientId) {
        return jedis.hget(clientId, TOKEN);
    }

}
