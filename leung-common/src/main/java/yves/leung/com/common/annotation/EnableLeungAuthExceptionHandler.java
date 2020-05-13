package yves.leung.com.common.annotation;

import org.springframework.context.annotation.Import;
import yves.leung.com.common.configure.LeungAuthExceptionConfigure;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LeungAuthExceptionConfigure.class)
public @interface EnableLeungAuthExceptionHandler {
}