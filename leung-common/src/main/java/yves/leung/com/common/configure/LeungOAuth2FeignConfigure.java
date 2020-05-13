package yves.leung.com.common.configure;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * 因为Feign在调用远程服务的时候，并不会帮我们把原HTTP请求头部的内容也携带上，所以访问febs-server-system的/hello服务的时候，请求头部没有访问令牌，于是抛出了401异常。
 * 要解决上面的问题也很简单，只需要拦截Feign请求，手动往请求头上加入令牌即可。
 *
 * 在leung-common模块的yves.leung.common.configure路径下新建FebsOAuth2FeignConfigure配置类，在该配置类里注册一个Feign请求拦截器：
 */
public class LeungOAuth2FeignConfigure {
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof OAuth2AuthenticationDetails) {
                String authorizationToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
                requestTemplate.header(HttpHeaders.AUTHORIZATION, "bearer " + authorizationToken);
            }
        };
    }
}
