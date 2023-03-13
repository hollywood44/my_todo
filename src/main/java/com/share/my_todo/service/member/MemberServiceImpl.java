package com.share.my_todo.service.member;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.config.SecurityConfig;
import com.share.my_todo.entity.common.Auth;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.exceptionClass.CommonException;
import com.share.my_todo.login.JwtTokenProvider;
import com.share.my_todo.login.TokenInfo;
import com.share.my_todo.repository.friend.FriendListRepository;
import com.share.my_todo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final FriendListRepository friendListRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenInfo login(String memberId, String password) throws BadCredentialsException {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    @Override
    @Transactional
    public String signUp(MemberDto memberDto) {
        Optional<Member> check = memberRepository.findById(memberDto.getMemberId());
        if (check.isPresent()) {
            throw new CommonException(ErrorCode.ID_DUPLICATED);
        }

        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberDto.setAuth(Auth.Member);
        Member member = dtoToEntity(memberDto);

        memberRepository.save(member);

        FriendList friendList = FriendList.builder().member(member).build();
        friendListRepository.save(friendList);

        return memberDto.getMemberId();
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
    public MemberDto getMemberInfo(String memberId) {
        MemberDto memberInfo = entityToDto(memberRepository.findById(memberId).get());
        return memberInfo;
    }

    @Override
    public String modifyMemberInfo(MemberDto dto){
        if (dto.getName().isEmpty()) {
            throw new CommonException(ErrorCode.MODIFY_VALUE_EMPTY);
        }

        Member modifiedMember = memberRepository.findById(dto.getMemberId()).get();
        modifiedMember.modifyInfo(dto);

        return memberRepository.save(modifiedMember).getMemberId();
    }

    @Override
    public String modifyPassword(String memberId, String password, String passwordCheck){
        if (password.isEmpty()) {
            throw new CommonException(ErrorCode.MODIFY_VALUE_EMPTY);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Member member = memberRepository.findById(memberId).get();

        if (encoder.matches(passwordCheck, member.getPassword())) {
            member.modifyPassword(encoder.encode(password));
        } else {
            throw new CommonException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        return memberRepository.save(member).getMemberId();
    }

    @Override
    public void deleteAccount(String memberId, String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Member check = memberRepository.findById(memberId).get();

        if (encoder.matches(password, check.getPassword())) {
            memberRepository.delete(check);
        } else {
            throw new CommonException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }
}
