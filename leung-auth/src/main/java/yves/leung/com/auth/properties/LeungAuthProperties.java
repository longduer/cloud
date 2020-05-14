package yves.leung.com.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:leung-auth.properties"})
@ConfigurationProperties(prefix = "leung.auth")
public class LeungAuthProperties {
    private LeungClientsProperties[] clients = {};
    private int accessTokenValiditySeconds = 60 * 60 * 24;
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

    private LeungValidateCodeProperties code = new LeungValidateCodeProperties();

    // 免认证路径
    private String anonUrl;
}
