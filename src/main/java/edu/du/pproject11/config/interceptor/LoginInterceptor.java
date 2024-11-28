package edu.du.pproject11.config.interceptor;

import edu.du.pproject11.config.session.SessionConst;
import edu.du.pproject11.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Member loginUser = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginUser == null) {
            request.setAttribute("loginRequired","로그인/회원가입이 필요한 컨텐츠입니다.");
            log.info(request.getAttribute("loginRequired").toString());
            return true;
        }
        request.setAttribute("loginUser", loginUser);
        return true;
    }
}
