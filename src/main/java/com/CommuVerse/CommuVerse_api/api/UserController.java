package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.dto.UserDTO;
import com.CommuVerse.CommuVerse_api.dto.auth.AuthRequest;
import com.CommuVerse.CommuVerse_api.dto.auth.AuthResponse;
import com.CommuVerse.CommuVerse_api.mapper.UserMapper;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.service.RevokedTokenService;
import com.CommuVerse.CommuVerse_api.service.UserService;
import com.CommuVerse.CommuVerse_api.config.JwtUtil;
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
    private final RevokedTokenService revokedTokenService;
    private final JwtUtil jwtUtil;  // Inyectar JwtUtil

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

        // Autenticar al usuario con el servicio
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


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7); // Eliminar "Bearer " del token
        String username = jwtUtil.extractUsername(jwt); // Extraer el nickname del token

        // Obtener el usuario por su nickname
        User user = userService.findByNickName(username);

        
        revokedTokenService.revokeToken(jwt);

        
        System.out.println("El usuario " + user.getName() + " (nickname: " + username + ") ha cerrado sesión.");

        return ResponseEntity.ok("Has cerrado sesión exitosamente.");
    }
@PutMapping("/profile/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")  // Requiere que el usuario esté autenticado
    public ResponseEntity<UserDTO> updateProfile(@PathVariable int userId, @Valid @RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token) {
        // Validar el token y obtener el nickname del usuario autenticado
        String extractedToken = token.substring(7);  // Quitar "Bearer " del token
        String username = userService.extractUsernameFromToken(extractedToken);

        // Asegurarse de que el usuario autenticado solo edite su propio perfil
        if (!userService.isUserOwnerOfProfile(username, userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Actualizar el perfil
        User updatedUser = userService.updateUserProfile(userId, userDTO);
        UserDTO responseDTO = userMapper.toUserDTO(updatedUser);

        // Mensaje de éxito con los cambios realizados
        String message = "Los cambios han sido realizados con éxito. " +
                            "Cambios: nickname: " + updatedUser.getNickName() + 
                            ", biografía: " + updatedUser.getBio() + 
                            ", foto de perfil: " + updatedUser.getProfilePicture();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
