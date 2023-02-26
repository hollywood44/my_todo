package com.share.my_todo.service.member;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.entity.common.Auth;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.exceptionClass.IdDuplicateException;
import com.share.my_todo.repository.friend.FriendListRepository;
import com.share.my_todo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final FriendListRepository friendListRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다."));
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
        }else {
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
    public String modifyMemberInfo(MemberDto dto) {
        Member modifiedMember = memberRepository.findById(dto.getMemberId()).get();
        modifiedMember.modifyInfo(dto);

        return memberRepository.save(modifiedMember).getMemberId();
    }

    @Override
    public String modifyPassword(String memberId, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Member member = memberRepository.findById(memberId).get();
        member.modifyPassword(encoder.encode(password));
        return memberRepository.save(member).getMemberId();
    }

    @Override
    public String deleteAccount(String memberId, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Member check = memberRepository.findById(memberId).get();


        if (encoder.matches(password, check.getPassword())) {
            memberRepository.delete(check);
        } else {
            String message = "error";
            return message;
        }
        return check.getMemberId();
    }
}
