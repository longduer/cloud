package yves.leung.com.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yves.leung.com.common.entity.LeungResponse;
import yves.leung.com.common.exception.LeugnAuthException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class SecurityController {
    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("oauth/test")
    public String testOauth() {
        return "oauth";
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    @DeleteMapping("signout")
    public LeungResponse signout(HttpServletRequest request) throws LeugnAuthException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "bearer ", "");
        LeungResponse febsResponse = new LeungResponse();
        if (!consumerTokenServices.revokeToken(token)) {
            throw new LeugnAuthException("退出登录失败");
        }
        return febsResponse.message("退出登录成功");
    }
}
