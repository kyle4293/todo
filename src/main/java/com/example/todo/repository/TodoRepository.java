package com.example.todo.repository;

import com.example.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // Query Methods: 메서드 이름으로 SQL을 생성(Select * ~)
    List<Todo> findAllByOrderByModifiedAtDesc();
}