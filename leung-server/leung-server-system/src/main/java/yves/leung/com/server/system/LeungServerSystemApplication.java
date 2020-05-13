package yves.leung.com.server.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import yves.leung.com.common.annotation.EnableLeungAuthExceptionHandler;
import yves.leung.com.common.annotation.EnableLeungOauth2FeignClient;
import yves.leung.com.common.annotation.EnableLeungServerProtect;
import yves.leung.com.common.annotation.LeungCloudApplication;

@EnableDiscoveryClient
@SpringBootApplication
//表示开启Spring Cloud Security权限注解（可以参考 https://mrbird.cc/Spring-Security-Permission.html）
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableLeungServerProtect
//@EnableLeungOauth2FeignClient
//@EnableLeungAuthExceptionHandler
@LeungCloudApplication
public class LeungServerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeungServerSystemApplication.class, args);
    }

}
