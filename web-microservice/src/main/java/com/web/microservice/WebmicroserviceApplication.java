package com.web.microservice;

import com.web.microservice.web.WebAccountsController;
import com.web.microservice.web.WebAccountsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(useDefaultFilters = false)
public class WebmicroserviceApplication {
	public static final String ACCOUNTS_SERVICE_URL = "http://ACCOUNTS-SERVICE";

	public static void main(String[] args) {
		SpringApplication.run(WebmicroserviceApplication.class, args);
	}

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}


	@Bean
	public WebAccountsService accountsService() {
		return new WebAccountsService(ACCOUNTS_SERVICE_URL);
	}

	@Bean
	public WebAccountsController accountsController() {
		return new WebAccountsController(accountsService());
	}

}
