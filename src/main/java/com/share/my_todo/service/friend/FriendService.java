package com.share.my_todo.service.friend;

import com.share.my_todo.DTO.member.FriendDto;
import com.share.my_todo.DTO.member.FriendListDto;
import com.share.my_todo.entity.member.Friend;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;

import java.util.ArrayList;
import java.util.List;

public interface FriendService {

    default Member easyMakeMember(String memberId) {
        Member member = Member.builder().memberId(memberId).build();
        return member;
    }

    default Friend dtoToEntity(FriendDto dto) {
        Friend entity = Friend.builder()
                .member(Member.builder().memberId(dto.getMemberId()).build())
                .build();
        return entity;
    }

    default FriendDto entityToDto(Friend entity) {
        FriendDto dto = FriendDto.builder()
                .friendId(entity.getFriendId())
                .memberId(entity.getMember().getMemberId())
                .friendListId(entity.getFriendList().getFriendListId())
                .Status(entity.getFollowStatus().toString())
                .build();
        return dto;
    }

    default FriendListDto entityToDtoForList(FriendList entity) {
        List<FriendDto> dtoList = new ArrayList<>();

        for (Friend entityFriend : entity.getFriendList()) {
            dtoList.add(entityToDto(entityFriend));
        }

        FriendListDto dto = FriendListDto.builder()
                .friendListId(entity.getFriendListId())
                .friendList(dtoList)
                .memberId(entity.getMember().getMemberId())
                .build();
        return dto;
    }

    /**
     * 회원의 아이디를 가지고 해당 회원의 친구 목록을 불러옴
     * @return 친구목록
     */
    FriendListDto getFriendList();

    /**
     * 친구 팔로우<br/>
     * 1. <br/>내 FriendList를 불러와서 팔로우할 Friend를 status가 wating(수락대기)인 상태로 FriendList에 넣음(영속화)<br/>
     * 2. <br/>팔로우한 멤버의 FriendList를 불러와서 팔로우 한 Friend를 status가 requested(요청이옴) 상태로 넣음(영속화)<br/>
     * 3. <br/>위의 2개의 FriendList를 save -> 만약 잘못 됬을 시 Transaction 어노테이션으로 인해서 Rollback<br/>
     * @param followId 팔로우 할 친구 아이디
     * @return 성공하면 요청자 친구목록아이디 리턴
     */
    void followRequest(String followId);

    /**
     * 팔루우 요청 수락<br/>
     * 1. <br/>팔로우 당한 회원의 FriendList와 그 중에서 팔로우 한 회원의 FriendId 가져옴<br/>
     * 2. <br/>팔로우 한 회원의 FriendList와 그 중에서 팔로우 당한 회원의 FriendId 가져옴<br/>
     * 3. <br/>친구목록을 for문을 돌려서 FriendId와 일치하는 Friend 객체 가져옴(팔로우 당한 회원 & 팔로우 한 회원 해서 for문 각각 1번씩)<br/>
     * 4. <br/>그 Friend객체의 상태를 Accept로 바꾸고 저장(각각 1번씩)<br/>
     * @param followerId 팔로워 아이디
     * @return 성공하면 수락자 친구목록아이디 리턴
     */
    void followAccept(String followerId);

    /**
     * 팔로우 요청목록 반환
     * @return 팔로우 요청받은 목록 반환
     */
    List<FriendDto> requestedFriendList();

    /**
     * 내가 팔로우 요청한 목록 반환
     * @return 내가 팔로우 요청한 목록 반환
     */
    List<FriendDto> requestFriendList();

    /**
     * 팔로우 거절
     * @param followerId 거절 받는 사람 아이디
     */
    void followReject(String followerId);

    /**
     * 팔로우 해제
     * @param followId 팔로우 해체 할 아이디
     */
    void unFollow(String followId);

}