package com.universidad.biblioteca.backend_server.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaRepository extends MongoRepository<MediaDoc, ObjectId> {
    List<MediaDoc> findByIdLibroOrderByFechaSubidaDesc(Integer idLibro);
}
