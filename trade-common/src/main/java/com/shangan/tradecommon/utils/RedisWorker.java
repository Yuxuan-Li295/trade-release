package com.shangan.tradecommon.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

@Slf4j
@Service
public class RedisWorker {
    @Autowired
    private JedisPool jedisPool;

    public void setKeyValue(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key,value);
            jedis.close();
        } catch (Exception e) {
            //Handle exception
            e.printStackTrace();
        }
    }

    public void setValue(String key, Long value) {
        Jedis jedisClient = jedisPool.getResource();
        jedisClient.set(key, value.toString());
        jedisClient.close();
    }

    /*
     * Function overloading
     */
    public void setValue(String key, String value) {
        Jedis jedisClient = jedisPool.getResource();
        jedisClient.set(key, value);
        jedisClient.close();
    }

    public String getValueByKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String value = jedis.get(key);
            jedis.close();
            return value;
        } catch (Exception e) {
            //Handle exception
            e.printStackTrace();
        }
        return null;
    }

    /*
    Redis conduct Lua Script: judge the available stock and conduct the stock deduction
    Check and deducts stock atomically using a Lua Script in Redis
    @param: key the Redis key to check and decrement
    @return: true if the stock was successfully decremented, false otherwise
     */
    public boolean stockDeductCheck(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // Lua script to check and decrement stock
            String script = "if redis.call('exists',KEYS[1]) == 1 then\n" +
                    "                 local availableStock = tonumber(redis.call('get', KEYS[1]))\n" +
                    "                 if( availableStock <=0 ) then\n" +
                    "                    return -1\n" +
                    "                 end;\n" +
                    "                 redis.call('decr',KEYS[1]);\n" +
                    "                 return availableStock - 1;\n" +
                    "             end;\n" +
                    "             return -1;";
            //Run the Lua script in Redis and interpret the result
            long scriptResult = (Long) jedis.eval(script,Collections.singletonList(key), Collections.emptyList());
            if (scriptResult < 0) {
                log.info("库存不足，抢购不成功，下次再来");
                return false;
            } else {
                log.info("恭喜你！抢购成功!");
            }
            return true;
        } catch (Exception e) {
            log.error("Error during stock deduction:{}", e.getMessage());
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
