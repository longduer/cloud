package yves.leung.com.server.system.configure;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import yves.leung.com.common.handler.LeungAccessDeniedHandler;
import yves.leung.com.common.handler.LeungAuthExceptionEntryPoint;
import yves.leung.com.server.system.properties.LeungServerSystemProperties;

@Configuration
@EnableResourceServer
public class LeungServerSystemResourceServerConfigure extends ResourceServerConfigurerAdapter {

    @Autowired
    private LeungAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private LeungAuthExceptionEntryPoint exceptionEntryPoint;

    @Autowired
    private LeungServerSystemProperties properties;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");

        http.csrf().disable()
                .requestMatchers().antMatchers("/**") //该安全配置对所有请求都生效
                .and()
                .authorizeRequests().antMatchers("/actuator/**").permitAll() // 免认证
                .and()
                .authorizeRequests().antMatchers(anonUrls).permitAll() // 免认证
                .antMatchers("/**").authenticated();
    }

    /***
     * 在资源服务中配置自定义异常处理 详见common包中注解EnableLeungAuthExceptionHandler，资源服务中都会引用此段代码
     * @param resources
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
