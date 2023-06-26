package com.example.demo.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        boardService.postBoard(board);
        return new ResponseEntity<>(board, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Board>> getBoards() {
        List<Board> boards = boardService.getBoards();
        if (boards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(boards, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable("id") Long id) {
        Board board = boardService.getBoard(id);
        if (board != null) {
            return new ResponseEntity<>(board, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") Long id, @RequestBody Board board) {
        Board existingBoard = boardService.getBoard(id);
        if (existingBoard != null) {
            board.setId(id);
            boardService.updateBoard(board);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id) {
        Board existingBoard = boardService.getBoard(id);
        if (existingBoard != null) {
            boardService.deleteBoard(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
