package com.pontebella.msusuarios.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontebella.msusuarios.dto.response.UsuarioResponse;
import com.pontebella.msusuarios.dto.response.UsuarioResumenResponse;
import com.pontebella.msusuarios.enums.RolUsuario;
import com.pontebella.msusuarios.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class PublicoController {

    private final UsuarioService usuarioService;

    @GetMapping("/estilistas")
    public ResponseEntity<List<UsuarioResumenResponse>> listarEstilistas() {
        return ResponseEntity.ok(usuarioService.listarPorRol(RolUsuario.ESTILISTA));
    }

    @GetMapping("/{id}/interno")
    public ResponseEntity<UsuarioResponse> obtenerParaValidacion(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerParaValidacion(id));
    }
}