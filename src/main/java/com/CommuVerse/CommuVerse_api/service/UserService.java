package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.config.JwtUtil;
import com.CommuVerse.CommuVerse_api.dto.UserDTO;
import com.CommuVerse.CommuVerse_api.exception.BadRequestException;
import com.CommuVerse.CommuVerse_api.exception.ResourceNotFoundException;
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
    private final RevokedTokenService revokedTokenService;

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
        user.setSessionActive(false); // Inicialmente, la sesión está inactiva

        return userRepository.save(user);
    }

    public String authenticate(String nickname, String password) {
        var user = userRepository.findByNickName(nickname);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            user.get().setSessionActive(true); // Marcar la sesión como activa
            userRepository.save(user.get()); // Guardar el estado en la base de datos
            return jwtUtil.generateToken(nickname);  
        }

        return null;  
    }

    public boolean isUserOwnerOfProfile(String username, int userId) {
        return userRepository.findById(userId)
            .map(user -> user.getNickName().equals(username) && user.isSessionActive()) // Solo si la sesión está activa
            .orElse(false);
    }

    @Transactional
    public User updateUserProfile(int userId, UserDTO updatedUserDTO) {
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!existingUser.getNickName().equals(updatedUserDTO.getNickName()) &&
                userRepository.findByNickName(updatedUserDTO.getNickName()).isPresent()) {
            throw new BadRequestException("El nickname ya existe.");
        }

        // Validar que la sesión esté activa antes de permitir la edición del perfil
        if (!existingUser.isSessionActive()) {
            throw new BadRequestException("No se puede editar el perfil porque la sesión está inactiva.");
        }

        existingUser.setNickName(updatedUserDTO.getNickName());
        existingUser.setBio(updatedUserDTO.getBio());
        existingUser.setProfilePicture(updatedUserDTO.getProfilePicture());

        return userRepository.save(existingUser);
    }

    public String extractUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token);
    }

    public boolean revokeToken(String token) {
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByNickName(username)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        user.setSessionActive(false); // Marcar la sesión como inactiva
        userRepository.save(user); // Guardar el estado en la base de datos

        return revokedTokenService.revokeToken(token); // Revoca el token
    }

    public String getUserNameByNickName(String nickName) {
        return userRepository.findByNickName(nickName)
                .map(User::getName)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }
}
