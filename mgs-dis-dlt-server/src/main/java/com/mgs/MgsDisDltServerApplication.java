package com.mgs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan(basePackages={"com.mgs.*.mapper","com.mgs.*.*.mapper","com.mgs.*.*.*.mapper"})
@EntityScan(basePackages={"com.mgs.*.domain","com.mgs.*.*.domain"})
public class MgsDisDltServerApplication {

	public static ConfigurableApplicationContext ac;

	public static void main(String[] args) {
		MgsDisDltServerApplication.ac = SpringApplication.run(MgsDisDltServerApplication.class, args);
	}

}
