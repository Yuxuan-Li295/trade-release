package com.shangan.tradelightningdeal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig extends CachingConfigurerSupport {
    private Logger logger = LoggerFactory.getLogger(JedisConfig.class);

    //Redis Server location
    private final String host = "localhost";

    //Redis Server Connection Port
    private final int port = 6379;

    //Connection timeout（millisceonds） maximum wait time befroe make connection
    //Negative value means infinite waiting
    private final int timeout = 5000;

    //Connection pool maximum connections
    //Negative value means no restrictions
    private final int maxActive = 8;

    //Maximum idle connection in the connection pool
    private final int maxIdle = 8;

    //Minimum idle connection in the connection pool
    private final int minIdle = 0;

    //Maximum blocking waiting time for the connection pool
    //Negative value means no restrictions
    private final long maxWaitMillis = -1;

    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMinIdle(minIdle);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, null);

        logger.info("JedisPool注入成功！");
        logger.info("redis地址：" + host + ":" + port);
        return jedisPool;
    }
}
