package com.pontebella.msusuarios.dto.request;

import com.pontebella.msusuarios.enums.RolUsuario;

import lombok.Data;

@Data
public class ActualizarRolRequest {
    
    private RolUsuario nuevoRol;
}
