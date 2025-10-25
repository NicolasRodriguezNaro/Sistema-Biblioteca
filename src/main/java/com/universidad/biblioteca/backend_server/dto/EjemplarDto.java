package com.universidad.biblioteca.backend_server.dto;

public class EjemplarDto {
    
    private Integer idLibro;       // pfk_idlibro
    private Integer numero;        // numero
    private String estado;         // estado_ejemplar (enum en BD)
    private String tituloLibro;    // lectura
    private Boolean prestableLibro;// lectura

    public EjemplarDto() {}

    public EjemplarDto(Integer idLibro, Integer numero, String estado, String tituloLibro, Boolean prestableLibro) {
        this.idLibro = idLibro;
        this.numero = numero;
        this.estado = estado;
        this.tituloLibro = tituloLibro;
        this.prestableLibro = prestableLibro;
    }

    public Integer getIdLibro() { return idLibro; }
    public void setIdLibro(Integer idLibro) { this.idLibro = idLibro; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
    public Boolean getPrestableLibro() { return prestableLibro; }
    public void setPrestableLibro(Boolean prestableLibro) { this.prestableLibro = prestableLibro; }

}
