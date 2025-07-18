package org.umc.spring.service.MemberService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.handler.FoodCategoryHandler;
import org.umc.spring.apiPayload.exception.handler.MemberHandler;
import org.umc.spring.config.security.jwt.JwtTokenProvider;
import org.umc.spring.converter.MemberConverter;
import org.umc.spring.converter.MemberPreferConverter;
import org.umc.spring.domain.FoodCategory;
import org.umc.spring.domain.Member;
import org.umc.spring.domain.mapping.MemberPrefer;
import org.umc.spring.dto.member.request.MemberRequestDTO;
import org.umc.spring.dto.member.response.MemberResponseDTO;
import org.umc.spring.repository.FoodCategoryRepository.FoodCategoryRepository;
import org.umc.spring.repository.MemberRepository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

  private final MemberRepository memberRepository;
  private final FoodCategoryRepository foodCategoryRepository;

  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  @Transactional
  public Member joinMember(MemberRequestDTO.JoinDto request) {

    Member newMember = MemberConverter.toMember(request);
    newMember.encodePassword(passwordEncoder.encode(request.getPassword()));

    List<FoodCategory> foodCategoryList = request.getPreferCategory().stream()
        .map(category -> {
          return foodCategoryRepository.findById(category)
              .orElseThrow(() -> new FoodCategoryHandler(ErrorStatus.FOOD_CATEGORY_NOT_FOUND));
        }).collect(Collectors.toList());

    List<MemberPrefer> memberPreferList = MemberPreferConverter.toMemberPreferList(
        foodCategoryList);

    memberPreferList.forEach(newMember::addMemberPrefer);

    return memberRepository.save(newMember);
  }

  @Override
  @Transactional
  public MemberResponseDTO.LoginResultDTO loginMember(MemberRequestDTO.LoginRequestDTO request) {
    Member member = memberRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

    if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
      throw new MemberHandler(ErrorStatus.INVALID_PASSWORD);
    }

    Authentication authentication = new UsernamePasswordAuthenticationToken(
        member.getEmail(), null,
        Collections.singleton(() -> member.getRole().name())
    );

    String accessToken = jwtTokenProvider.generateToken(authentication);

    return MemberConverter.toLoginResultDTO(
        member.getId(),
        accessToken
    );
  }
}