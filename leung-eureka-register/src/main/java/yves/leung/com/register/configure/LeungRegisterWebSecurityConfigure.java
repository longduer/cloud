package yves.leung.com.register.configure;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class LeungRegisterWebSecurityConfigure extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF(Cross-site request forgery) 是指跨站请求伪造，是 web 常见的攻击之一，
        // 从 Spring Security 4.0 开始，默认情况下 security 会启用 CSRF 保护，以防止 CSRF 攻击应用程序。

        // 默认情况下，当 spring security 在类路径上时，它要求用户向应用程序发送请求时，
        // 都必须携带一个有效的 csrf 令牌，而 Eureka 客户端通常不会拥有有效的跨站点请求（CSRF）令牌，此时 Eureka Serer 端应该对 eureka 的请求路径放行。
        http.csrf().ignoringAntMatchers("/eureka/**")
                .and()
                .authorizeRequests().antMatchers("/actuator/**").permitAll();
        super.configure(http);
    }
}