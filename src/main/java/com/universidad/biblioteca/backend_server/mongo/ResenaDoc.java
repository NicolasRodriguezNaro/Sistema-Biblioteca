package com.universidad.biblioteca.backend_server.mongo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@CompoundIndexes({
    // Opcional pero recomendado: evita 2 reseñas del mismo usuario sobre el mismo libro
    @CompoundIndex(name = "ux_libro_usuario", def = "{'idLibro': 1, 'usuarioId': 1}", unique = true)
})
public class ResenaDoc {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)  // <- 🔧 hace que el _id se emita como String
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
