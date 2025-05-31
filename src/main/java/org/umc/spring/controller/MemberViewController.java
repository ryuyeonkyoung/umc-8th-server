package org.umc.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.umc.spring.domain.Member;
import org.umc.spring.dto.member.request.MemberRequestDTO;
import org.umc.spring.service.MemberService.MemberCommandService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberViewController {

    private final MemberCommandService memberCommandService;

    // 8주차에서 작성한 컨트롤러와 동일하게 작성
    @PostMapping("/members/signup")
    public String joinMember(@ModelAttribute("memberJoinDto") MemberRequestDTO.JoinDto request, // 협업시에는 기존 RequestBody 어노테이션을 붙여주시면 됩니다!
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            // 뷰에 데이터 바인딩이 실패할 경우 signup 페이지를 유지합니다.
            return "signup";
        }

        log.info("socialType: {}" , request.getSocialType());
        log.info("name: {}" , request.getName());
        log.info("nickname: {}" , request.getNickname());
        log.info("socialId: {}" , request.getSocialId());
        log.info("phoneNumber: {}" , request.getPhoneNumber());
        log.info("phoneVerified: {}" , request.getPhoneVerified());
        log.info("email: {}" , request.getEmail());
        log.info("address: {}" , request.getAddress());
        log.info("gender: {}" , request.getGender());

        try {
            memberCommandService.joinMember(request);
            Member member = memberCommandService.joinMember(request);
            log.info("회원가입 성공: {}", member.getId());
            return "redirect:/login";
        } catch (Exception e) {
            // 회원가입 과정에서 에러가 발생할 경우 에러 메시지를 보내고, signup 페이지를 유지합니다.
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("memberJoinDto", new MemberRequestDTO.JoinDto());
        return "signup";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
