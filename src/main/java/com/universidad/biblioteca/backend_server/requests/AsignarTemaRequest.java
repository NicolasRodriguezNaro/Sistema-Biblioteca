package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.NotNull;

public class AsignarTemaRequest {
    
    @NotNull
    private Integer idTema;

    public Integer getIdTema() { return idTema; }
    public void setIdTema(Integer idTema) { this.idTema = idTema; }
}
