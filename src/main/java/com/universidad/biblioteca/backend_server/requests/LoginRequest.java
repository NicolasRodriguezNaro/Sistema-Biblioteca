package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank @Email private String correo;
    @NotBlank private String password;

    public String getCorreo(){return correo;} public void setCorreo(String v){this.correo=v;}
    public String getPassword(){return password;} public void setPassword(String v){this.password=v;}

}
