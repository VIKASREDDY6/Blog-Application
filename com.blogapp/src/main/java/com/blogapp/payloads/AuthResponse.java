package com.blogapp.payloads;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessType = "Bearer ";
    private String jwtToken;
    public AuthResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
