package edu.du.pproject11.controller;

import edu.du.pproject11.config.session.SessionConst;
import edu.du.pproject11.dto.LoginForm;
import edu.du.pproject11.entity.Member;
import edu.du.pproject11.exception.InvalidLoginException;
import edu.du.pproject11.repository.MemberRepository;
import edu.du.pproject11.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberLoginController {
    private final MemberService memberService;

    // 로그인
    @PostMapping("/login")
    @ResponseBody
    public String login(@ModelAttribute LoginForm loginForm, HttpSession session, Model model) {
        try{
            Member loginUser = memberService.validateLogin(loginForm.getLoginId(), loginForm.getPassword());
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
            session.setMaxInactiveInterval(3600);
            log.info("loginForm 세션 : {}", session.getAttribute(SessionConst.LOGIN_MEMBER));
            return "success";
        }catch(InvalidLoginException e){
            model.addAttribute("error", e.getMessage());
            log.info("에러메시지 {}", model.getAttribute("error"));
            session.invalidate();
            return e.getMessage();
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionConst.LOGIN_MEMBER);
        session.invalidate();
        return "redirect:/";
    }
}
