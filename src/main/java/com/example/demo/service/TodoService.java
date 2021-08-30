package com.example.demo.service;

import com.example.demo.entity.TodoEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.TodoNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    public Todo createTodo(TodoEntity todo, Long userId) throws UserNotFoundException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));
        todo.setUser(user);
        return Todo.toModel(todoRepository.save(todo));
    }

    public Todo completeTodo(Long id) throws TodoNotFoundException {
        TodoEntity todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found by id: " + id));
        todo.setCompleted(!todo.getCompleted());
        return Todo.toModel(todoRepository.save(todo));
    }
}
