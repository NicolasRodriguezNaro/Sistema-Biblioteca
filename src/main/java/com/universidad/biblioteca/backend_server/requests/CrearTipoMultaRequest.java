package com.universidad.biblioteca.backend_server.requests;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CrearTipoMultaRequest {
    @NotBlank private String nombre;
    @NotNull @DecimalMin("0.01") private BigDecimal monto;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
}
