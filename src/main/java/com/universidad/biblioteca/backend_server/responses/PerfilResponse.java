package com.universidad.biblioteca.backend_server.responses;

import java.util.List;

public class PerfilResponse {
    private Integer idUsuario;
    private String correo;
    private String estadoUsuario;     // 'activo' | 'inactivo' | 'suspendido'
    private Integer idRol;
    private String nombreRol;
    private Boolean correoVerificado;
    private Integer tokenVersion;
    private List<String> permisos;    // nombres de permisos

    public PerfilResponse() {}

    public PerfilResponse(Integer idUsuario, String correo, String estadoUsuario,
                          Integer idRol, String nombreRol, Boolean correoVerificado,
                          Integer tokenVersion, List<String> permisos) {
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.estadoUsuario = estadoUsuario;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.correoVerificado = correoVerificado;
        this.tokenVersion = tokenVersion;
        this.permisos = permisos;
    }

    // getters/setters
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getEstadoUsuario() { return estadoUsuario; }
    public void setEstadoUsuario(String estadoUsuario) { this.estadoUsuario = estadoUsuario; }
    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }
    public Boolean getCorreoVerificado() { return correoVerificado; }
    public void setCorreoVerificado(Boolean correoVerificado) { this.correoVerificado = correoVerificado; }
    public Integer getTokenVersion() { return tokenVersion; }
    public void setTokenVersion(Integer tokenVersion) { this.tokenVersion = tokenVersion; }
    public List<String> getPermisos() { return permisos; }
    public void setPermisos(List<String> permisos) { this.permisos = permisos; }
}
