package com.qingshuihe.common.infrastructure.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:加载配置文件application中的关于redis连接池的配置信息
 * @Author: shl
 * @Date: 2022/9/7
 **/
@Component
@ConfigurationProperties(prefix = "redis-config.pool")
@Data
public class RedisPoolPropertiesConfig {

    /**
     * @Description: 密码
     * @Date: 2022/9/7
     **/
    private String password;
    /**
     * @Description: 主机ip
     * @Date: 2022/9/7
     **/
    private String host;
    /**
     * @Description: 端口
     * @Date: 2022/9/7
     **/
    private int port;
    /**
     * @Description: 设置最大连接数，默认值为8.如果赋值为-1，则表示不限制；
     * @Date: 2022/9/7
     **/
    private int maxTotal;
    /**
     * @Description: 最大空闲连接数 默认值：8
     * @Date: 2022/9/7
     **/
    private int maxIdle;
    /**
     * @Description: 最小空闲连接数 默认值：0
     * @Date: 2022/9/7
     **/
    private int minIdle;
    /**
     * @Description: 当资源池连接用尽后，获取Jedis连接的最大等待时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
     * @Date: 2022/9/7
     **/
    private int maxWaitMillis;
    /**
     * @Description: 连接空闲多久后释放, 当空闲时间大于该值 且 空闲连接大于最大空闲连接数 时直接释放, 默认值:-1L
     * @Date: 2022/9/7
     **/
    private int softMinEvictableIdleTimeMillis;
    /**
     * @Description: 向资源池借用连接时，是否做连接有效性检测，无效连接会被移除，默认值：false ，业务量很大时建议为false，因为会多一次ping的开销
     * @Date: 2022/9/7
     **/
    private boolean testOnBorrow;
    /**
     * @Description: 向资源池归还连接时，是否做连接有效性检测，无效连接会被移除，默认值：false，业务量很大时建议为false，因为会多一次ping的开销
     * @Date: 2022/9/7
     **/
    private boolean testOnReturn;
    /**
     * @Description: 自动测试池中的空闲连接是否都是可用连接(是否开启空闲资源监测，默认值：false)
     * @Date: 2022/9/7
     **/
    private boolean testWhileIdle;
    /**
     * @Description: 当需要对空闲资源进行监测时， testWhileIdle 参数开启后与下列几个参数(timeBetweenEvictionRunsMillis minEvictableIdleTimeMillis numTestsPerEvictionRun )组合完成监测任务。
     *     空闲资源的检测周期，单位为毫秒，默认值：-1，表示不检测，建议设置一个合理的值，周期性运行监测任务
     * @Date: 2022/9/7
     **/
    private int timeBetweenEvictionRunsMillis;
    /**
     * @Description: 资源池中资源最小空闲时间，单位为毫秒，默认值：30分钟（1000 60L 30L）=1800000，当达到该值后空闲资源将被移除，建议根据业务自身设定
     * @Date: 2022/9/7
     **/
    private int minEvictableIdleTimeMillis;
    /**
     * @Description: 做空闲资源检测时，每次的采样数，默认值：3，可根据自身应用连接数进行微调，如果设置为 -1，表示对所有连接做空闲监测
     * @Date: 2022/9/7
     **/
    private int numTestsPerEvictionRun;
    /**
     * @Description: 当资源池用尽后，调用者是否要等待(阻塞)。默认值：true，当为true时，maxWaitMillis参数才会生效，建议使用默认值, false会报异常
     * @Date: 2022/9/7
     **/
    private boolean blockWhenExhausted;
    /**
     * @Description: 是否启用pool的jmx管理功能, 默认true
     * @Date: 2022/9/7
     **/
    private boolean jmxEnabled;
}
