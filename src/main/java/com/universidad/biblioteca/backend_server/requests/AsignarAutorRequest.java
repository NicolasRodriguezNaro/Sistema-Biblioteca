package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.NotNull;

public class AsignarAutorRequest {
    @NotNull
    private Integer idAutor;

    public Integer getIdAutor() { return idAutor; }
    public void setIdAutor(Integer idAutor) { this.idAutor = idAutor; }
}
