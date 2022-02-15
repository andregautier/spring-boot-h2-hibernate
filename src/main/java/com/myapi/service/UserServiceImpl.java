package com.myapi.service;

import com.myapi.model.User;
import com.myapi.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User registerUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public Optional<User> retrieveUserByName(String username) {
    return userRepository.findByUsername(username);
  }
}