package com.example.todo.dto;

import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {
    private Long id;
    private Long user_id;
    private String title;
    private String contents;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.user_id = todo.getUser().getId();
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.status = todo.getStatus();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
    }
}
