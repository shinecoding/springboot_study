package com.example.demo.service;

import com.example.demo.model.Board;
import com.example.demo.model.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board) {
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }

    public Page<Board> findAllPage(int startAt) {
        Pageable pageable = PageRequest.of(startAt, 10);
        return boardRepository.findAll(pageable);
    }

}
