package com.moath.todoappjdbc.user.controller;

import com.moath.todoappjdbc.todo.model.TodoItem;
import com.moath.todoappjdbc.user.model.User;
import com.moath.todoappjdbc.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
@Tag(name = "User", description = "Endpoints for managing users.")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Fetch users", description = "Fetch all the users")
    @GetMapping("/fetch")
    public ResponseEntity<List<User>> fetch() {
        try{
            List<User> users = userService.getAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.severe("Error fetching all users: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Fetch a user", description = "Fetch a single user with an id")
    @GetMapping("/fetch/{id}")
    public ResponseEntity<User> getTodoById(@PathVariable Long id) {
        try {
            if (userService.getById(id) == null) {
                logger.warning("User with ID " + id + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(userService.getById(id));
        } catch (Exception e) {
            logger.severe("Error fetching user with ID " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Register a user", description = "Register a new user with credentials")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            // Check if the username or email already exists
            List<User> existingUsers = userService.getAll();
            boolean userExists = existingUsers.stream().anyMatch(
                    existingUser -> existingUser.getUsername().equals(user.getUsername()) ||
                            existingUser.getEmail().equals(user.getEmail()));

            if (userExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

            // Set default role for new users
            user.setRole(false); // Default role (non-admin)
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            logger.severe("Error registering user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @Operation(summary = "Delete a user", description = "Delete a single user with an id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
        try {
            if (userService.getById(id) == null) {
                logger.warning("Todo item with ID " + id + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.severe("Error deleting todo item with ID " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Update a user", description = "Update a single user with an id")
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateTodoStatusById(@PathVariable Long id) {
        try {
            User user = userService.getById(id);
            if (user == null) {
                logger.warning("Todo item with ID " + id + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            userService.update(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.severe("Error updating user with ID " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
