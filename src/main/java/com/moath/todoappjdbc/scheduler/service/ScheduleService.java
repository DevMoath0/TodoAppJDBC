package com.moath.todoappjdbc.scheduler.service;

import com.moath.todoappjdbc.todo.model.TodoItem;
import com.moath.todoappjdbc.todo.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class
ScheduleService {

    @Autowired
    private TodoItemService todoItemService;

    private static final Logger logger = Logger.getLogger(ScheduleService.class.getName());

    private Boolean createUserFlag = false;

    @Scheduled(cron = "*/20 * * * * *")
    public void completeItem() {
        logger.info("Complete Item");
    }

    @Scheduled(cron = "0 * * * * *")
    public void createUser() {
        if (createUserFlag) {
            logger.info("Create User Flag is 1");
            TodoItem todoItem = new TodoItem();
            todoItem.setDescription("Scheduled item");
            todoItem.setIsComplete(false);
            todoItemService.save(todoItem);
        } else {
            logger.info("Create User Flag is 0");
        }
    }

    public Boolean getCreateUserFlag() {
        return createUserFlag;
    }

    public void setCreateUserFlag(Boolean createUserFlag) {
        this.createUserFlag = createUserFlag;
    }
}
