package com.atos.service;

import com.atos.model.User;
import java.util.Optional;

public interface UserService {
  User registerUser(User user);
  Optional<User> retrieveUserByName(String username);
}