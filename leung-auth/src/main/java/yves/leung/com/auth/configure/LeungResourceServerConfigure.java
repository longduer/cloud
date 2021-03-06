package yves.leung.com.auth.configure;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import yves.leung.com.auth.properties.LeungAuthProperties;
import yves.leung.com.common.handler.LeungAccessDeniedHandler;
import yves.leung.com.common.handler.LeungAuthExceptionEntryPoint;

/**
 * FebsResourceServerConfigure用于处理非/oauth/开头的请求，其主要用于资源的保护，
 * 客户端只能通过OAuth2协议发放的令牌来从资源服务器中获取受保护的资源。
 */
@Configuration
@EnableResourceServer
public class LeungResourceServerConfigure extends ResourceServerConfigurerAdapter {

    @Autowired
    private LeungAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private LeungAuthExceptionEntryPoint exceptionEntryPoint;

    @Autowired
    private LeungAuthProperties properties;



    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");

        http.csrf().disable() //无需携带令牌
                .requestMatchers().antMatchers("/**") //该安全配置对所有请求都生效
                .and()
                .authorizeRequests().antMatchers(anonUrls).permitAll() //免费认证
                .and()
                .authorizeRequests().antMatchers("/actuator/**").permitAll() //SBA监控接口免认证
                .antMatchers("/**").authenticated() //其它都需要认证
                .and().httpBasic();
    }

    /***
     * 在资源服务中配置自定义异常处理 详见common包中注解EnableLeungAuthExceptionHandler
     * @param resources
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
