package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.dto.UserDTO;
import com.CommuVerse.CommuVerse_api.dto.auth.AuthRequest;
import com.CommuVerse.CommuVerse_api.dto.auth.AuthResponse;
import com.CommuVerse.CommuVerse_api.mapper.UserMapper;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO) {
        User newUser = userService.registerUser(userDTO);
        UserDTO responseDTO = userMapper.toUserDTO(newUser);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        String nickname = authRequest.getNickName();
        String password = authRequest.getPassword();

        String token = userService.authenticate(nickname, password);

        if (token != null) {
            AuthResponse authResponse = AuthResponse.builder()
                    .token(token)
                    .nickName(nickname)
                    .build();

            return ResponseEntity.ok(authResponse);
        } else {
            AuthResponse authResponse = AuthResponse.builder()
                    .nickName(nickname)
                    .message("Nickname o contraseña incorrectos")
                    .build();

            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/profile/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UserDTO> updateProfile(@PathVariable int userId, @Valid @RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token) {
        String extractedToken = token.substring(7);
        String username = userService.extractUsernameFromToken(extractedToken);

        if (!userService.isUserOwnerOfProfile(username, userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User updatedUser = userService.updateUserProfile(userId, userDTO);
        UserDTO responseDTO = userMapper.toUserDTO(updatedUser);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // Endpoint para cerrar sesión
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String extractedToken = token.substring(7);
        String username = userService.extractUsernameFromToken(extractedToken);

        if (userService.revokeToken(extractedToken)) {
            String name = userService.getUserNameByNickName(username);
            String goodbyeMessage = "ADIOS " + name;
            return new ResponseEntity<>(goodbyeMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Token inválido o no encontrado.", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{id}/deleteUser")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        boolean isDeleted = userService.deleteUserAccount(id);
        if (isDeleted) {
            return ResponseEntity.ok("La cuenta del usuario ha sido  eliminada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la cuenta del usuario.");
        }
    }

}
