package com.trustrent.backend.dto;

import java.util.UUID;

public class UserResponse {
    public UUID id;
    public String name;
    public String email;
    public String phone;
    public String role;

    public UserResponse(UUID id, String name, String email, String phone, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
}
