package com.share.my_todo.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FollowNoticeMessage {
    FollowAccept("팔로우_수락"){
        public String getMessage(String memberId) {
            return String.format("%s님이 팔로우를 수락 하셨습니다.",memberId);
        }
    },
    FollowReject("팔로우_거부"){
        public String getMessage(String memberId) {
            return String.format("%s님이 팔로우를 거부 하셨습니다.",memberId);
        }
    },
    FollowRequest("팔로우_요청"){
        public String getMessage(String memberId) {
            return String.format("%s님이 팔로우를 요청 하셨습니다.",memberId);
        }
    };


    private String status;

    public abstract String getMessage(String memberId);

}
