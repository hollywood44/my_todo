package com.share.my_todo.service.member;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.DTO.member.PasswordCheckDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.config.login.TokenInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MemberService{

    default Member dtoToEntity(MemberDto dto) {
        Member entity = Member.builder()
                .memberId(dto.getMemberId())
                .password(dto.getPassword())
                .name(dto.getName())
                .auth(dto.getAuth())
                .build();
        return entity;
    }

    default MemberDto entityToDto(Member entity) {
        MemberDto dto = MemberDto.builder()
                .memberId(entity.getMemberId())
                .name(entity.getName())
                .auth(entity.getAuth())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }


    /**
     * 회원가입
     * @param memberDto : 회원가입에 필요한 정보가 담겨있음
     * @return memberId : String 타입
     */
    String signUp(MemberDto memberDto);

    String signUpForAdmin(MemberDto memberDto);

    boolean idCheck(String memberId);

    /**
     * 내 정보 불러오기
     * @param memberId : 회원 아이디
     * @return MemberDto : 내 정보를 담아서 넘겨줌
     */
    MemberDto getMyInfo(String memberId);

    /**
     * 회원 정보 불러오기
     * @return MemberDto : 해당 회원의 정보를 담아서 넘겨줌
     */
    MemberDto getMemberInfo();

    /**
     * 회원 정보 수정하기
     * @param dto : 기존 필드에서 password 제외하고 변경된 값이 적용되서 넘어온 수정된 회원정보
     * @return MemberDto : 수정이 완료된 회원정보
     */
    void modifyMemberInfo(MemberDto dto);

    /**
     * 비밀번호 변경
     * @param passwordCheckDto : memberId , password
     * @return 변경완료 된 아이디 리턴
     */
    void modifyPassword(PasswordCheckDto passwordCheckDto);

    void deleteAccount(PasswordCheckDto passwordCheckDto);

}
