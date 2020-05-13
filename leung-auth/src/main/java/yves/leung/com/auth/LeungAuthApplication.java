package yves.leung.com.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import yves.leung.com.common.annotation.EnableLeungAuthExceptionHandler;
import yves.leung.com.common.annotation.EnableLeungServerProtect;
import yves.leung.com.common.annotation.LeungCloudApplication;

@EnableDiscoveryClient
@SpringBootApplication
/**
 * 通过该注解，leung-auth模块的IOC容器里就已经注册了FebsAccessDeniedHandler和FebsAuthExceptionEntryPoint
 */
//@EnableLeungServerProtect
//@EnableLeungOauth2FeignClient
//@EnableLeungAuthExceptionHandler
@LeungCloudApplication
public class LeungAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeungAuthApplication.class, args);
    }

}
