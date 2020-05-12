package yves.leung.com.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class LeungEurekaRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeungEurekaRegisterApplication.class, args);
    }

}
