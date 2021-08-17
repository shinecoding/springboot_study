package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.service.BoardService;
import com.example.demo.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 3) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
        /*
        Pageable
        page 조회할 페이지 번호
        size 한 페이지에 보여지는 게시글 수
        sort 정렬 기준
        */
        //tab 에 보여줄 페이지 수는 지정해야할 듯
        int tabPage = 5;

       //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
       int startPage= (boards.getNumber() / tabPage) * tabPage + 1;
       int endPage = boards.getTotalPages() == 0? 1: (startPage + tabPage-1 < boards.getTotalPages()? startPage + tabPage-1 : boards.getTotalPages()) ;

       model.addAttribute("startPage", startPage);
       model.addAttribute("endPage", endPage);
       model.addAttribute("boards",boards);
       model.addAttribute("tabPage", tabPage);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);

        }
        return "board/form";
    }

    @PostMapping("/form")
    public String boardForm(@Valid Board board, BindingResult bindingResult, Authentication authentication){
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        //인증정보 가져오는 방법2
        //  Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        //인증정보를 이렇겐 가져올 수 없음
        //board.setUser(user);

        boardService.save(username, board);
        //boardRepository.save(board);

        return "redirect:/board/list";
    }
}
