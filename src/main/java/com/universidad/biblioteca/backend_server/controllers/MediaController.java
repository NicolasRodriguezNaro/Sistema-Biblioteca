package com.universidad.biblioteca.backend_server.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsResource;
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

import com.universidad.biblioteca.backend_server.mongo.MediaDto;
import com.universidad.biblioteca.backend_server.mongo.MediaService;

@RestController
@RequestMapping("/api/media")
public class MediaController {
    
    private final MediaService service;

    public MediaController(MediaService service) { this.service = service; }

    // ---- SUBIR ----
    @PreAuthorize("hasAnyRole('ADMIN','BIBLIOTECARIO')")
    @PostMapping(path = "/libro/{idLibro}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaDto> upload(@PathVariable Integer idLibro,
                                           @RequestParam("file") MultipartFile file,
                                           @RequestParam("tipoArchivo") String tipoArchivo,
                                           @RequestParam(value = "descripcion", required = false) String descripcion,
                                           @RequestParam Map<String,String> form) throws IOException {

        // Metadatos libres (sin casts inseguros)
        Set<String> reservadas = Set.of("file","tipoArchivo","descripcion");
        Map<String,Object> metadatos = form.entrySet().stream()
                .filter(e -> !reservadas.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (Object)e.getValue()));

        var saved = service.subir(
                idLibro, tipoArchivo,
                file.getOriginalFilename(),
                file.getContentType(),
                descripcion,
                metadatos,
                file.getInputStream()
        );
        return ResponseEntity.ok(MediaDto.of(saved));
    }

    // ---- LISTAR POR LIBRO (solo metadata, no binario) ----
    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<List<MediaDto>> listarPorLibro(@PathVariable Integer idLibro) {
        var list = service.listarPorLibro(idLibro).stream().map(MediaDto::of).toList();
        return ResponseEntity.ok(list);
    }

    // ---- METADATA POR ID ----
    @GetMapping("/{mediaId}")
    public ResponseEntity<?> metadata(@PathVariable String mediaId) {
        return service.metadata(new ObjectId(mediaId))
                .map(MediaDto::of)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(new Msg("No existe media")));
    }

    // ---- DESCARGAR BINARIO ----
    @GetMapping("/{mediaId}/archivo")
    public ResponseEntity<?> descargar(@PathVariable String mediaId) throws IOException {
        var opt = service.descargarBinarioHex(mediaId);
        if (opt.isEmpty()) return ResponseEntity.status(404).body(new Msg("No existe media o archivo"));
        GridFsResource res = opt.get();

        var bytes = res.getInputStream().readAllBytes();
        String ct = res.getContentType();
        if (ct == null || ct.isBlank()) ct = "application/octet-stream";
        String filename = (res.getFilename()!=null && !res.getFilename().isBlank()) ? res.getFilename() : "archivo";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ct));
        headers.setContentDisposition(ContentDisposition.inline().filename(filename).build());
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    // ---- ELIMINAR ----
    @PreAuthorize("hasAnyRole('ADMIN','BIBLIOTECARIO')")
    @DeleteMapping("/{mediaId}")
    public ResponseEntity<?> eliminar(@PathVariable String mediaId) {
        service.eliminar(new ObjectId(mediaId));
        return ResponseEntity.ok(new Msg("Media eliminado"));
    }

    // Mensaje simple
    static class Msg { public final String message; Msg(String m){ this.message=m; } }

}
