package com.moath.todoappjdbc.todo.controller;

import com.moath.todoappjdbc.todo.model.TodoItem;
import com.moath.todoappjdbc.todo.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:8081") // Allow frontend origin
public class TodoController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping("/fetch")
    public ResponseEntity<List<TodoItem>> getAllTodos() {
        List<TodoItem> todoItems = todoItemService.getAll();
        return ResponseEntity.ok(todoItems);
    }

    @PostMapping("/create")
    public ResponseEntity<TodoItem> createTodo(@RequestBody TodoItem todoItem) {
        todoItemService.save(todoItem);
        return ResponseEntity.ok(todoItem);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<TodoItem> getTodoById(@PathVariable Long id) {
        try {
            TodoItem todoItem = todoItemService.getById(id);
            return ResponseEntity.ok(todoItem);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
        try {
            todoItemService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TodoItem> updateTodoStatusById(@PathVariable Long id) {
        try {
            TodoItem todoItem = todoItemService.getById(id);
            todoItem.setIsComplete(!todoItem.getIsComplete());
            todoItemService.update(todoItem);
            return ResponseEntity.ok(todoItem);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}