package com.fh.Test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class TestRedis {
    @Test
    public void tsetRedis(){
        Jedis jedis = new Jedis("192.168.239.132");
        Set<String> keys = jedis.keys("*");
        for (String key:keys){
           //System.out.println(key);
            String type = jedis.type(key);
            if ("list".equals(type)){
                List<String> lrange = jedis.lrange(key, 0, -1);
                for(String lr:lrange){
                    System.out.println(lr);
                }
            }else if("hash".equals(type)){
                List<String> hvals = jedis.hvals(key);
                for(String hk:hvals){
                    System.out.println(hk);
                }
            }
        }
    }
}
