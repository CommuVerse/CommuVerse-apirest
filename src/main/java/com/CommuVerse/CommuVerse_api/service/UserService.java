package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.config.JwtUtil; 
import com.CommuVerse.CommuVerse_api.dto.UserDTO;
import com.CommuVerse.CommuVerse_api.mapper.UserMapper;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil; 

    @Transactional
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

    public String authenticate(String nickname, String password) {
        Optional<User> user = userRepository.findByNickName(nickname);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            // Generar el token JWT
            return jwtUtil.generateToken(nickname); 
        }

        return null;  
    }
}
