package com.sparta.hanghaeboard.entity;

import com.sparta.hanghaeboard.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.GeneratedValue;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String password;

//    public Board(BoardRequestDto requestDto) {
//        this.title = requestDto.getTitle();
//        this.contents = requestDto.getContents();
//        this.username = requestDto.getUsername();
//    }
    public Board(BoardRequestDto requestDto, User user){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = user.getUsername();
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}