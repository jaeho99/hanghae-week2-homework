package com.sparta.postlike.controller;

import com.sparta.postlike.awsfile.S3Uploader;
import com.sparta.postlike.domain.*;
import com.sparta.postlike.service.BoardService;
import com.sparta.postlike.springsecurityjwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardRestController {

    private final BoardRepository boardRepository;  //레포지토리 지우고 서비스로 가져오게 분리하기
    private final BoardService boardService;
    private final BoardLikeRepository boardLikeRepository;
    private final S3Uploader s3Uploader;
    private final UsersRepository usersRepository;


    @GetMapping("/api/boards")
    public List<BoardsRequestDto> boardList(){   // 잘못된 DB 설정으로 코드로 수정
        List<BoardsRequestDto> list = new ArrayList<BoardsRequestDto>();
        boardRepository.findAllByOrderByCreatedAtDesc().forEach(board -> {
            list.add(new BoardsRequestDto(board,
                    boardLikeRepository.findAllByBoardId(board.getId()),
                    usersRepository.findById(board.getUserId()).get().getNickname()));
        });

        return list;
    }

    @PostMapping(value = "/api/boards")
    public String createBoard(@ModelAttribute BoardRequestDto requestDto,
                              @AuthenticationPrincipal Users userDetails
    ) throws IOException {
        Board board = new Board(new BoardRequestDto(requestDto.getImg(), requestDto.getContent(), requestDto.getLayoutType())
                ,s3Uploader.upload(requestDto.getImg(), userDetails.getNickname()), userDetails.getId());
        boardRepository.save(board);

        return "status";
    }
    @DeleteMapping("/api/boards/{id}")  //id 어떤 아이디인지 이름 변경해주기
    public String deleteBoard(@PathVariable Long id){
//        s3Uploader
        boardRepository.deleteById(id);         //상태 리턴해주기
        return "삭제";
    }
    @PutMapping("/api/boards/{id}")
    public String updateBoard(@PathVariable Long id,
                              @ModelAttribute BoardRequestDto requestDto,
                              @AuthenticationPrincipal Users userDetails) throws IOException {
//        if(!userDetails.getNickname().equals(boardRepository.findById()))
        s3Uploader.upload(requestDto.getImg(), userDetails.getNickname());
        boardService.update(id, new BoardRequestDto(requestDto.getImg(), requestDto.getContent(), requestDto.getLayoutType()), userDetails);
        return "업데이트";
    }
    // 현재 boardLike 에서 boardid와 userid 가 유니크가 아니기에 중복 좋아요가 될 가능성 있음 예외처리
    @PostMapping("/api/boards/{boardId}/likes")
    public String likeUp(@PathVariable Long boardId, @AuthenticationPrincipal Users userDetails){
        BoardLike boardLike = new BoardLike(boardId, userDetails.getId());
        boardLikeRepository.save(boardLike);
        return "성공";
    }

    @DeleteMapping("/api/boards/{boardId}/likes")
    public String likeCancel(@PathVariable Long boardId, @AuthenticationPrincipal Users userDetails){
        boardLikeRepository.deleteById(
                boardLikeRepository.findByBoardIdAndUserId(boardId, userDetails.getId()).getId()
        );
        return "성공";
    }



}
