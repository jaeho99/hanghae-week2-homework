package com.sparta.postlike.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@NoArgsConstructor
@Entity
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Long boardId;
    @Column(nullable = false)
    private Long userId;


    public BoardLike(Long boardId, Long userId){
        this.boardId = boardId;
        this.userId = userId;
    }
}
