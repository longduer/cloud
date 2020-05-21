package yves.leung.com.gateway.filter;

import com.netflix.zuul.context.RequestContext;
import io.lettuce.core.dynamic.support.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import yves.leung.com.common.entity.LeungResponse;
import yves.leung.com.common.utils.LeungUtil;

import javax.servlet.http.HttpServletResponse;

/***
 * 处理Zuul异常
 * 当Zuul转发请求超时时，系统返回如下响应：
 *
 * {
 *     "timestamp": "2019-08-07T07:58:21.938+0000",
 *     "status": 504,
 *     "error": "Gateway Timeout",
 *     "message": "com.netflix.zuul.exception.ZuulException: Hystrix Readed time out"
 * }
 * 当处理转发请求的微服务模块不可用时，系统返回：
 *
 * {
 *     "timestamp": "2019-08-07T08:01:31.829+0000",
 *     "status": 500,
 *     "error": "Internal Server Error",
 *     "message": "GENERAL"
 * }
 */
@Slf4j
@Component
public class LeungGatewayErrorFilter  extends SendErrorFilter {
    @Override
    public Object run() {
        try {
            LeungResponse leungResponse = new LeungResponse();
            RequestContext ctx = RequestContext.getCurrentContext();
            String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);

            ExceptionHolder exception = findZuulException(ctx.getThrowable());
            String errorCause = exception.getErrorCause();
            Throwable throwable = exception.getThrowable();
            String message = throwable.getMessage();
            message = StringUtils.isBlank(message) ? errorCause : message;
            leungResponse = resolveExceptionMessage(message, serviceId, leungResponse);

            HttpServletResponse response = ctx.getResponse();
            LeungUtil.makeResponse(
                    response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, leungResponse
            );
            log.error("Zull sendError：{}", leungResponse.getMessage());
        } catch (Exception ex) {
            log.error("Zuul sendError", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

    private LeungResponse resolveExceptionMessage(String message, String serviceId, LeungResponse leungResponse) {
        if (StringUtils.containsIgnoreCase(message, "time out")) {
            return leungResponse.message("请求" + serviceId + "服务超时");
        }
        if (StringUtils.containsIgnoreCase(message, "forwarding error")) {
            return leungResponse.message(serviceId + "服务不可用");
        }
        return leungResponse.message("Zuul请求" + serviceId + "服务异常");
    }
}
