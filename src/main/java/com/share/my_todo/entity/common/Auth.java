package com.share.my_todo.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 권한 지정을 위한 Enum 타입 클래스 <br/>
 * 권한의 종류는 관리자 또는 회원 2종류이다.
 */
@AllArgsConstructor
@Getter
public enum Auth {

    Admin("ROLE_Admin"),
    Member("ROLE_Member");

    private String value;
}
