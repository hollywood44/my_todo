package com.share.my_todo.service.member;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.DTO.member.PasswordCheckDto;
import com.share.my_todo.entity.member.RefreshToken;
import com.share.my_todo.repository.member.RefreshTokenRepository;
import com.share.my_todo.util.CookieUtil;
import com.share.my_todo.util.SecurityUtil;
import com.share.my_todo.entity.common.Auth;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.exceptionClass.CommonException;
import com.share.my_todo.config.login.JwtTokenProvider;
import com.share.my_todo.config.login.TokenInfo;
import com.share.my_todo.repository.friend.FriendListRepository;
import com.share.my_todo.repository.member.MemberRepository;
import com.share.my_todo.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final FriendListRepository friendListRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public String signUp(MemberDto memberDto) {
        Optional<Member> check = memberRepository.findById(memberDto.getMemberId());
        if (check.isPresent()) {
            throw new CommonException(ErrorCode.ID_DUPLICATED);
        }

        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberDto.setAuth(Auth.MEMBER);
        Member member = dtoToEntity(memberDto);

        memberRepository.save(member);

        FriendList friendList = FriendList.builder().member(member).build();
        friendListRepository.save(friendList);

        return memberDto.getMemberId();
    }

    @Override
    public String signUpForAdmin(MemberDto memberDto) {
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberDto.setAuth(Auth.ADMIN);
        Member member = dtoToEntity(memberDto);

        return memberRepository.save(member).getMemberId();
    }

    @Override
    public boolean idCheck(String memberId) {
        return memberRepository.existsById(memberId);
    }

    @Override
    public MemberDto getMyInfo(String memberId) {
        MemberDto myInfo = entityToDto(memberRepository.findById(memberId).get());
        return myInfo;
    }

    @Override
    public MemberDto getMemberInfo() {
        MemberDto memberInfo = entityToDto(memberRepository.findById(SecurityUtil.getCurrentMemberId()).get());
        return memberInfo;
    }

    @Override
    public void modifyMemberInfo(MemberDto dto){
        if (dto.getName().isEmpty()) {
            throw new CommonException(ErrorCode.MODIFY_VALUE_EMPTY);
        }

        Member modifiedMember = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new CommonException(ErrorCode.ID_NOT_FOUND));
        modifiedMember.modifyInfo(dto);

        memberRepository.save(modifiedMember);
    }

    @Override
    public void modifyPassword(PasswordCheckDto passwordCheckDto){
        if (passwordCheckDto.getPassword()==null || passwordCheckDto.getPassword().isEmpty()) {
            throw new CommonException(ErrorCode.MODIFY_VALUE_EMPTY);
        }

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(()-> new CommonException(ErrorCode.ID_NOT_FOUND));

        if (passwordEncoder.matches(passwordCheckDto.getPasswordCheck(), member.getPassword())) {
            member.modifyPassword(passwordEncoder.encode(passwordCheckDto.getPassword()));
        } else {
            throw new CommonException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        memberRepository.save(member);
    }

    @Override
    public void deleteAccount(PasswordCheckDto passwordCheckDto){
        Member check = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(()->new CommonException(ErrorCode.ID_NOT_FOUND));

        if (passwordEncoder.matches(passwordCheckDto.getPasswordCheck(), check.getPassword())) {
            memberRepository.delete(check);
        } else {
            throw new CommonException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }
}
