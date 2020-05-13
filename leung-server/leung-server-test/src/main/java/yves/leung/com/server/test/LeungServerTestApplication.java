package yves.leung.com.server.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import yves.leung.com.common.annotation.EnableLeungAuthExceptionHandler;

@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableLeungAuthExceptionHandler
public class LeungServerTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeungServerTestApplication.class, args);
    }

}
