package com.shangan.tradeorder.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@Slf4j
public class RiskBlackListService {

    @Autowired
    private JedisPool jedisPool;

    private static final String BLACKLIST_KEY = "risk_black_list_members";

    /**
     * 将用户的ID添加到黑名单中
     * @param userId 用户的ID
     */
    public void addRiskBlackListMember(long userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sadd(BLACKLIST_KEY,String.valueOf(userId));
            log.info("Added to the risk control list with userId: {}", userId);
        }
    }

    /**
     * Check whether the user is in the black list or not
     * @param userId
     * @return If the user is in the black list -> return true, else return false
     */
    public boolean isUserInBlackList(long userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            boolean result = jedis.sismember(BLACKLIST_KEY,String.valueOf(userId));
            log.info("Whether or not in the risk control black list: {}, userId: {}", result, userId);
            return result;
        }
    }

    /**
     * 将用户的ID 从黑名单中移除
     * @param userId
     */
    public void removeRiskBlackListMember(long userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.srem(BLACKLIST_KEY,String.valueOf(userId));
            log.info("Removed from the risk control black list with userId: {}", userId);
        }
    }
}
