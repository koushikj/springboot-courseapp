package com.coursepoject.com.redisclient;

import redis.clients.jedis.Jedis;

public class RedisClient {
    public static void main(String[] args) {
        //Connecting to Redis server on localhost
        Jedis jedis = new Jedis("10.126.68.62");
        jedis.select(0);
        jedis.set("y","12");
        //System.out.println(jedis.get("y"));
        System.out.println("Connection to server sucessfully");
        //check whether server is running or not
        System.out.println("Server is running: "+jedis.ping());
    }
}
