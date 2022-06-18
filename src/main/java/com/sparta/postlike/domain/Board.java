package com.sparta.postlike.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Board extends Timestamped{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int layoutType;
    @Column(nullable = false)
    private String img_url;

    public Board(BoardRequestDto requestDto, Long userId){
        this.userId = userId;
        this.content = requestDto.getContent();
        this.layoutType = Integer.parseInt(requestDto.getLayoutType());
        this.img_url = requestDto.getImg().getName(); //s3 에따른 경로로 변경
    }
    public Board(BoardRequestDto requestDto, String img_url, Long userId){
        this.userId = userId;
        this.content = requestDto.getContent();
        this.layoutType = Integer.parseInt(requestDto.getLayoutType());
        this.img_url = img_url;
    }

    public void update(BoardRequestDto requestDto, String img_url){
        this.content = requestDto.getContent();
        this.layoutType =  Integer.parseInt(requestDto.getLayoutType());
        this.img_url = img_url;
    }

}
