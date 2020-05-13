package yves.leung.com.server.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import yves.leung.com.common.annotation.EnableLeungAuthExceptionHandler;
import yves.leung.com.common.annotation.EnableLeungOauth2FeignClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableLeungAuthExceptionHandler
// 开启fegin客户端，此时需要测试访问server-system
@EnableFeignClients
// 拦截Feign请求，手动往请求头上加入令牌即可
@EnableLeungOauth2FeignClient
public class LeungServerTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeungServerTestApplication.class, args);
    }

}
