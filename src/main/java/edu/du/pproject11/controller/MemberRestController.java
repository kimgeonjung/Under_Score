package edu.du.pproject11.controller;

import edu.du.pproject11.dto.ChangePwRequest;
import edu.du.pproject11.dto.FindPwRequest;
import edu.du.pproject11.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/findLoginId")
    public ResponseEntity<String> findLoginId(@RequestParam String name, @RequestParam String email) {
        String loginId = memberService.findLoginIdByNameAndEmail(name, email);
        if (loginId != null) {
            return ResponseEntity.ok(loginId);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 정보를 찾을 수 없습니다.");
        }
    }

    @PostMapping("/findPassword")
    public ResponseEntity<String> findPassword(@RequestBody FindPwRequest request) {
        String status;
        try{
            status = memberService.findPw(request);
        } catch (Exception e) {
            status = e.getMessage();
        }
        return ResponseEntity.ok().body(status);
    }

    @PostMapping("/changePw")
    public ResponseEntity<String> changePw(@RequestBody ChangePwRequest request, HttpSession session) {
        try{
            String response = memberService.changePassword(request);
            session.invalidate();
            return ResponseEntity.ok().body(response);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
