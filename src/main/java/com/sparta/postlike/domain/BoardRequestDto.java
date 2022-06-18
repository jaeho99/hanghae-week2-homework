package com.sparta.postlike.domain;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;

@Getter
public class BoardRequestDto {
    private Long id;
    private String content;
    private MultipartFile img;
    private String layoutType;

    public BoardRequestDto(MultipartFile img, String content, String layoutType){
        this.img = img;
        this.content = content;
        this.layoutType = layoutType;
    }

}
