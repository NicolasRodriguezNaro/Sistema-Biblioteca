package com.universidad.biblioteca.backend_server.dto;

public class PerfilDto {
    private Integer idUsuario;
    private String correo;
    private String estadoUsuario;  // 'activo' | 'inactivo' | 'suspendido'
    private Integer idRol;
    private String nombreRol;
    private Integer numDocumento;
    private String nombreUno;
    private String nombreDos;
    private String apellidoUno;
    private String apellidoDos;
    private String telefono;
    private String direccion;
    private String tipoDocumento;  // enum text
    private Boolean correoVerificado;
    private Integer tokenVersion;

    public PerfilDto() {}
    public PerfilDto(Integer idUsuario, String correo, String estadoUsuario, Integer idRol, String nombreRol,
                     Integer numDocumento, String nombreUno, String nombreDos, String apellidoUno, String apellidoDos,
                     String telefono, String direccion, String tipoDocumento, Boolean correoVerificado, Integer tokenVersion) {
        this.idUsuario = idUsuario; this.correo = correo; this.estadoUsuario = estadoUsuario;
        this.idRol = idRol; this.nombreRol = nombreRol; this.numDocumento = numDocumento;
        this.nombreUno = nombreUno; this.nombreDos = nombreDos; this.apellidoUno = apellidoUno; this.apellidoDos = apellidoDos;
        this.telefono = telefono; this.direccion = direccion; this.tipoDocumento = tipoDocumento;
        this.correoVerificado = correoVerificado; this.tokenVersion = tokenVersion;
    }
    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getEstadoUsuario() {
        return estadoUsuario;
    }
    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }
    public Integer getIdRol() {
        return idRol;
    }
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
    public String getNombreRol() {
        return nombreRol;
    }
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    public Integer getNumDocumento() {
        return numDocumento;
    }
    public void setNumDocumento(Integer numDocumento) {
        this.numDocumento = numDocumento;
    }
    public String getNombreUno() {
        return nombreUno;
    }
    public void setNombreUno(String nombreUno) {
        this.nombreUno = nombreUno;
    }
    public String getNombreDos() {
        return nombreDos;
    }
    public void setNombreDos(String nombreDos) {
        this.nombreDos = nombreDos;
    }
    public String getApellidoUno() {
        return apellidoUno;
    }
    public void setApellidoUno(String apellidoUno) {
        this.apellidoUno = apellidoUno;
    }
    public String getApellidoDos() {
        return apellidoDos;
    }
    public void setApellidoDos(String apellidoDos) {
        this.apellidoDos = apellidoDos;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    public Boolean getCorreoVerificado() {
        return correoVerificado;
    }
    public void setCorreoVerificado(Boolean correoVerificado) {
        this.correoVerificado = correoVerificado;
    }
    public Integer getTokenVersion() {
        return tokenVersion;
    }
    public void setTokenVersion(Integer tokenVersion) {
        this.tokenVersion = tokenVersion;
    }
}
