package com.pontebella.msusuarios.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontebella.msusuarios.dto.request.ActualizarPerfilRequest;
import com.pontebella.msusuarios.dto.request.ActualizarRolRequest;
import com.pontebella.msusuarios.dto.response.UsuarioResponse;
import com.pontebella.msusuarios.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        UsuarioResponse response = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }


    /**
     * Edición de datos propios (nombre, teléfono). NO permite cambiar el rol.
     * El Gateway debe validar que el {id} corresponda al usuario autenticado
     * (o que sea ADMIN) antes de dejar pasar esta petición.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarPerfil(
            @PathVariable Long id,
            @RequestBody ActualizarPerfilRequest request) {
        return ResponseEntity.ok(usuarioService.actualizarPerfil(id, request));
    }

    /**
     * Cambio de rol (RF-US-02). Endpoint exclusivo de ADMIN, validado en el Gateway.
     */
    @PutMapping("/{id}/rol")
    public ResponseEntity<UsuarioResponse> actualizarRol(
            @PathVariable Long id,
            @RequestBody ActualizarRolRequest request) {
        return ResponseEntity.ok(usuarioService.actualizarRol(id, request));
    }

    /**
     * Borrado lógico. Endpoint exclusivo de ADMIN (validado en el Gateway).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
