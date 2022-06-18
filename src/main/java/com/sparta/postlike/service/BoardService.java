package com.sparta.postlike.service;

import com.sparta.postlike.awsfile.S3Uploader;
import com.sparta.postlike.domain.Board;
import com.sparta.postlike.domain.BoardRepository;
import com.sparta.postlike.domain.BoardRequestDto;
import com.sparta.postlike.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    private final S3Uploader s3Uploader;

    @Transactional
    public Long update(Long id, BoardRequestDto requestDto, Users userDetails) throws IOException {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );

        board.update(requestDto, s3Uploader.upload(requestDto.getImg(), userDetails.getNickname()));
        //upload의 리턴 값이 파일명 이기 때문에 리턴값을 DB에 저장한다.
        return id;
    }

}
