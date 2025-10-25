package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.NotNull;

public class CambiarPrestableRequest {
    
    @NotNull
    private Boolean prestable;

    public Boolean getPrestable() {
        return prestable;
    }

    public void setPrestable(Boolean prestable) {
        this.prestable = prestable;
    }

}
