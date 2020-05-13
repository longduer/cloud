package yves.leung.com.common.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import yves.leung.com.common.configure.LeungAuthExceptionConfigure;
import yves.leung.com.common.configure.LeungOAuth2FeignConfigure;
import yves.leung.com.common.configure.LeungServerProtectConfigure;

public class LeungCloudApplicationSelector implements ImportSelector {

    /*
    @EnableFebsServerProtect，开启微服务防护，避免客户端绕过网关直接请求微服务；
    @EnableFebsOauth2FeignClient，开启带令牌的Feign请求，避免微服务内部调用出现401异常；
    @EnableFebsAuthExceptionHandler，认证类型异常翻译。
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                LeungAuthExceptionConfigure.class.getName(),
                LeungOAuth2FeignConfigure.class.getName(),
                LeungServerProtectConfigure.class.getName()
        };
    }
}
