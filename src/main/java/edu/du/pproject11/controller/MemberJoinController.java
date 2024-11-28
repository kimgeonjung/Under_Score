package edu.du.pproject11.controller;

import edu.du.pproject11.config.SecurityConfig;
import edu.du.pproject11.entity.Member;
import edu.du.pproject11.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("member/join")
public class MemberJoinController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String join(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);
        return "member/joinForm";
    }

    @PostMapping
    public String createMember(@ModelAttribute Member member, BindingResult bindingResult, HttpSession session) {
        memberService.validateMember(member, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "member/joinForm";
        }
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberService.join(member);
        log.info("member = {}", member);
        session.setAttribute("loginUser", member);
        return "redirect:/";
    }
}
