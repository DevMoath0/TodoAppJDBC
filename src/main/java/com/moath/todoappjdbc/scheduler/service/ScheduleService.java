package com.moath.todoappjdbc.scheduler.service;

import com.moath.todoappjdbc.todo.model.TodoItem;
import com.moath.todoappjdbc.todo.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ScheduleService {

    private final TodoItemService todoItemService;
    private static final Logger logger = Logger.getLogger(ScheduleService.class.getName());
    private Boolean createItemFlag = false;
    private Boolean deleteItemFlag = false;

    @Autowired
    public ScheduleService(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @Scheduled(cron = "0 0 */6 * * *")
    public void deleteNonCompletedItems() {
        List<TodoItem> todoItems = todoItemService.getAll();
        Instant requiredTime = Instant.now().minus(6, ChronoUnit.HOURS);

        if(deleteItemFlag) {
            try{
                for(TodoItem item: todoItems){
                    if(item.getUpdatedAt() != null && item.getUpdatedAt().isBefore(requiredTime) && !item.getIsComplete()){
                        logger.info("Deleting non-completed item with id: " + item.getId() + " as it's older than 6 hours");
                        deleteItemSafely(item);
                    }
                }
            } catch (Exception e) {
                logger.severe("Error while deleting non-completed items: " + e.getMessage());
            }
        }else{
            logger.info("Delete Item Flag is disabled");
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    public void createNewItem() {
        if (createItemFlag) {
            logger.info("Creating item");
            try {
                TodoItem todoItem = new TodoItem();
                todoItem.setDescription("Scheduled item");
                todoItem.setIsComplete(false);
                todoItemService.save(todoItem);
            } catch (Exception e) {
                logger.severe("Error while creating item: " + e.getMessage());
            }
        } else {
            logger.info("Create Item Flag is disabled.");
        }
    }

    private void deleteItemSafely(TodoItem item) {
        try {
            todoItemService.delete(item.getId());
            logger.info("Successfully deleted item with id: " + item.getId());
        } catch (Exception e) {
            logger.severe("Error while deleting item with id " + item.getId() + ": " + e.getMessage());
        }
    }

    public Boolean getCreateItemFlag() {
        return createItemFlag;
    }

    public void setCreateItemFlag(Boolean createItemFlag) {
        this.createItemFlag = createItemFlag;
    }

    public Boolean getDeleteItemFlag() {
        return deleteItemFlag;
    }

    public void setDeleteItemFlag(Boolean deleteItemFlag) {
        this.deleteItemFlag = deleteItemFlag;
    }
}
