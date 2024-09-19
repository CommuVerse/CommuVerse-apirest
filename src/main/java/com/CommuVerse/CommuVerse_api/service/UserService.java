package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.dto.UserDTO;
import com.CommuVerse.CommuVerse_api.model.entity.User;

public interface UserService {
    User registerUser(UserDTO userDTO);
    boolean authenticate(String nickname, String password);  
}
