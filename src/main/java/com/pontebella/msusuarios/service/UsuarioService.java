package com.pontebella.msusuarios.service;

import com.pontebella.msusuarios.dto.request.ActualizarPerfilRequest;
import com.pontebella.msusuarios.dto.request.ActualizarRolRequest;
import com.pontebella.msusuarios.dto.request.LoginRequest;
import com.pontebella.msusuarios.dto.request.RegistroRequest;
import com.pontebella.msusuarios.dto.response.LoginResponse;
import com.pontebella.msusuarios.dto.response.UsuarioResponse;

public interface UsuarioService {
    UsuarioResponse registrar(RegistroRequest request);
    LoginResponse login(LoginRequest request);
    UsuarioResponse buscarPorId(Long id);
    UsuarioResponse actualizarPerfil(Long id, ActualizarPerfilRequest request);
    UsuarioResponse actualizarRol(Long id, ActualizarRolRequest request);
    void desactivarUsuario(Long id);
    
}

