package com.universidad.biblioteca.backend_server.services;

import com.universidad.biblioteca.backend_server.requests.ActualizarPersonaRequest;

public interface PersonaService {
    void registrar(Integer numDocumento,
                      String nombreUno,
                      String apellidoUno,
                      String direccion,
                      String telefono,
                      String tipoDocumento, // texto, se castea al enum en el CALL
                      String nombreDos,
                      String apellidoDos);

    void actualizar(Integer numDocumento, ActualizarPersonaRequest req);
}
