package com.pontebella.msusuarios.service;

import com.pontebella.msusuarios.dto.request.LoginRequest;
import com.pontebella.msusuarios.dto.request.RegistroRequest;
import com.pontebella.msusuarios.dto.response.UsuarioResponse;

public interface UsuarioService {
    UsuarioResponse registrar(RegistroRequest request);
    UsuarioResponse login(LoginRequest request);
    UsuarioResponse buscarPorId(Long id);
}
