package com.example.demo.board;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository repository;

    public BoardService(BoardRepository repository) {
        this.repository = repository;
    }

    public void postBoard(Board board) {
        repository.save(board);
    }

    public List<Board> getBoards() {
        return repository.findAll();
    }

    public Board getBoard(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void updateBoard(Board board) {
        repository.save(board);
    }

    public void deleteBoard(Long id) {
        repository.deleteById(id);
    }

}
