package com.CommuVerse.CommuVerse_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private int id;
    private String name;
    private String nickName;
    private String email;
    private String password;
    private String bio;
    private Date dateOfBirth;  
    private String role;
    private boolean isActive;
}
