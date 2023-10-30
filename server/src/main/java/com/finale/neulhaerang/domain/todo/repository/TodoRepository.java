package com.finale.neulhaerang.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.todo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
