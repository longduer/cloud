package yves.leung.com.server.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import yves.leung.com.common.annotation.LeungCloudApplication;
// 若采用nacos配置中心，不需要Erueka服务查找与发现
//@EnableDiscoveryClient
@SpringBootApplication
//表示开启Spring Cloud Security权限注解（可以参考 https://mrbird.cc/Spring-Security-Permission.html）
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableLeungServerProtect
//@EnableLeungOauth2FeignClient
//@EnableLeungAuthExceptionHandler
@LeungCloudApplication
@MapperScan("yves.leung.com.server.system.mapper")
@EnableTransactionManagement
@EnableSwagger2
public class LeungServerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeungServerSystemApplication.class, args);
    }

}
