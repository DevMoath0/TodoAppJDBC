package com.moath.todoappjdbc.todo.service;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.moath.todoappjdbc.kafka.service.KafkaProducerService;
import com.moath.todoappjdbc.todo.model.TodoItem;
import com.moath.todoappjdbc.todo.repository.TodoItemRepository;
import com.moath.todoappjdbc.user.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;
    private static final Logger logger = Logger.getLogger(TodoItemService.class.getName());

    @Autowired
    public TodoItemService(TodoItemRepository todoItemRepository,
                           KafkaProducerService kafkaProducerService) {
        this.todoItemRepository = todoItemRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public List<TodoItem> getAll(){
        return todoItemRepository.findAll();
    }

    public TodoItem getById(Long id){
        return todoItemRepository.findById(id);
    }

    public TodoItem save(TodoItem item) {
        try {
            if (item.getId() == null) {
                TodoItem savedItem = todoItemRepository.save(item);
                String jsonMessage = objectMapper.writeValueAsString(savedItem);
                kafkaProducerService.sendMessage("todo-topic", jsonMessage);
                return savedItem;
            } else {
                todoItemRepository.update(item);
                return item;
            }
        } catch (Exception e) {
            logger.severe("Error saving todo item: " + e.getMessage());
            throw new RuntimeException("Failed to save todo item", e);
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
