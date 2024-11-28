package edu.du.pproject11.service;

import edu.du.pproject11.dto.ChangePwRequest;
import edu.du.pproject11.dto.FindPwRequest;
import edu.du.pproject11.dto.FindPwResponse;
import edu.du.pproject11.entity.Cart;
import edu.du.pproject11.entity.Member;
import edu.du.pproject11.exception.BadCredentialsException;
import edu.du.pproject11.exception.InvalidLoginException;
import edu.du.pproject11.repository.CartRepository;
import edu.du.pproject11.repository.MemberRepository;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;

    public String join(Member member) {
        memberRepository.findByLoginId(member.getLoginId())
                .ifPresent(m -> {
                    throw new IllegalStateException();
                });
        memberRepository.save(member);
        log.info("멤버 {}", member);
        Cart cart = Cart.createCart(member);
        cartRepository.save(cart);
        log.info("카트 {}", cart);
        return member.getLoginId();
    }

    public void validateMember(Member member, BindingResult bindingResult) {
        if (!StringUtils.hasText(member.getName())) {
            bindingResult.addError(new FieldError("member", "name", "올바르지 않은 이름"));
        }
        if (!StringUtils.hasText(member.getLoginId()) || member.getLoginId().length() < 3 || member.getLoginId().length() > 20) {
            bindingResult.addError(new FieldError("member", "loginId", "올바르지 않은 ID (3자 이상 20자 이하)"));
        }
        if (member.getPassword().length() < 3 || member.getPassword().length() > 10) {
            bindingResult.addError(new FieldError("member", "password", "올바르지 않은 PASSWORD (3자 이상 10자 이하)"));
        }
        if (!StringUtils.hasText(member.getEmail()) || !member.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            bindingResult.addError(new FieldError("member", "email", "유효하지 않은 이메일주소"));
        }
        if (!StringUtils.hasText(member.getPhone()) || !member.getPhone().matches("^[0-9]{10,11}$")) {
            bindingResult.addError(new FieldError("member", "phone", "유효하지 않은 전화번호 (10-11자리 숫자)."));
        }
    }

    public Member validateLogin(String loginId, String rawPassword) throws InvalidLoginException {
        Optional<Member> member = memberRepository.findByLoginId(loginId);
        boolean login = passwordEncoder.matches(rawPassword, member.map(Member::getPassword).orElse(null));

        if (member.isEmpty() || !login) {
            throw new InvalidLoginException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
        return member.get();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정 정보입니다."));
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정 정보입니다."));
    }

    public Member updateMember(Member member) {
        Member newMember = findById(member.getId());
        newMember.setName(member.getName());
        newMember.setPhone(member.getPhone());
        newMember.setAddress(member.getAddress());
        memberRepository.save(newMember);
        return newMember;
    }

    public String findLoginIdByNameAndEmail(String name, String email) {
        return memberRepository.findLoginIdByNameAndEmail(name, email).orElse(null);
    }

    // 임시 비밀번호 발급
    public String findPw(FindPwRequest request) {
        Dotenv dotenv = Dotenv.load();
        String mailUsername = dotenv.get("mail_username");

        Member member = findByLoginId(request.getLoginId());

        if(!member.getEmail().equals(request.getEmail())){
            throw new BadCredentialsException("이메일이 맞지 않습니다");
        }
        char[] charSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        StringBuilder tempPw = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int idx = (int) (charSet.length * Math.random());
            tempPw.append(charSet[idx]);
        }

        String newPw = tempPw.toString();
        log.info(newPw);
        String body = "안녕하세요. "+member.getName()+"님. 임시 비밀번호를 발급해드립니다.\n"+
                "회원님의 임시 비밀번호는 "+newPw+" 입니다.\n" +
                "임시 비밀번호로 로그인 후 비밀번호를 변경해주세요.";

        FindPwResponse response = FindPwResponse.builder()
                .receiveAddress(request.getEmail())
                .mailTitle("UNDER_SCORE 임시 비밀번호 발급")
                .mailContent(body)
                .build();

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(mailUsername);
        message.setTo(response.getReceiveAddress());
        message.setSubject(response.getMailTitle());
        message.setText(response.getMailContent());

        mailSender.send(message);
        member.updatePassword(passwordEncoder.encode(newPw));
        memberRepository.save(member);

        return "임시 비밀번호 발급.";
    }

    public String changePassword(ChangePwRequest request) {
        Member member = findByLoginId(request.getLoginId());
        log.info("아이디: {}", member.getLoginId());
        if(!passwordEncoder.matches(request.getOldPw(), member.getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        member.updatePassword(passwordEncoder.encode(request.getNewPw()));
        memberRepository.save(member);

        return "비밀번호가 성공적으로 변경되었습니다.";
    }

}
