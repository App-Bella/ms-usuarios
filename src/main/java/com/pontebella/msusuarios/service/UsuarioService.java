package com.pontebella.msusuarios.service;

import java.util.List;

import com.pontebella.msusuarios.dto.request.ActualizarPerfilRequest;
import com.pontebella.msusuarios.dto.request.ActualizarRolRequest;
import com.pontebella.msusuarios.dto.request.LoginRequest;
import com.pontebella.msusuarios.dto.request.RegistroRequest;
import com.pontebella.msusuarios.dto.response.LoginResponse;
import com.pontebella.msusuarios.dto.response.UsuarioResponse;
import com.pontebella.msusuarios.dto.response.UsuarioResumenResponse;
import com.pontebella.msusuarios.enums.RolUsuario;

public interface UsuarioService {
    UsuarioResponse registrar(RegistroRequest request);
    LoginResponse login(LoginRequest request);
    UsuarioResponse buscarPorId(Long id, Long requesterId, String requesterRole);
    UsuarioResponse actualizarPerfil(Long id, ActualizarPerfilRequest request, Long requesterId, String requesterRole);
    UsuarioResponse actualizarRol(Long id, ActualizarRolRequest request);
    void desactivarUsuario(Long id);
    List<UsuarioResumenResponse> listarPorRol(RolUsuario rol);
    UsuarioResponse obtenerParaValidacion(Long id);
}