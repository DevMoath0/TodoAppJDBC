package com.moath.todoappjdbc.service;

import com.moath.todoappjdbc.model.TodoItem;
import com.moath.todoappjdbc.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    public List<TodoItem> getAll(){
        return todoItemRepository.findAll();
    }

    public TodoItem getById(Long id){
        return todoItemRepository.findById(id);
    }

    public void save(TodoItem item){
        if(item.getId() == null){
            todoItemRepository.save(item);
        }else{
            todoItemRepository.update(item);
        }
    }

    public void update(TodoItem item){
        if(item.getId() != null){
            todoItemRepository.update(item);
        }
    }

    public void delete(Long id){
        todoItemRepository.deleteById(id);
    }

}
