package com.moath.TodoAppJDBC.controller;

import com.moath.TodoAppJDBC.model.TodoItem;
import com.moath.TodoAppJDBC.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TodoController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("todoItems", todoItemService.getAll());
        return "index";
    }

    @GetMapping("/create")
    public String createForm(Model model){
        model.addAttribute("todoItem", new TodoItem());
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TodoItem todoItem){
        todoItemService.save(todoItem);
        return "redirect:/";
    }

    @PostMapping("/createpostman")
    public ResponseEntity<TodoItem> createpostman(@ModelAttribute TodoItem todoItem) {
        todoItemService.save(todoItem);
        return ResponseEntity.ok(todoItem); // Return the created item as JSON
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        todoItemService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id){
        TodoItem todoItem = todoItemService.getById(id);
        todoItem.setIsComplete(!todoItem.getIsComplete());
        todoItemService.update(todoItem);
        return "redirect:/";
    }

}
