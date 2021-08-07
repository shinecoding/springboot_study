package com.example.demo.service;

import com.example.demo.model.Board;
import com.example.demo.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;



    public Page<Board> findAllPage(int startAt) {
        Pageable pageable = PageRequest.of(startAt, 10);
        return boardRepository.findAll(pageable);
    }

}
