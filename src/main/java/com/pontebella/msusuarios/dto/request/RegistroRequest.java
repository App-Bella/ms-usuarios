package com.pontebella.msusuarios.dto.request;

import lombok.Data;

@Data
public class RegistroRequest {

    private String nombre;
    private String email;
    private String password;
    private String telefono;
}

