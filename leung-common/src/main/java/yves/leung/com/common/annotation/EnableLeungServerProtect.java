package yves.leung.com.common.annotation;

import org.springframework.context.annotation.Import;
import yves.leung.com.common.configure.LeungServerProtectConfigure;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LeungServerProtectConfigure.class)
public @interface EnableLeungServerProtect {

}