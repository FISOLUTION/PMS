package fis.pms.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 세션이 유효한지 확인

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginId") == null) {
            log.warn("미인증 사용자 요청");
            throw new LoginException("로그인이 되지않았습니다");
        }

        // 유효하다면 return true;
        return true;
    }
}
