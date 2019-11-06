package com.yash.es.Appmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.yash.es.*"})
public class SpringWithElasticSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWithElasticSearchApplication.class, args);
	}

}

