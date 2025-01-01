package com.moath.todoappjdbc.repository;

import com.moath.todoappjdbc.model.TodoItem;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.Instant;
import java.util.List;

@Repository
public class TodoItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS todo_items (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "description VARCHAR(255) NOT NULL, " +
                "is_complete BOOLEAN DEFAULT FALSE, " +
                "created_at TIMESTAMP, " +
                "updated_at TIMESTAMP)";
        jdbcTemplate.execute(sql);
    }

    public List<TodoItem> findAll(){
        String sql = "SELECT * FROM todo_items";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToDoItem(rs));
    }

    public TodoItem findById(Long id) {
        String sql = "SELECT * FROM todo_items WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> mapRowToDoItem(rs));
    }

    public void save(TodoItem item) {
        String sql = "INSERT INTO todo_items (description, is_complete, created_at, updated_at) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, item.getDescription(), item.getIsComplete(),
                Instant.now(), Instant.now());
    }

    public void update(TodoItem item) {
        String sql = "UPDATE todo_items SET  is_complete = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, item.getIsComplete(),
                Instant.now(), item.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM todo_items WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private TodoItem mapRowToDoItem(ResultSet rs) throws java.sql.SQLException{
        TodoItem item = new TodoItem();
        item.setId(rs.getLong("id"));
        item.setDescription(rs.getString("description"));
        item.setIsComplete(rs.getBoolean("is_complete"));
        item.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        item.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());
        return item;
    }

}
