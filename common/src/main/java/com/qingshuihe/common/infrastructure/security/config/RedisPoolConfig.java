package com.qingshuihe.common.infrastructure.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description:初始化redis连接池
 * @Author: shl
 * @Date: 2022/9/7
 **/
@Configuration
public class RedisPoolConfig {

    @Autowired
    private RedisPoolPropertiesConfig redisPoolPropertiesConfig;

    @Bean
    public JedisPoolConfig initPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 设置最大连接数，默认值为8.如果赋值为-1，则表示不限制；
        poolConfig.setMaxTotal(redisPoolPropertiesConfig.getMaxTotal());
        // 最大空闲连接数
        poolConfig.setMaxIdle(redisPoolPropertiesConfig.getMaxIdle());
        // 最小空闲连接数
        poolConfig.setMinIdle(redisPoolPropertiesConfig.getMinIdle());
        // 获取Jedis连接的最大等待时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
        poolConfig.setMaxWaitMillis(redisPoolPropertiesConfig.getMaxWaitMillis());
        // 每次释放连接的最大数目
        poolConfig.setNumTestsPerEvictionRun(redisPoolPropertiesConfig.getNumTestsPerEvictionRun());
        // 释放连接的扫描间隔（毫秒）,如果为负数,则不运行逐出线程, 默认-1
        poolConfig.setTimeBetweenEvictionRunsMillis(redisPoolPropertiesConfig.getTimeBetweenEvictionRunsMillis());
        // 连接最小空闲时间
        poolConfig.setMinEvictableIdleTimeMillis(redisPoolPropertiesConfig.getMinEvictableIdleTimeMillis());
        // 连接空闲多久后释放, 当空闲时间&gt;该值 且 空闲连接&gt;最大空闲连接数 时直接释放
        poolConfig.setSoftMinEvictableIdleTimeMillis(redisPoolPropertiesConfig.getSoftMinEvictableIdleTimeMillis());
        // 在获取Jedis连接时，自动检验连接是否可用
        poolConfig.setTestOnBorrow(redisPoolPropertiesConfig.isTestOnBorrow());
        // 在将连接放回池中前，自动检验连接是否有效
        poolConfig.setTestOnReturn(redisPoolPropertiesConfig.isTestOnReturn());
        // 自动测试池中的空闲连接是否都是可用连接
        poolConfig.setTestWhileIdle(redisPoolPropertiesConfig.isTestWhileIdle());
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        poolConfig.setBlockWhenExhausted(redisPoolPropertiesConfig.isBlockWhenExhausted());
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        poolConfig.setNumTestsPerEvictionRun(redisPoolPropertiesConfig.getNumTestsPerEvictionRun());
        return poolConfig;
    }



    @Bean
    public JedisConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisPoolPropertiesConfig.getHost(), redisPoolPropertiesConfig.getPort());
        config.setPassword(redisPoolPropertiesConfig.getPassword());
        JedisConnectionFactory  redisConnectionFactory = new JedisConnectionFactory(config);
        redisConnectionFactory.setPoolConfig(initPoolConfig());
        return redisConnectionFactory;
    }
}
