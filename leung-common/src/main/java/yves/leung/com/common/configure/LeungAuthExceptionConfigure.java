package yves.leung.com.common.configure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import yves.leung.com.common.handler.LeungAccessDeniedHandler;
import yves.leung.com.common.handler.LeungAuthExceptionEntryPoint;

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
