package com.fundoo.user.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisService {

    private static Jedis jedis = new Jedis();

  //  @Value("${redis.token}")
    private static String TOKEN = "jwtToken";

    public static void setToken(String clientId, String jwtToken) {
        jedis.hset(clientId, TOKEN, jwtToken);
    }

    synchronized public String getToken(String clientId) {
        return jedis.hget(clientId, TOKEN);
    }

}
