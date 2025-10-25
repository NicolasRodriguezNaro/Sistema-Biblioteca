package com.universidad.biblioteca.backend_server.repositories;

public interface PersonaRepository {
    void crearPersona(Integer numDocumento, String nombreUno, String apellidoUno, String direccion,
                      String telefono, String tipoDocumento, // texto, se castea al enum en el CALL
                      String nombreDos, String apellidoDos);

    void actualizar(Integer numDocumento, String nombreUno, String nombreDos, String apellidoUno,
        String apellidoDos, String telefono, String direccion, String tipoDocumento);
}
