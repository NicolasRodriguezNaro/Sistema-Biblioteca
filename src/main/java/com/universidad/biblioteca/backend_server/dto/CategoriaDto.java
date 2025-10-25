package com.universidad.biblioteca.backend_server.dto;

public class CategoriaDto {
    
    private Integer id;            // pk_idcategoria
    private String nombre;         // nombre_categoria
    
    public CategoriaDto() {
    }

    public CategoriaDto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
