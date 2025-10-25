package com.universidad.biblioteca.backend_server.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.biblioteca.backend_server.repositories.PersonaRepository;
import com.universidad.biblioteca.backend_server.requests.ActualizarPersonaRequest;

@Service
public class PersonaServiceImpl implements PersonaService{

    private final PersonaRepository personaRepository;

    public PersonaServiceImpl(PersonaRepository personaRepository){
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional
    public void registrar(Integer numDocumento, String nombreUno, String apellidoUno, String direccion,
                          String telefono, String tipoDocumento, String nombreDos, String apellidoDos) {
        personaRepository.crearPersona(numDocumento, trim(nombreUno), trim(apellidoUno), trim(direccion),
                trim(telefono), trim(tipoDocumento), trimOrNull(nombreDos), trimOrNull(apellidoDos));
    }

    @Override
    @Transactional
    public void actualizar(Integer numDocumento, ActualizarPersonaRequest req) {
        personaRepository.actualizar(
                numDocumento,
                blankToNull(req.getNombreUno()),
                blankToNull(req.getNombreDos()),
                blankToNull(req.getApellidoUno()),
                blankToNull(req.getApellidoDos()),
                blankToNull(req.getTelefono()),
                blankToNull(req.getDireccion()),
                blankToNull(req.getTipoDocumento())
        );
    }

    // ===== Helpers de normalización =====
    private static String trim(String s){ return s == null ? null : s.trim(); }
    private static String trimOrNull(String s){
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static String blankToNull(String s){
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
        // dejamos NULL para que el SP no cambie ese campo (usa COALESCE en la BD)
    }

}
