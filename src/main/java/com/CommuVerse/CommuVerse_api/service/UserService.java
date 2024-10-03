package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.config.JwtUtil;
import com.CommuVerse.CommuVerse_api.dto.UserDTO;
import com.CommuVerse.CommuVerse_api.exception.BadRequestException;
import com.CommuVerse.CommuVerse_api.mapper.UserMapper;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    // Método para registrar un nuevo usuario
    @Transactional
    public User registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("El correo electrónico ya está registrado.");
        }

        if (userRepository.findByNickName(userDTO.getNickName()).isPresent()) {
            throw new BadRequestException("El nickname ya existe.");
        }

        User user = userMapper.toUser(userDTO);
        user.setRegistrationDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    // Método para autenticar al usuario y generar un token JWT
    public String authenticate(String nickname, String password) {
        var user = userRepository.findByNickName(nickname);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return jwtUtil.generateToken(nickname);  // Genera el token JWT
        }

        return null;  // Si la autenticación falla
    }

    // Método para buscar un usuario por su nickname
    public User findByNickName(String nickname) {
        return userRepository.findByNickName(nickname)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
