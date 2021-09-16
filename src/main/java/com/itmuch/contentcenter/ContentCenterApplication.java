package com.itmuch.contentcenter;

import com.itmuch.contentcenter.configuration.GlobalFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.itmuch.contentcenter")
@SpringBootApplication
//defaultConfiguration实现feign全局配置
@EnableFeignClients//(defaultConfiguration = GlobalFeignConfiguration.class)
public class ContentCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentCenterApplication.class, args);
	}

	@Bean
	@LoadBalanced
	//可以配置异常处理，类似sentinelResource
	@SentinelRestTemplate
	public RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
}
