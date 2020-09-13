package com.fundoo.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisService {

    private static Jedis jedis = new Jedis();
    private static String TOKEN = "jwtToken";

    public static void setToken(String clientId, String jwtToken) {
        jedis.hset(clientId, TOKEN, jwtToken);
    }

    public static String getToken(String clientId) {
        return jedis.hget(clientId, TOKEN);
    }

}
