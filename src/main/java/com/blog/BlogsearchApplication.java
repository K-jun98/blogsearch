package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BlogsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogsearchApplication.class, args);
	}

}
