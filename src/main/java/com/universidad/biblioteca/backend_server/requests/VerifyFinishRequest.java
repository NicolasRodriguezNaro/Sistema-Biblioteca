package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.NotBlank;

public class VerifyFinishRequest {
    @NotBlank private String token;
    public String getToken(){return token;} public void setToken(String v){this.token=v;}
}
