package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.BoardRequestDto;
import com.sparta.hanghaeboard.dto.BoardResponseDto;
import com.sparta.hanghaeboard.dto.StatusDto;
import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.entity.StatusEnum;
import com.sparta.hanghaeboard.entity.User;
import com.sparta.hanghaeboard.jwt.JwtUtil;
import com.sparta.hanghaeboard.repository.BoardRepository;
import com.sparta.hanghaeboard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    //  작성
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        // 토큰 검사
        User user = tokenCheck(request);
        // 보드를 세이브하고
//        Board board = new Board(requestDto);
//        boardRepository.save(board);
//        boardRepository.save(new Board(requestDto);
//        boardRepository.save(new Board(requestDto, user));
        return new BoardResponseDto(boardRepository.save(new Board(requestDto, user)));
    }

    //전체 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getListBoards() {
        return boardRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Board::getCreatedAt).reversed())
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

    //선택 조회
    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new BoardResponseDto(board);
    }

    //수정
    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        User user = tokenCheck(request);
        // 토크이 있는지, 토큰 검증, 토큰 주인의 아이디 -> user
        // 토큰의 username 이랑 선택한 게시글의 username 이랑 비교해서 맞으면 수정 틀리면 exception
        Board board = boardRepository.findBoardByIdAndUsername(id, user.getUsername()).orElseThrow(
                () -> new NullPointerException("해당 게시글을 수정할 권한이 없습니다.")
        );
        board.update(requestDto);
        return new BoardResponseDto(board);
    }

    //삭제
    @Transactional
    public ResponseEntity<StatusDto> deleteBoard(Long id, HttpServletRequest request) {
        User user = tokenCheck(request);
        Board board = boardRepository.findBoardByIdAndUsername(id, user.getUsername()).orElseThrow(
                () -> new NullPointerException("해당 게시글을 삭제할 권한이 없습니다.")
        );
        boardRepository.delete(board);

        StatusDto statusDto = StatusDto.setSuccess(StatusEnum.OK.getStatusCode(), "삭제완료", null);
        return new ResponseEntity(statusDto, HttpStatus.OK);
    }
    // camel case
    public User tokenCheck(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;

        // 토큰검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("로그인 후 이용해 주세요");
            }

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회 -> user 를 생성
//        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
//        );
//        return user;
        return userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 없습니다.")
        );
    }

}
