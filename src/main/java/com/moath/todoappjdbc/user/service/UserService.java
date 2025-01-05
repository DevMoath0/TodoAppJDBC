package com.moath.todoappjdbc.user.service;

import com.moath.todoappjdbc.user.model.User;
import com.moath.todoappjdbc.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password
        userRepository.save(user);
    }

    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password
        userRepository.update(user);
    }

    public void deleteById(Long id) {
        userRepository.delete(id);
    }
}
