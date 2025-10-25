package com.universidad.biblioteca.backend_server.dto;

public class LibroDto {
    
    private Integer id;
    private String isbn;
    private String titulo;
    private String descripcion;
    private Integer cantidadEjemplares; 
    private String editorial;
    private Integer anioPublicacion;
    private Boolean prestable;
    private Integer idCategoria;
    private String nombreCategoria;
    
    public LibroDto() {
    }

    public LibroDto(Integer id, String isbn, String titulo, String descripcion, Integer cantidadEjemplares,
            String editorial, Integer anioPublicacion, Boolean prestable, Integer idCategoria, String nombreCategoria) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cantidadEjemplares = cantidadEjemplares;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.prestable = prestable;
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getCantidadEjemplares() {
        return cantidadEjemplares;
    }

    public void setCantidadEjemplares(Integer cantidadEjemplares) {
        this.cantidadEjemplares = cantidadEjemplares;
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

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
