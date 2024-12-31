package com.moath.TodoAppJDBC.model;

import java.time.Instant;

public class TodoItem {

    private String description;
    private Boolean isComplete;
    private Instant createdAt;
    private Instant updatedAt;


    private Long id;

    public Long getId() {
        return id;
    }

    public Boolean getIsComplete() {
        return isComplete;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setIsComplete(Boolean complete) {
        isComplete = complete;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
