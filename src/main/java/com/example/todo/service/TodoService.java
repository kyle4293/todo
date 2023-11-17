package com.example.todo.service;


import com.example.todo.dto.CommentRequestDto;
import com.example.todo.dto.CommentResponseDto;
import com.example.todo.dto.TodoRequestDto;
import com.example.todo.dto.TodoResponseDto;
import com.example.todo.entity.Comment;
import com.example.todo.entity.ErrorCode;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.exception.CustomException;
import com.example.todo.repository.CommentRepository;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    public TodoService(TodoRepository todoRepository, CommentRepository commentRepository) {
        this.todoRepository = todoRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public TodoResponseDto createTodo(User user, TodoRequestDto requestDto) {
        // RequestDto -> Entity
        Todo todo = new Todo(user, requestDto);

        // DB 저장
        Todo saveTodo = todoRepository.save(todo);

        // Entity -> ResponseDto
        TodoResponseDto todoResponseDto = new TodoResponseDto(saveTodo);

        return todoResponseDto;
    }

    @Transactional
    public CommentResponseDto addComment(Long todo_id, User user, CommentRequestDto requestDto) {
        Todo todo = findTodo(todo_id);
        Comment comment = new Comment(user, todo, requestDto);
        Comment saveComment = commentRepository.save(comment);

        CommentResponseDto commentResponseDto = new CommentResponseDto(saveComment);

        return commentResponseDto;
    }

    @Transactional
    public List<TodoResponseDto> getTodoList() {
        // DB 조회
        return todoRepository.findAllByOrderByModifiedAtDesc().stream().map(TodoResponseDto::new).toList();
    }

    @Transactional
    public TodoResponseDto getTodo(Long id) {
        Todo todo = findTodo(id);
        TodoResponseDto todoResponseDto = new TodoResponseDto(todo);
        return todoResponseDto;
    }

    @Transactional
    public List<CommentResponseDto> getComments(Long id) {
        Todo todo = findTodo(id);

        List<Comment> comments = todo.getComments();
        List<CommentResponseDto> commentResponseDtos = comments.stream().map(CommentResponseDto::new).toList();

        return commentResponseDtos;
    }

    @Transactional
    public TodoResponseDto updateTodo(Long id, User user, TodoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Todo todo = findTodo(id);
        if (todo.getUser().getId()==user.getId()){
            // todo 내용 수정
            todo.update(requestDto);
        }
        else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        TodoResponseDto todoResponseDto = new TodoResponseDto(todo);
        return todoResponseDto;
    }

    @Transactional
    public TodoResponseDto completeTodo(Long id, User user) {
        // 해당 메모가 DB에 존재하는지 확인
        Todo todo = findTodo(id);
        if (todo.getUser().getId()==user.getId()){
            // todo 내용 수정
            todo.complete();
        }
        else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        TodoResponseDto todoResponseDto = new TodoResponseDto(todo);
        return todoResponseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, User user, CommentRequestDto requestDto) {
        Comment comment = findComment(id);
        if (comment.getUser().getId()==user.getId()) {
            comment.update(requestDto);
        } else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        return commentResponseDto;
    }

    @Transactional
    public Long deleteTodo(Long id, User user) {
        // 해당 메모가 DB에 존재하는지 확인
        Todo todo = findTodo(id);
        if (todo.getUser().getId()==user.getId()){
            // todo 삭제
            todoRepository.delete(todo);
        } else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        return id;
    }

    @Transactional
    public Long deleteComment(Long id, User user) {
        Comment comment = findComment(id);
        if (comment.getUser().getId()==user.getId()) {
            commentRepository.delete(comment);
        } else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        return id;
    }

    @Transactional
    private Todo findTodo(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> {
                    throw new CustomException(ErrorCode.INDEX_NOT_FOUND);
                }
        );
    }

    @Transactional
    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> {
                    throw new CustomException(ErrorCode.INDEX_NOT_FOUND);
                }
        );
    }
}