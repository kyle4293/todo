package com.example.todo.controller;


import com.example.todo.dto.CommentRequestDto;
import com.example.todo.dto.CommentResponseDto;
import com.example.todo.entity.Comment;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.dto.TodoRequestDto;
import com.example.todo.dto.TodoResponseDto;
import com.example.todo.security.UserDetailsImpl;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todo")
    public TodoResponseDto createTodo(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody TodoRequestDto requestDto) {
        User user = userDetails.getUser();
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("user.getEmail() = " + user.getEmail());

        return todoService.createTodo(user, requestDto);
    }

    @PostMapping("/todo/{id}/comments")
    public CommentResponseDto addComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @Valid @RequestBody CommentRequestDto requestDto) {
        User user = userDetails.getUser();
        return todoService.addComment(id, user, requestDto);
    }


    @GetMapping("/todo")
    public List<TodoResponseDto> getTodoList() {
        return todoService.getTodoList();
    }

    @GetMapping("/todo/{id}")
    public TodoResponseDto getTodo(@PathVariable Long id) {
        return todoService.getTodo(id);
    }

    @GetMapping("/todo/{id}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long id) {
        return todoService.getComments(id);
    }


    @PutMapping("/todo/{id}")
    public TodoResponseDto updateTodo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        User user = userDetails.getUser();
        return todoService.updateTodo(id, user, requestDto);
    }

    @PutMapping("/todo/{id}/complete")
    public TodoResponseDto completeTodo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        User user = userDetails.getUser();
        return todoService.completeTodo(id, user);
    }

    @PutMapping("/comments/{id}")
    public CommentResponseDto updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        User user = userDetails.getUser();
        return todoService.updateComment(id, user, requestDto);
    }

    @DeleteMapping("/todo/{id}")
    public Long deleteTodo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        User user = userDetails.getUser();

        return todoService.deleteTodo(id, user);
    }

    @DeleteMapping("/comments/{id}")
    public Long deleteComments(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        User user = userDetails.getUser();

        return todoService.deleteComment(id, user);
    }
}