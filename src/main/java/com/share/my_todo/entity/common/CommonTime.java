package com.share.my_todo.entity.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 등록일, 수정일 자동화를 위한 클래스
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CommonTime {

    @CreatedDate
    private LocalDateTime regDate;
    @LastModifiedDate
    private LocalDateTime modDate;
}
