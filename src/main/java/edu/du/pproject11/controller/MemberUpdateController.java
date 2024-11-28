package edu.du.pproject11.controller;

import edu.du.pproject11.config.session.SessionConst;
import edu.du.pproject11.entity.Member;
import edu.du.pproject11.repository.MemberRepository;
import edu.du.pproject11.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberUpdateController {
    private final MemberService memberService;

    @GetMapping("/update")
    public String updateView(HttpServletRequest request, Model model) {
        Member member = (Member) request.getAttribute("loginUser");
        model.addAttribute("member", member);
        return "member/updateForm";
    }

    @PostMapping("/update")
    public String updateMember(HttpServletRequest request, HttpSession session,
                               @ModelAttribute Member member) {
        Member loginUser = (Member) request.getAttribute("loginUser");  // 로그인 되어있는 계정
        Member member1 = memberService.findById(loginUser.getId());        // 위 계정의 정보 가져오기
        member.setId(member1.getId());  // 로그인 되어있는 계정이랑 폼에서 읽어온 값을 id로 연결
        memberService.updateMember(member); // 폼에서 읽어온 값으로 로그인 된 계정 정보 변경

        log.info("세션 {}", member);
        request.setAttribute("loginUser", member); // 세션 정보 변경
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);  // 세션 정보 변경
        return "redirect:/main";
    }

}
