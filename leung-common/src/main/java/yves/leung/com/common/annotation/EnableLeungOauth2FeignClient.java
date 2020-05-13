package yves.leung.com.common.annotation;

import org.springframework.context.annotation.Import;
import yves.leung.com.common.configure.LeungOAuth2FeignConfigure;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LeungOAuth2FeignConfigure.class)
public @interface EnableLeungOauth2FeignClient {
}