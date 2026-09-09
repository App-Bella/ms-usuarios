package com.pontebella.msusuarios.dto.request;

import com.pontebella.msusuarios.enums.RolUsuario;
import lombok.Data;

@Data
public class RegistroRequest {

    private String nombre;
    private String email;
    private String password;
    private RolUsuario rol;
    private String telefono;
}

