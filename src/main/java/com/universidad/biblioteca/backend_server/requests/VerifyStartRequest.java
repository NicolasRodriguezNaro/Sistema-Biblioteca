package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class VerifyStartRequest {
    @NotBlank @Email private String correo;
    public String getCorreo(){return correo;} public void setCorreo(String v){this.correo=v;}
}
