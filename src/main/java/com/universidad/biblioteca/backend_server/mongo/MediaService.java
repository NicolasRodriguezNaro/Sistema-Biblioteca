package com.universidad.biblioteca.backend_server.mongo;

import java.io.InputStream;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MediaService {
    private final MediaRepository repo;
    private final GridFsTemplate gridfs;

    public MediaService(MediaRepository repo, GridFsTemplate gridfs) {
        this.repo = repo; this.gridfs = gridfs;
    }

    public MediaDoc subir(Integer idLibro, String tipoArchivo, String nombreArchivo,
                          String contentType, String descripcion, Map<String,Object> metadatos,
                          InputStream data) {
        Document meta = new Document();
        meta.append("idLibro", idLibro);
        meta.append("tipoArchivo", tipoArchivo);
        meta.append("nombreArchivo", nombreArchivo);
        if (metadatos != null) meta.append("metadatos", new Document(metadatos));

        ObjectId fileId = gridfs.store(data, nombreArchivo, contentType, meta);

        MediaDoc doc = new MediaDoc();
        doc.setIdLibro(idLibro);
        doc.setTipoArchivo(tipoArchivo);
        doc.setNombreArchivo(nombreArchivo);
        doc.setContentType(contentType);
        doc.setFechaSubida(Instant.now());
        doc.setDescripcion(descripcion);
        doc.setGridFsId(fileId);
        doc.setMetadatos(metadatos);
        return repo.save(doc);
    }

    public java.util.List<MediaDoc> listarPorLibro(Integer idLibro) {
        return repo.findByIdLibroOrderByFechaSubidaDesc(idLibro);
    }

    public Optional<MediaDoc> metadata(ObjectId id) {
        return repo.findById(id);
    }

    public Optional<GridFsResource> descargarBinario(ObjectId mediaId) {
        return repo.findById(mediaId).flatMap(doc -> {
            var file = gridfs.findOne(new Query(Criteria.where("_id").is(doc.getGridFsId())));
            if (file == null) return Optional.empty();
            return Optional.of(gridfs.getResource(file)); // <- ahora sí
        });
    }

    public void eliminar(ObjectId mediaId) {
        var doc = repo.findById(mediaId).orElse(null);
        if (doc != null) {
            gridfs.delete(new org.springframework.data.mongodb.core.query.Query(
                org.springframework.data.mongodb.core.query.Criteria.where("_id").is(doc.getGridFsId())
            ));
            repo.deleteById(mediaId);
        }
    }

    public java.util.Optional<org.springframework.data.mongodb.gridfs.GridFsResource> descargarBinarioHex(String mediaIdHex) {
        return descargarBinario(new ObjectId(mediaIdHex));
    }

    public Optional<MediaDoc> metadataHex(String mediaIdHex) {
        return metadata(new ObjectId(mediaIdHex));
    }
}
