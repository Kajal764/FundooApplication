package com.fundoo.user.service;

import org.springframework.beans.factory.annotation.Value;
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

    public String getToken(String clientId) {
        System.out.println(clientId);
        System.out.println(jedis.hget(clientId, TOKEN));
        return jedis.hget(clientId, TOKEN);
    }

}
