package com.share.my_todo.DTO.notice;

import com.share.my_todo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeDto {

    private Long noticeId;
    private String notice;
    private String memberId;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
