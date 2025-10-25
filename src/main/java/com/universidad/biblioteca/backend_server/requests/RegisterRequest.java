package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotNull private Integer numDocumento;
    @NotBlank private String nombreUno;
    private String nombreDos;
    @NotBlank private String apellidoUno;
    private String apellidoDos;
    @NotBlank private String direccion;
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos numéricos")
    private String telefono;
    @NotBlank(message = "tipoDocumento es requerido (cedula de ciudadania|tarjeta de identidad|cedula de extranjeria)")
    private String tipoDocumento; // enum de esquema_personas.tipo_documento (texto exacto)

    // USUARIO
    @NotBlank @Email private String correo;
    @NotBlank @Size(min=8, max=100) private String password;
    @NotNull private Integer idRol;

    // getters y setters...
    public Integer getNumDocumento(){return numDocumento;}
    public void setNumDocumento(Integer v){this.numDocumento=v;}
    public String getNombreUno(){return nombreUno;}
    public void setNombreUno(String v){this.nombreUno=v;}
    public String getNombreDos(){return nombreDos;}
    public void setNombreDos(String v){this.nombreDos=v;}
    public String getApellidoUno(){return apellidoUno;}
    public void setApellidoUno(String v){this.apellidoUno=v;}
    public String getApellidoDos(){return apellidoDos;}
    public void setApellidoDos(String v){this.apellidoDos=v;}
    public String getDireccion(){return direccion;}
    public void setDireccion(String v){this.direccion=v;}
    public String getTelefono(){return telefono;}
    public void setTelefono(String v){this.telefono=v;}
    public String getTipoDocumento(){return tipoDocumento;}
    public void setTipoDocumento(String v){this.tipoDocumento=v;}
    public String getCorreo(){return correo;}
    public void setCorreo(String v){this.correo=v;}
    public String getPassword(){return password;}
    public void setPassword(String v){this.password=v;}
    public Integer getIdRol(){return idRol;}
    public void setIdRol(Integer v){this.idRol=v;}

}
