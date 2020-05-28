package yves.leung.com.auth.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import yves.leung.com.auth.filter.ValidateCodeFilter;
import yves.leung.com.auth.service.LeungUserDetailService;

/***
 * FebsSecurityConfigure用于处理/oauth开头的请求，Spring Cloud OAuth内部定义的获取令牌，
 * 刷新令牌的请求地址都是以/oauth/开头的，也就是说FebsSecurityConfigure用于处理和令牌相关的请求；
 */

//同时出现 认证过滤器（oauth/*相关请求）与资源过滤器LeungResourceServerConfigure（/*），我们希望所有请求先通过认证过滤器，
// 所以oauth的排行级设置为2，高于资源过滤器LeungResourceServerConfigure的优先级10要高。（数字越小，优先级越高）
@Order(2) // WebSecurityConfigurerAdapter 过滤器默认的优秀级是：@Order(100)
@EnableWebSecurity
public class LeungSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private LeungUserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .requestMatchers()
                .antMatchers("/oauth/**") //安全配置类只对/oauth/开头的请求有效
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .csrf().disable(); // 禁用csrf功能 即无令牌访问 因为通过用户名和密码才能获得到令牌

        // CSRF(Cross-site request forgery) 是指跨站请求伪造，是 web 常见的攻击之一，
        // 从 Spring Security 4.0 开始，默认情况下 security 会启用 CSRF 保护，以防止 CSRF 攻击应用程序。

        // 默认情况下，当 spring security 在类路径上时，它要求用户向应用程序发送请求时，
        // 都必须携带一个有效的 csrf 令牌，而 Eureka 客户端通常不会拥有有效的跨站点请求（CSRF）令牌，此时 Eureka Serer 端应该对 eureka 的请求路径放行。

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

}
