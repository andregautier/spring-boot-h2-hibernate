package com.myapi.service;

import com.myapi.model.User;
import java.util.Optional;

public interface UserService {
  User registerUser(User user);
  Optional<User> retrieveUserByName(String username);
}