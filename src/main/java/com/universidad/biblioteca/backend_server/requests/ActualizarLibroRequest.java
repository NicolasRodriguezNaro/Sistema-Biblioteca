package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ActualizarLibroRequest {
    
    @NotBlank private String isbn;
    @NotBlank private String titulo;
    @NotBlank private String descripcion;
    @NotBlank private String editorial;
    @NotNull  private Integer anioPublicacion;
    @NotNull  private Boolean prestable;
    @NotNull  private Integer idCategoria;

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getEditorial() {
        return editorial;
    }
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }
    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    public Boolean getPrestable() {
        return prestable;
    }
    public void setPrestable(Boolean prestable) {
        this.prestable = prestable;
    }
    public Integer getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

}
