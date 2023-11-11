package com.shangan.tradeorder.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Slf4j
@Service
public class LimitBuyService {
    private static final String LIMIT_MEMBER_KEY_PREFIX = "seckill_activity_members:";

    @Autowired
    private JedisPool jedisPool;

    public void addLimitMember(long seckillActivityId, long userId) {
        //Construct the Redis key using a consistent refix and the seckill activity ID
        String limitKey = LIMIT_MEMBER_KEY_PREFIX + seckillActivityId;
        //Use Jedis Connection from the pool
        try (Jedis jedisClient = jedisPool.getResource()) {
            //Add the user ID to the set associated with seckill activity
            jedisClient.sadd(limitKey, String.valueOf(userId));
        } catch (Exception e) {
            // Handle the exception and log the error
            log.error("Error adding user to limit list: userId={}, seckillActivityId={}, error={}",
                    userId, seckillActivityId, e.getMessage());
        }
        // Log the action of adding a user to the limit list
        log.info("Added to limited buying list: userId={} seckillActivityId={}", userId, seckillActivityId);
    }

    public void removeLimitMember(long seckillActivityId, long userId) {
        String limitKey = LIMIT_MEMBER_KEY_PREFIX + seckillActivityId;
        try (Jedis jedis = jedisPool.getResource()) {
            //Remove the user ID from the set associated with the seckill activity
            jedis.srem(limitKey, String.valueOf(userId));
        } catch (Exception e) {
            // Handle the exception and log the error
            log.error("Error removing user from limit list: userId={}, seckillActivityId ={}, error = {}", userId, seckillActivityId, e.getMessage());
        }
        log.info("Removed from limited buying list: userId = {} seckillActivityId = {}", userId, seckillActivityId);
    }

    public boolean isInLimitMember(long seckillActivityId, long userId) {
        String limitKey = LIMIT_MEMBER_KEY_PREFIX + seckillActivityId;
        try (Jedis jedis = jedisPool.getResource()) {
            boolean result = jedis.sismember(limitKey, String.valueOf(userId));
            log.info("是否在限购名单中:{} userId:{}, seckillActivityId:{}", result, userId, seckillActivityId);
            return result;
        } catch (Exception e) {
            // Handle the exception and log the error
            log.error("Error checking user in limit list: userId={}, seckillActivityId ={}, error = {}", userId, seckillActivityId, e.getMessage());
        }
        // In case of an exception, return false as a safe default
        return false;
    }
}
