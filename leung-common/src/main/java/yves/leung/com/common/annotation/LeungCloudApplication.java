package yves.leung.com.common.annotation;

import org.springframework.context.annotation.Import;
import yves.leung.com.common.selector.LeungCloudApplicationSelector;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LeungCloudApplicationSelector.class)
public @interface LeungCloudApplication {
}