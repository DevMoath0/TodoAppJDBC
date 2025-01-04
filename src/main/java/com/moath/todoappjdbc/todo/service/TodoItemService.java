package com.moath.todoappjdbc.todo.service;

import com.moath.todoappjdbc.kafka.service.KafkaProducerService;
import com.moath.todoappjdbc.todo.model.TodoItem;
import com.moath.todoappjdbc.todo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public TodoItemService(TodoItemRepository todoItemRepository, KafkaProducerService kafkaProducerService) {
        this.todoItemRepository = todoItemRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public List<TodoItem> getAll(){
        return todoItemRepository.findAll();
    }

    public TodoItem getById(Long id){
        return todoItemRepository.findById(id);
    }

    public void save(TodoItem item){
        if(item.getId() == null){
            todoItemRepository.save(item);
            kafkaProducerService.sendMessage("todo-topic", "New TodoItem Created: " + item.getDescription());
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
