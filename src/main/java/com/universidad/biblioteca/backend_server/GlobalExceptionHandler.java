package com.universidad.biblioteca.backend_server;

import java.sql.SQLException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Estructura base de error
    static class ApiError {
        public final Instant timestamp = Instant.now();
        public final int status;
        public final String error;
        public final String message;
        public final Map<String, Object> details;

        ApiError(HttpStatus status, String message) {
            this(status, message, null);
        }

        ApiError(HttpStatus status, String message, Map<String, Object> details) {
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.message = message;
            this.details = details;
        }
    }

    // 1) Errores de la capa de datos (SP, triggers, FKs, etc.)
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> handleDataAccess(DataAccessException ex) {
        String msg = extractDbMessage(ex);
        HttpStatus status = HttpStatus.BAD_REQUEST; // suele ser error de negocio/validación BD
        return ResponseEntity.status(status).body(new ApiError(status, msg));
    }

    // 2) Bean Validation @Valid en @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> details = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            details.put(fe.getField(), fe.getDefaultMessage());
        }
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(new ApiError(status, "Solicitud inválida", details));
    }

    // 3) Bean Validation @Validated en @PathVariable / @RequestParam
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> details = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(v -> details.put(v.getPropertyPath().toString(), v.getMessage()));
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(new ApiError(status, "Parámetros inválidos", details));
    }

    // 4) Acceso denegado
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(new ApiError(status, "Acceso denegado"));
    }

    // 5) Fallback general
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAny(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(new ApiError(status, ex.getMessage()));
    }

    // ===== Helpers =====
    private String extractDbMessage(DataAccessException ex) {
        Throwable root = ex.getMostSpecificCause();

        if (root instanceof SQLException sql) {
            String msg = nullSafe(sql.getMessage());

            // Si es Postgres, intenta extraer detalle si existe sin acoplar el tipo
            if ("org.postgresql.util.PSQLException".equals(sql.getClass().getName())) {
                // Intento por reflexión de obtener serverErrorMessage.message (opcional)
                try {
                    var getServerErrorMessage = sql.getClass().getMethod("getServerErrorMessage");
                    Object serverError = getServerErrorMessage.invoke(sql);
                    if (serverError != null) {
                        var getMessage = serverError.getClass().getMethod("getMessage");
                        Object serverMsg = getMessage.invoke(serverError);
                        if (serverMsg instanceof String s && !s.isBlank()) {
                            msg = s;
                        }
                    }
                } catch (Exception ignore) {
                    /* fallback a msg */ }
            }
            return msg;
        }
        return nullSafe(root != null ? root.getMessage() : ex.getMessage());
    }

    private String nullSafe(String s) {
        if (s == null || s.isBlank())
            return "Error en base de datos";
        // Opcional: limpia prefijos/ruido si quieres
        return s;
    }

}
