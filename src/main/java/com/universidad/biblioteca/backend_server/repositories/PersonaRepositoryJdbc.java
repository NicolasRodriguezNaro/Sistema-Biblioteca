package com.universidad.biblioteca.backend_server.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaRepositoryJdbc implements PersonaRepository{

    private final JdbcTemplate jdbc;
    public PersonaRepositoryJdbc(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    @Override
    public void crearPersona(Integer numDocumento,
                             String nombreUno,
                             String apellidoUno,
                             String direccion,
                             String telefono,
                             String tipoDocumento,
                             String nombreDos,
                             String apellidoDos) {
        final String call = """
            CALL sp_crear_persona(
              ?, ?, ?, ?, ?, ?::tipo_documento, ?, ?
            )
            """;
        try {
            jdbc.update(call,
                numDocumento, nombreUno, apellidoUno, direccion, telefono,
                tipoDocumento, nombreDos, apellidoDos
            );
        } catch (DataAccessException dae) {
            throw wrap("Error al crear persona", dae);
        }
    }

    @Override
    public void actualizar(Integer numDocumento,
                           String nombreUno,
                           String nombreDos,
                           String apellidoUno,
                           String apellidoDos,
                           String telefono,
                           String direccion,
                           String tipoDocumento) {
        // NOTA: usamos CAST(? AS esquema_personas.tipo_documento) para que acepte NULL sin problemas
        final String call = """
            CALL sp_actualizar_persona(
              ?, ?, ?, ?, ?, ?, ?, CAST(? AS tipo_documento)
            )
            """;
        try {
            jdbc.update(call, numDocumento, nombreUno, nombreDos, apellidoUno, apellidoDos,
                    telefono, direccion, tipoDocumento);
        } catch (DataAccessException dae) {
            throw wrap("Error al actualizar persona", dae);
        }
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null ? dae.getMostSpecificCause().getMessage() : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
