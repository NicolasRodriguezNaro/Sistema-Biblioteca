package com.universidad.biblioteca.backend_server.mongo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class ResenaDoc {
    @Id
    private ObjectId id;

    @Indexed
    private Integer idLibro;      // pk_idlibro (Postgres)

    @Indexed
    private Integer usuarioId;    // pfk_idpersona (Postgres)

    private String comentario;
    private Integer calificacion; // 1..5
    private Instant fecha;

    private List<Respuesta> respuestas = new ArrayList<>();

    public static class Respuesta {
        private Integer usuarioId;
        private String comentario;
        private Instant fecha;

        public Integer getUsuarioId() {
            return usuarioId;
        }
        public void setUsuarioId(Integer usuarioId) {
            this.usuarioId = usuarioId;
        }
        public String getComentario() {
            return comentario;
        }
        public void setComentario(String comentario) {
            this.comentario = comentario;
        }
        public Instant getFecha() {
            return fecha;
        }
        public void setFecha(Instant fecha) {
            this.fecha = fecha;
        }
    }

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

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

}
