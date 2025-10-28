package com.universidad.biblioteca.backend_server.mongo;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class ResenaService {
    
    private final ResenaRepository repo;

    public ResenaService(ResenaRepository repo) { this.repo = repo; }

    public ResenaDoc crear(Integer idLibro, Integer usuarioId, String comentario, Integer calificacion) {
        // Validaciones simples
        if (calificacion == null || calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        if (repo.existsByIdLibroAndUsuarioId(idLibro, usuarioId)) {
            throw new RuntimeException("El usuario ya registró una reseña para este libro");
        }

        var d = new ResenaDoc();
        d.setIdLibro(idLibro);
        d.setUsuarioId(usuarioId);
        d.setComentario(comentario);
        d.setCalificacion(calificacion);
        d.setFecha(Instant.now());
        return repo.save(d);
    }

    public List<ResenaDoc> listarPorLibro(Integer idLibro) {
        return repo.findByIdLibroOrderByFechaDesc(idLibro);
    }

    public void responder(String idResena, Integer usuarioId, String comentario) {
        if (!ObjectId.isValid(idResena)) throw new IllegalArgumentException("idResena inválido");
        var r = repo.findById(new ObjectId(idResena))
                    .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
        var resp = new ResenaDoc.Respuesta();
        resp.setUsuarioId(usuarioId);
        resp.setComentario(comentario);
        resp.setFecha(Instant.now());
        r.getRespuestas().add(resp);
        repo.save(r);
    }

    public void eliminar(String idResena) {
        if (!ObjectId.isValid(idResena)) throw new IllegalArgumentException("idResena inválido");
        repo.deleteById(new ObjectId(idResena));
    }
}
