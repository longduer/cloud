package yves.leung.com.common.configure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import yves.leung.com.common.handler.LeungAccessDeniedHandler;
import yves.leung.com.common.handler.LeungAuthExceptionEntryPoint;

/**
 * 在该配置类中，我们注册了FebsAccessDeniedHandler和FebsAuthExceptionEntryPoint。
 * @ConditionalOnMissingBean注解的意思是，当IOC容器中没有指定名称或类型的Bean的时候，就注册它。
 * 以@ConditionalOnMissingBean(name = "accessDeniedHandler")为例，
 * 当微服务系统的Spring IOC容器中没有名称为accessDeniedHandler的Bean的时候，就将LeungAccessDeniedHandler注册为一个Bean。
 * 这样做的好处在于，子系统可以自定义自个儿的资源服务器异常处理器，覆盖我们在 leung-common通用模块里定义的。
 */
public class LeungAuthExceptionConfigure {
    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public LeungAccessDeniedHandler accessDeniedHandler() {
        return new LeungAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public LeungAuthExceptionEntryPoint authenticationEntryPoint() {
        return new LeungAuthExceptionEntryPoint();
    }
}
