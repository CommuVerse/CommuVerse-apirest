package com.CommuVerse.CommuVerse_api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserErrorResponse {

    private LocalDateTime datetime;
    private String message;
    private String details;
}
