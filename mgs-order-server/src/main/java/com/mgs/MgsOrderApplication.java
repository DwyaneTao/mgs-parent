package com.mgs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Auther: Owen
 * @Date: 2019/4/23 19:09
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan(basePackages={"com.mgs.*.mapper","com.mgs.*.*.mapper","com.mgs.*.*.*.mapper"})
@EntityScan(basePackages={"com.mgs.*.domain","com.mgs.*.*.domain"})
@EnableAsync
public class MgsOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MgsOrderApplication.class, args);
    }
}
