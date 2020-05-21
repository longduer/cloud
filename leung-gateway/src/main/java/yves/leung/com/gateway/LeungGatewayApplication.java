package yves.leung.com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// 若采用nacos配置中心，不需要Erueka服务查找与发现
//@EnableDiscoveryClient
@SpringBootApplication
public class LeungGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeungGatewayApplication.class, args);
    }

}
