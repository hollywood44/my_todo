package com.share.my_todo.service.member;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.entity.common.Auth;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.exceptionClass.AccountDeleteException;
import com.share.my_todo.exception.exceptionClass.CommonException;
import com.share.my_todo.exception.exceptionClass.IdDuplicateException;
import com.share.my_todo.exception.exceptionClass.InfoModifyException;
import com.share.my_todo.login.JwtTokenProvider;
import com.share.my_todo.login.TokenInfo;
import com.share.my_todo.repository.friend.FriendListRepository;
import com.share.my_todo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Override
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    @Override
    @Transactional
    public String signUp(MemberDto memberDto) {
        Optional<Member> check = memberRepository.findById(memberDto.getMemberId());
        if (!check.isPresent()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            memberDto.setPassword(encoder.encode(memberDto.getPassword()));
            memberDto.setAuth(Auth.Member);
            Member member = dtoToEntity(memberDto);

            memberRepository.save(member);

            FriendList friendList = FriendList.builder().member(member).build();
            friendListRepository.save(friendList);

            return memberDto.getMemberId();
        } else {
            throw new IdDuplicateException("Member Id Duplicated", ErrorCode.ID_DUPLICATION);
        }
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
    public String modifyMemberInfo(MemberDto dto) throws InfoModifyException {
        if (dto.getName().isEmpty()) {
            throw new CommonException(ErrorCode.MODIFY_INFO_NOT_EMPTY.getMessage(), ErrorCode.MODIFY_INFO_NOT_EMPTY);
        }

        Member modifiedMember = memberRepository.findById(dto.getMemberId()).get();
        modifiedMember.modifyInfo(dto);

        return memberRepository.save(modifiedMember).getMemberId();
    }

    @Override
    public String modifyPassword(String memberId, String password, String passwordCheck) throws InfoModifyException {
        if (password.isEmpty()) {
            throw new CommonException(ErrorCode.MODIFY_INFO_NOT_EMPTY.getMessage(), ErrorCode.MODIFY_INFO_NOT_EMPTY);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Member member = memberRepository.findById(memberId).get();

        if (encoder.matches(passwordCheck, member.getPassword())) {
            member.modifyPassword(encoder.encode(password));
        } else {
            throw new CommonException(ErrorCode.MEMBER_PASSWORD_CHECK_WRONG_ERROR.getMessage(), ErrorCode.MEMBER_PASSWORD_CHECK_WRONG_ERROR);
        }
        return memberRepository.save(member).getMemberId();
    }

    @Override
    public void deleteAccount(String memberId, String password) throws AccountDeleteException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Member check = memberRepository.findById(memberId).get();

        if (encoder.matches(password, check.getPassword())) {
            memberRepository.delete(check);
        } else {
            throw new CommonException(ErrorCode.MEMBER_PASSWORD_CHECK_WRONG_ERROR.getMessage(), ErrorCode.MEMBER_PASSWORD_CHECK_WRONG_ERROR);
        }
    }
}
