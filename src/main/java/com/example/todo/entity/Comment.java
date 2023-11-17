package com.example.todo.entity;

import com.example.todo.dto.CommentRequestDto;
import com.example.todo.dto.TodoRequestDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;
    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(User user, Todo todo, CommentRequestDto requestDto) {
        this.user = user;
        this.todo = todo;
        this.contents = requestDto.getContents();
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
