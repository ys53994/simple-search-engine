package com.ys53994.sse.client;

import com.ys53994.sse.client.cli.CliManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Client implements CommandLineRunner {

    @Autowired
    private CliManager cliHandler;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        new SpringApplication(Client.class).run(args);
    }

    @Override
    public void run(String... args) {
        cliHandler.handle(args);
    }

}
