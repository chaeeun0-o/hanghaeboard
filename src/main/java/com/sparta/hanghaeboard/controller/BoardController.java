package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.BoardRequestDto;
import com.sparta.hanghaeboard.dto.BoardResponseDto;
import com.sparta.hanghaeboard.dto.ResponseDto;
import com.sparta.hanghaeboard.dto.StatusDto;
import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.createBoard(requestDto, request);
    }

    @GetMapping("/api/boards")
    public List<BoardResponseDto> getListBoards(){return boardService.getListBoards();
    }

    //선택 조회
    @GetMapping("/api/boards/{id}")
    public BoardResponseDto getBoards(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    //수정
    @PutMapping("/api/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.update(id, requestDto, request);
    }

    //삭제
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<StatusDto> deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deleteBoard(id, request);
    }
}