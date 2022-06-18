package com.sparta.postlike.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardsRequestDto {
    private Long id;
    private String nickname;
    private String content;
    private String img_url;
    private List<Long> likes;
    private int layoutType;

    private Long userId;

    public BoardsRequestDto(Board board, List<BoardLike> likes , String nickname){
        List<Long> likesArray = new ArrayList<>();
        for(BoardLike like: likes){                 //이부분에서 생성자에 이와 같은 행위를 해도 되는지
            likesArray.add(like.getUserId());
        };
        this.id = board.getId();
        this.userId = board.getUserId();
        this.content = board.getContent();
        this.img_url = board.getImg_url();
        this.layoutType = board.getLayoutType();
        this.likes = likesArray;
        this.nickname = nickname;

    }

}
