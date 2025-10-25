package com.universidad.biblioteca.backend_server.dto;

public class PermisoDto {
    private Integer idPermiso;
    private String nombrePermiso;
    
    public PermisoDto() {}
    public PermisoDto(Integer id, String nombre){ this.idPermiso = id; this.nombrePermiso = nombre; }

    public Integer getIdPermiso() {
        return idPermiso;
    }
    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }
    public String getNombrePermiso() {
        return nombrePermiso;
    }
    public void setNombrePermiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }
}
