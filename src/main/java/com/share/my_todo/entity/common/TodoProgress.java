package com.share.my_todo.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TodoProgress {

    Success("Success"),
    Proceeding("Proceeding"),
    Failed("Failed");

    private String value;
}
