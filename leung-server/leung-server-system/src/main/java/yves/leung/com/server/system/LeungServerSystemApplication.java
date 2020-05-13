package yves.leung.com.server.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import yves.leung.com.common.annotation.EnableLeungAuthExceptionHandler;

@EnableDiscoveryClient
@SpringBootApplication
//表示开启Spring Cloud Security权限注解（可以参考 https://mrbird.cc/Spring-Security-Permission.html）
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableLeungAuthExceptionHandler
public class LeungServerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeungServerSystemApplication.class, args);
    }

}
