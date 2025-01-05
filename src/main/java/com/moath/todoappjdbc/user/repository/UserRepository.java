package com.moath.todoappjdbc.user.repository;

import com.moath.todoappjdbc.user.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initializeDatabase() {
        String userSql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(100) NOT NULL UNIQUE, " +
                "password VARCHAR(255) NOT NULL, " +
                "phone_no VARCHAR(100) NOT NULL, " +
                "email VARCHAR(255) NOT NULL UNIQUE, " +
                "role BOOLEAN NOT NULL DEFAULT FALSE)";
        jdbcTemplate.execute(userSql);
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapToUser(rs));
    }

    public User findById(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> mapToUser(rs));
    }

    public void save(User user) {
        String sql = "INSERT INTO users (username, password, phone_no, email, role) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getPhone_no(), user.getEmail(), false);
    }

    public void update(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, phone_no = ?, email = ?, WHERE id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getPhone_no(), user.getEmail(), user.getId());
    }

    public void delete(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private User mapToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setPhone_no(rs.getString("phone_no"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getBoolean("role"));
        return user;
    }
}
