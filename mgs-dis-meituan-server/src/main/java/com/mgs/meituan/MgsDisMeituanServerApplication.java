package com.mgs.meituan;

import com.mgs.dis.remote.DisConfigRemote;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.mgs.*.remote"})
@EnableDiscoveryClient
@MapperScan(basePackages={"com.mgs.*.mapper","com.mgs.*.*.mapper","com.mgs.*.*.*.mapper"})
@EntityScan(basePackages={"com.mgs.*.domain","com.mgs.*.*.domain"})
@EnableAsync
public class MgsDisMeituanServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MgsDisMeituanServerApplication.class, args);
    }

}
