package yves.leung.com.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;
import yves.leung.com.common.entity.LeungConstant;
import yves.leung.com.common.entity.LeungResponse;
import yves.leung.com.common.handler.LeungAuthExceptionEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截只能通过网关进行服务访问，而不是直接跳级访问
 */
public class LeungServerProtectInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 从请求头中获取 Zuul Token
        String token = request.getHeader(LeungConstant.ZUUL_TOKEN_HEADER);
        String zuulToken = new String(Base64Utils.encode(LeungConstant.ZUUL_TOKEN_VALUE.getBytes()));
        // 校验 Zuul Token的正确性
        if (StringUtils.equals(zuulToken, token)) {
            return true;
        } else {
            LeungResponse leungResponse = new LeungResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(JSONObject.toJSONString(leungResponse.message("请通过网关获取资源")));
            return false;
        }
    }
}
