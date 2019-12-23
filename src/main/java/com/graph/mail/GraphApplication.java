package com.graph.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.graph.mail.common.HttpsClientRequestFactory;


@SpringBootApplication
public class GraphApplication {
	@Bean
	public RestTemplate httpsRestTemplate() {
		RestTemplate restClient = new RestTemplate(new HttpsClientRequestFactory());
		return restClient;
	}
	public static void main(String[] args) {
		SpringApplication.run(GraphApplication.class, args);
	}
	 
}
