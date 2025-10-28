package com.universidad.biblioteca.backend_server.mongo;

import java.time.Instant;
import java.util.Map;

import org.bson.types.ObjectId;

public class MediaDto {
    public String id;
    public Integer idLibro;
    public String tipoArchivo;
    public String nombreArchivo;
    public String contentType;
    public Instant fechaSubida;
    public String descripcion;
    public String gridFsId;
    public Map<String,Object> metadatos;

    public static MediaDto of(MediaDoc d){
        MediaDto m = new MediaDto();
        m.id = d.getId()!=null ? d.getId().toHexString() : null;
        m.idLibro = d.getIdLibro();
        m.tipoArchivo = d.getTipoArchivo();
        m.nombreArchivo = d.getNombreArchivo();
        m.contentType = d.getContentType();
        m.fechaSubida = d.getFechaSubida();
        m.descripcion = d.getDescripcion();
        m.gridFsId = d.getGridFsId()!=null ? d.getGridFsId().toHexString() : null;
        m.metadatos = d.getMetadatos();
        return m;
    }

    public static String hex(ObjectId oid){
        return oid!=null ? oid.toHexString() : null;
    }
}
