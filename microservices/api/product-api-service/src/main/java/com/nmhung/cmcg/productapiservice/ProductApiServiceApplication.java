package com.nmhung.cmcg.productapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
public class ProductApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApiServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced // Load balance between service instances running at different ports.
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
