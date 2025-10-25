package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ActualizarPersonaRequest {
    private String nombreUno;
    private String nombreDos;
    private String apellidoUno;
    private String apellidoDos;

    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos numéricos")
    private String telefono;

    @Size(max = 20, message = "Dirección máximo 20 caracteres")
    private String direccion;

    /** Debe coincidir con el enum: 'cedula de ciudadania'|'tarjeta de identidad'|'cedula de extranjeria' */
    private String tipoDocumento;

    public String getNombreUno() { return nombreUno; }
    public void setNombreUno(String nombreUno) { this.nombreUno = nombreUno; }
    public String getNombreDos() { return nombreDos; }
    public void setNombreDos(String nombreDos) { this.nombreDos = nombreDos; }
    public String getApellidoUno() { return apellidoUno; }
    public void setApellidoUno(String apellidoUno) { this.apellidoUno = apellidoUno; }
    public String getApellidoDos() { return apellidoDos; }
    public void setApellidoDos(String apellidoDos) { this.apellidoDos = apellidoDos; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
}
