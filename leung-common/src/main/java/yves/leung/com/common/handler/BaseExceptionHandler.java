package yves.leung.com.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import yves.leung.com.common.entity.LeungResponse;
import yves.leung.com.common.exception.LeugnAuthException;

@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public LeungResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return new LeungResponse().message("系统内部异常");
    }

    @ExceptionHandler(value = LeugnAuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public LeungResponse handleFebsAuthException(LeugnAuthException e) {
        log.error("系统错误", e);
        return new LeungResponse().message(e.getMessage());
    }
    
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public LeungResponse handleAccessDeniedException(){
        return new LeungResponse().message("没有权限访问该资源");
    }
}