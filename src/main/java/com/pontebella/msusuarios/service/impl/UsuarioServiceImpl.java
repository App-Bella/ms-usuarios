package com.pontebella.msusuarios.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pontebella.msusuarios.dto.request.ActualizarPerfilRequest;
import com.pontebella.msusuarios.dto.request.ActualizarRolRequest;
import com.pontebella.msusuarios.dto.request.LoginRequest;
import com.pontebella.msusuarios.dto.request.RegistroRequest;
import com.pontebella.msusuarios.dto.response.LoginResponse;
import com.pontebella.msusuarios.dto.response.UsuarioResponse;
import com.pontebella.msusuarios.dto.response.UsuarioResumenResponse;
import com.pontebella.msusuarios.entity.Usuario;
import com.pontebella.msusuarios.enums.RolUsuario;
import com.pontebella.msusuarios.exception.AccesoDenegadoException;
import com.pontebella.msusuarios.exception.CredencialesInvalidasException;
import com.pontebella.msusuarios.exception.EmailYaRegistradoException;
import com.pontebella.msusuarios.exception.RecursoNoEncontradoException;
import com.pontebella.msusuarios.repository.UsuarioRepository;
import com.pontebella.msusuarios.security.jwt.JwtService;
import com.pontebella.msusuarios.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UsuarioResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailYaRegistradoException("Ya existe un usuario registrado con este email");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(RolUsuario.CLIENTE)
                .telefono(request.getTelefono())
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return aUsuarioResponse(usuarioGuardado);
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

    private void validarOwnershipOAdmin(Long id, Long requesterId, String requesterRole) {
        boolean esAdmin = requesterRole != null && requesterRole.trim().equalsIgnoreCase("ADMIN");
        boolean esPropioPerfil = requesterId != null && requesterId.equals(id);

        if (!esAdmin && !esPropioPerfil) {
            throw new AccesoDenegadoException("No tienes permiso para acceder al perfil de otro usuario");
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CredencialesInvalidasException("Email o contraseña incorrectos"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new CredencialesInvalidasException("Email o contraseña incorrectos");
        }

        String token = jwtService.generarToken(usuario);
        return new LoginResponse(token, aUsuarioResponse(usuario));
    }

    @Override
    public UsuarioResponse buscarPorId(Long id, Long requesterId, String requesterRole) {
        validarOwnershipOAdmin(id, requesterId, requesterRole);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
        return aUsuarioResponse(usuario);
    }

    @Override
    public UsuarioResponse actualizarPerfil(Long id, ActualizarPerfilRequest request, Long requesterId, String requesterRole) {
        boolean esAdmin = requesterRole != null && requesterRole.trim().equalsIgnoreCase("ADMIN");
        boolean esPropioPerfil = requesterId != null && requesterId.equals(id);

        if (!esAdmin && !esPropioPerfil) {
            throw new AccesoDenegadoException("No tienes permiso para editar el perfil de otro usuario");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));

        usuario.setNombre(request.getNombre());
        usuario.setTelefono(request.getTelefono());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return aUsuarioResponse(usuarioActualizado);
    }

    @Override
    public UsuarioResponse actualizarRol(Long id, ActualizarRolRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));

        usuario.setRol(request.getNuevoRol());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return aUsuarioResponse(usuarioActualizado);
    }

    @Override
    public void desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public List<UsuarioResumenResponse> listarPorRol(RolUsuario rol) {
        return usuarioRepository.findByRol(rol).stream()
                .filter(Usuario::isActivo)
                .map(u -> new UsuarioResumenResponse(u.getId(), u.getNombre()))
                .toList();
    }

}