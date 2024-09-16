package com.CommuVerse.CommuVerse_api.mapper;

import com.CommuVerse.CommuVerse_api.dto.UserDTO;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO toUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
