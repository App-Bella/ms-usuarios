package com.pontebella.msusuarios.service.impl;

import org.springframework.stereotype.Service;

import com.pontebella.msusuarios.dto.request.LoginRequest;
import com.pontebella.msusuarios.dto.request.RegistroRequest;
import com.pontebella.msusuarios.dto.response.UsuarioResponse;
import com.pontebella.msusuarios.entity.Usuario;
import com.pontebella.msusuarios.exception.CredencialesInvalidasException;
import com.pontebella.msusuarios.exception.EmailYaRegistradoException;
import com.pontebella.msusuarios.exception.RecursoNoEncontradoException;
import com.pontebella.msusuarios.repository.UsuarioRepository;
import com.pontebella.msusuarios.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{
    
    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailYaRegistradoException("Ya existe un usuario registrado con este email");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                // TODO: reemplazar por BCryptPasswordEncoder cuando se agregue Spring Security
                .password(request.getPassword())
                .rol(request.getRol())
                .telefono(request.getTelefono())
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return aUsuarioResponse(usuarioGuardado);
    }

    @Override
    public UsuarioResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CredencialesInvalidasException("Email o contraseña incorrectos"));

        // TODO: reemplazar por BCryptPasswordEncoder.matches() cuando se agregue Spring Security
        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new CredencialesInvalidasException("Email o contraseña incorrectos");
        }

        return aUsuarioResponse(usuario);
    }

    @Override
    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
        return aUsuarioResponse(usuario);
    }

    private UsuarioResponse aUsuarioResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getTelefono(),
                usuario.isActivo()
        );
    }

}
