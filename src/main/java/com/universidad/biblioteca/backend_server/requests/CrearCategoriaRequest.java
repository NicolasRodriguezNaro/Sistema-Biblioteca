package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.NotBlank;

public class CrearCategoriaRequest {
    
    @NotBlank
    private String nombre;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

}
