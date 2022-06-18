package com.sparta.postlike.domain;

import lombok.Getter;

import javax.persistence.Column;

@Getter
public class BoardLikeRequestDto {
    private Long id;
    private Long boardId;
    private Long userId;
}
