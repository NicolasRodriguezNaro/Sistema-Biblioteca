package com.universidad.biblioteca.backend_server.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResenaRepository extends MongoRepository<ResenaDoc, ObjectId> {
    List<ResenaDoc> findByIdLibroOrderByFechaDesc(Integer idLibro);
    boolean existsByIdLibroAndUsuarioId(Integer idLibro, Integer usuarioId);
}