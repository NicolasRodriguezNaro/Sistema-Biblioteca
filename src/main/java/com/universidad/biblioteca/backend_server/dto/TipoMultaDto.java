package com.universidad.biblioteca.backend_server.dto;

import java.math.BigDecimal;

public class TipoMultaDto {
    private Integer id;        // pk_idtipomulta
    private String nombre;     // nombre_tipo
    private BigDecimal monto;  // monto

    public TipoMultaDto() {}
    public TipoMultaDto(Integer id, String nombre, BigDecimal monto) {
        this.id = id; this.nombre = nombre; this.monto = monto;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
}
