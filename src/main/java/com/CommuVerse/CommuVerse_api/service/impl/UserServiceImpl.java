package com.CommuVerse.CommuVerse_api.service.impl;

import com.CommuVerse.CommuVerse_api.dto.UserDTO;
import com.CommuVerse.CommuVerse_api.mapper.UserMapper;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.repository.UserRepository;
import com.CommuVerse.CommuVerse_api.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public User registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("The email is already registered.");
        }

        User user = userMapper.toUser(userDTO);

        if (user.getNickName() == null || user.getNickName().isEmpty()) {
            user.setNickName(user.getName().toLowerCase().replaceAll("\\s+", ""));  
        }

        if (user.getBio() == null || user.getBio().isEmpty()) {
            user.setBio("pendiente");  
        }

        user.setRegistrationDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    
    @Override
    public boolean authenticate(String nickname, String password) {
        Optional<User> user = userRepository.findByNickName(nickname);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return true;  
        }

        return false;  
    }
}
