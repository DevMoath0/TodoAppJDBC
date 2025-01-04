package com.moath.todoappjdbc.todo.controller;

import com.moath.todoappjdbc.common.ApiResponse;
import com.moath.todoappjdbc.todo.model.TodoItem;
import com.moath.todoappjdbc.todo.service.TodoItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:8081") // Allow frontend origin
@Tag(name = "Todo", description = "Endpoints for managing todo items.")
public class TodoController {

    private static final Logger logger = Logger.getLogger(TodoController.class.getName());
    private final TodoItemService todoItemService;

    @Autowired
    public TodoController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @Operation(summary = "Fetch all todo items", description = "Fetching all the items available")
    @GetMapping("/fetch")
    public ResponseEntity<List<TodoItem>> getAllTodos() {
        try{
            List<TodoItem> todoItems = todoItemService.getAll();
            return ResponseEntity.ok(todoItems);
        } catch (Exception e) {
            logger.severe("Error fetching all todos: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Create an item", description = "Create a new item and add it to the db")
    @PostMapping("/create")
    public ResponseEntity<TodoItem> createTodo(@RequestBody TodoItem todoItem) {
        try {
            todoItemService.save(todoItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(todoItem);
        } catch (Exception e) {
            logger.severe("Error creating a todo item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Fetch an item", description = "Fetch a single item with an id")
    @GetMapping("/fetch/{id}")
    public ResponseEntity<TodoItem> getTodoById(@PathVariable Long id) {
        try {
            if (todoItemService.getById(id) == null) {
                logger.warning("Todo item with ID " + id + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(todoItemService.getById(id));
        } catch (Exception e) {
            logger.severe("Error fetching todo item with ID " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Delete an item", description = "Delete a single item with an id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
        try {
            if (todoItemService.getById(id) == null) {
                logger.warning("Todo item with ID " + id + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            todoItemService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.severe("Error deleting todo item with ID " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Update an item", description = "Update a single item with an id")
    @PutMapping("/update/{id}")
    public ResponseEntity<TodoItem> updateTodoStatusById(@PathVariable Long id) {
        try {
            TodoItem todoItem = todoItemService.getById(id);
            if (todoItem == null) {
                logger.warning("Todo item with ID " + id + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            todoItem.setIsComplete(!todoItem.getIsComplete());
            todoItemService.update(todoItem);
            return ResponseEntity.ok(todoItem);
        } catch (Exception e) {
            logger.severe("Error updating todo item with ID " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}