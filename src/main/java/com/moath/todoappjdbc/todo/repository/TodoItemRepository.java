package com.moath.todoappjdbc.todo.repository;

import com.moath.todoappjdbc.todo.model.TodoItem;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
public class TodoItemRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TodoItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

    public TodoItem save(TodoItem item) {
        String sql = "INSERT INTO todo_items (description, is_complete, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        Instant now = Instant.now();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getDescription());
            ps.setBoolean(2, item.getIsComplete());
            ps.setTimestamp(3, Timestamp.from(now));
            ps.setTimestamp(4, Timestamp.from(now));
            return ps;
        }, keyHolder);

        item.setId(keyHolder.getKey().longValue());
        item.setCreatedAt(now);
        item.setUpdatedAt(now);

        return item;
    }

    public void update(TodoItem item) {
        String sql = "UPDATE todo_items SET is_complete = ?, updated_at = ? WHERE id = ?";
        Instant now = Instant.now();
        jdbcTemplate.update(sql, item.getIsComplete(), now, item.getId());
        item.setUpdatedAt(now);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM todo_items WHERE id = ?";
        jdbcTemplate.update(sql, id);
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
