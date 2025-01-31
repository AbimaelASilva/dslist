package com.famdev.dslist.dto;

import com.famdev.dslist.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
    
}
