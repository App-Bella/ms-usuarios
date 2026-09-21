package com.pontebella.msusuarios.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontebella.msusuarios.dto.request.ActualizarPerfilRequest;
import com.pontebella.msusuarios.dto.request.ActualizarRolRequest;
import com.pontebella.msusuarios.dto.response.UsuarioResponse;
import com.pontebella.msusuarios.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios/{id}")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<UsuarioResponse> buscarPorId(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long requesterId,
            @RequestHeader(value = "X-User-Role", required = false) String requesterRole) {
        UsuarioResponse response = usuarioService.buscarPorId(id, requesterId, requesterRole);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UsuarioResponse> actualizarPerfil(
            @PathVariable Long id,
            @RequestBody ActualizarPerfilRequest request,
            @RequestHeader("X-User-Id") Long requesterId,
            @RequestHeader(value = "X-User-Role", required = false) String requesterRole) {
        return ResponseEntity.ok(usuarioService.actualizarPerfil(id, request, requesterId, requesterRole));
    }

    @PutMapping("/rol")
    public ResponseEntity<UsuarioResponse> actualizarRol(
            @PathVariable Long id,
            @RequestBody ActualizarRolRequest request) {
        return ResponseEntity.ok(usuarioService.actualizarRol(id, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
