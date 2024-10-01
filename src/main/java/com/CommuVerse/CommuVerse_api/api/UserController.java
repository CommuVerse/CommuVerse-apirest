package com.CommuVerse.CommuVerse_api.api;

import java.util.Map;
import com.CommuVerse.CommuVerse_api.dto.UserDTO;
import com.CommuVerse.CommuVerse_api.mapper.UserMapper;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        User newUser = userService.registerUser(userDTO);
        UserDTO responseDTO = userMapper.toUserDTO(newUser);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest) {
        String nickname = loginRequest.get("nickname");
        String password = loginRequest.get("password");
    
        String token = userService.authenticate(nickname, password);
        if (token != null) {
            return ResponseEntity.ok("Login exitoso. Token: " + token);
        } else {
            return new ResponseEntity<>("Nickname o contraseña incorrectos.", HttpStatus.UNAUTHORIZED);
        }
    }
}
