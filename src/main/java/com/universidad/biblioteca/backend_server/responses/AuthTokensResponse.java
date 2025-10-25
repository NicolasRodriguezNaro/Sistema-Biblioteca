package com.universidad.biblioteca.backend_server.responses;

public class AuthTokensResponse {
    public final String accessToken;
    public final String refreshToken;
    public AuthTokensResponse(String a, String r){this.accessToken=a; this.refreshToken=r;}
}
