package yves.leung.com.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import yves.leung.com.common.annotation.EnableLeungLettuceRedis;
import yves.leung.com.common.annotation.LeungCloudApplication;

// 若采用nacos配置中心，不需要Erueka服务查找与发现
//@EnableDiscoveryClient
@SpringBootApplication
/**
 * 通过该注解，leung-auth模块的IOC容器里就已经注册了FebsAccessDeniedHandler和FebsAuthExceptionEntryPoint
 */
//@EnableLeungServerProtect
//@EnableLeungOauth2FeignClient
//@EnableLeungAuthExceptionHandler
@LeungCloudApplication
@MapperScan("yves.leung.com.auth.mapper") //Mapper类都注册到IOC容器中。
@EnableLeungLettuceRedis
public class LeungAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeungAuthApplication.class, args);
    }

}
