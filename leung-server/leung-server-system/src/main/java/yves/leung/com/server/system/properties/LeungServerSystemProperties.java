package yves.leung.com.server.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:leung-server-system.properties"})
@ConfigurationProperties(prefix = "leung.server.system")
public class LeungServerSystemProperties {

    /**
     * 免认证 URI，多个值的话以逗号分隔
     */
    private String anonUrl;

    private LeungSwaggerProperties swagger = new LeungSwaggerProperties();
}