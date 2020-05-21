package yves.leung.com.gateway.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

// 若采用nacos配置中心，不需要Erueka服务查找与发现
//@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class LeungGatewayZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeungGatewayZuulApplication.class, args);
    }

}
