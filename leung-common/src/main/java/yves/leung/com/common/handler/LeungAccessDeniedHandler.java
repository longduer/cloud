package yves.leung.com.common.handler;

import org.springframework.http.MediaType;
import org.springframework.security.web.access.AccessDeniedHandler;
import yves.leung.com.common.entity.LeungResponse;
import yves.leung.com.common.utils.LeungUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class LeungAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, org.springframework.security.access.AccessDeniedException e) throws IOException, ServletException {
        LeungResponse febsResponse = new LeungResponse();
        LeungUtil.makeResponse(
                httpServletResponse, MediaType.APPLICATION_JSON_UTF8_VALUE,
                HttpServletResponse.SC_FORBIDDEN, febsResponse.message("没有权限访问该资源"));
    }
}