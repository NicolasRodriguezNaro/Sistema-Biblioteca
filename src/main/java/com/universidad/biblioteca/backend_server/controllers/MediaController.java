package com.universidad.biblioteca.backend_server.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.universidad.biblioteca.backend_server.mongo.MediaDoc;
import com.universidad.biblioteca.backend_server.mongo.MediaService;

@RestController
@RequestMapping("/api/media")
public class MediaController {
    
    private final MediaService service;
    public MediaController(MediaService service) { this.service = service; }

    @PreAuthorize("hasAnyRole('ADMIN','BIBLIOTECARIO')")
    @PostMapping(path = "/libro/{idLibro}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaDoc> upload(@PathVariable Integer idLibro,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam("tipoArchivo") String tipoArchivo,
                                        @RequestParam(value = "descripcion", required = false) String descripcion,
                                        @RequestParam Map<String,String> form) throws IOException {

        // Construir metadatos sin tocar el mapa original y sin casts inseguros
        java.util.Map<String,Object> metadatos = new java.util.HashMap<>();
        for (var e : form.entrySet()) {
            String k = e.getKey();
            if (!"file".equals(k) && !"tipoArchivo".equals(k) && !"descripcion".equals(k)) {
                metadatos.put(k, e.getValue()); // String es asignable a Object sin cast
            }
        }

        var doc = service.subir(
            idLibro, tipoArchivo, file.getOriginalFilename(),
            file.getContentType(), descripcion,
            metadatos, file.getInputStream()
        );
        return ResponseEntity.ok(doc);
    }

    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<List<MediaDoc>> listar(@PathVariable Integer idLibro) {
        return ResponseEntity.ok(service.listarPorLibro(idLibro));
    }

    @GetMapping("/{mediaId}/archivo")
    public ResponseEntity<?> descargar(@PathVariable String mediaId) throws IOException {
        var opt = service.descargarBinario(new ObjectId(mediaId));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        var res = opt.get();
        var bytes = res.getInputStream().readAllBytes();

        String ct = res.getContentType();
        if (ct == null || ct.isBlank()) ct = "application/octet-stream";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ct));
        String filename = (res.getFilename() != null && !res.getFilename().isBlank())
                ? res.getFilename() : "archivo";
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());

        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    @PreAuthorize("hasAnyRole('ADMIN','BIBLIOTECARIO')")
    @DeleteMapping("/{mediaId}")
    public ResponseEntity<?> eliminar(@PathVariable String mediaId) {
        service.eliminar(new ObjectId(mediaId));
        return ResponseEntity.ok(new Msg("Media eliminado"));
    }

    static class Msg { public final String message; Msg(String m){ this.message = m; } }

}
