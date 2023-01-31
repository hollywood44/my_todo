package com.share.my_todo.entity.member;

import com.share.my_todo.entity.common.CommonTime;
import lombok.*;

import javax.persistence.*;

/**
 * 고유아이디, 소속된 친구목록 아이디, 친구 아이디, 팔로우 상태
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "friend_tbl")
public class Friend extends CommonTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @Enumerated(EnumType.STRING)
    private FollowStatus followStatus;

    @ManyToOne(targetEntity = FriendList.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "friendListId")
    private FriendList friendList;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    // 요청옴(받는쪽),수락대기(보내는쪽),거절,수락
    public enum FollowStatus {
        Requested,Waiting,Reject,Accept
    }

    public void setFriendList(FriendList friendList) {
        this.friendList = friendList;
    }
    public void statusToWaiting() {
        this.followStatus = FollowStatus.Waiting;
    }
    public void statusToRequest() {
        this.followStatus = FollowStatus.Requested;
    }
    public void statusToAccept() {
        this.followStatus = FollowStatus.Accept;
    }
    public void statusToReject() {
        this.followStatus = FollowStatus.Reject;
    }
}
