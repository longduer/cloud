package yves.leung.com.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import yves.leung.com.common.annotation.EnableLeungAuthExceptionHandler;

@EnableDiscoveryClient
@SpringBootApplication
@EnableLeungAuthExceptionHandler
public class LeungAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeungAuthApplication.class, args);
    }

}
