package com.share.my_todo.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TodoProgressNoticeMessage {

    ProgressPercent("달성률"){
        public String getMessage(String id,int percent){
            return String.format("%s 님의 달성률은 %d%% 입니다.", id, percent);
    }};

    private String status;

    public abstract String getMessage(String id,int percent);

}
