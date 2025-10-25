package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.NotBlank;

public class RefreshRequest {
    @NotBlank private String refreshToken;
    public String getRefreshToken(){return refreshToken;}
    public void setRefreshToken(String v){this.refreshToken=v;}
}
