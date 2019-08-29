package com.mgs.meituan.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:request.properties")
public class RequestConfig {

    /**
     * 请求数据库一次的最大数据量
     */
    @Value("${request.pageSize}")
    private Integer pageSize;

    /**
     * 每个线程处理的数据
     */
    @Value("${request.threadSize}")
    private Integer threadSize;

    /**
     * 处理增量的数（1分钟内)
     */
    @Value("${handler.increment.number}")
    private Integer incrementNumber;
}
