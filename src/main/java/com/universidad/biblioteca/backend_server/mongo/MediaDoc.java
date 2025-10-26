package com.universidad.biblioteca.backend_server.mongo;

import java.time.Instant;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("libros_medio")
public class MediaDoc {
    @Id
    private ObjectId id;

    @Indexed
    private Integer idLibro;              // referencia a Postgres

    private String tipoArchivo;           // "imagen_portada" | "pdf" | "audio" | etc
    private String nombreArchivo;         // nombre lógico
    private String contentType;           // MIME del binario
    private Instant fechaSubida;
    private String descripcion;           // opcional

    private ObjectId gridFsId;            // ID del archivo en GridFS (binario)
    private Map<String, Object> metadatos;// {"resolucion":"1080x720","pesoMb":2.3,...}
    
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public Integer getIdLibro() {
        return idLibro;
    }
    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }
    public String getTipoArchivo() {
        return tipoArchivo;
    }
    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }
    public String getNombreArchivo() {
        return nombreArchivo;
    }
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public Instant getFechaSubida() {
        return fechaSubida;
    }
    public void setFechaSubida(Instant fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public ObjectId getGridFsId() {
        return gridFsId;
    }
    public void setGridFsId(ObjectId gridFsId) {
        this.gridFsId = gridFsId;
    }
    public Map<String, Object> getMetadatos() {
        return metadatos;
    }
    public void setMetadatos(Map<String, Object> metadatos) {
        this.metadatos = metadatos;
    }

    
}
