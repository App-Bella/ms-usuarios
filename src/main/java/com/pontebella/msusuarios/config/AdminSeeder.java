package com.pontebella.msusuarios.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.pontebella.msusuarios.entity.Usuario;
import com.pontebella.msusuarios.enums.RolUsuario;
import com.pontebella.msusuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner{
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String emailAdmin = "admin@pontebella.com";

        if (usuarioRepository.existsByEmail(emailAdmin)) {
            return;
        }

        Usuario admin = Usuario.builder()
                .nombre("Administrador PonteBella")
                .email(emailAdmin)
                .password(passwordEncoder.encode("123"))
                .rol(RolUsuario.ADMIN)
                .telefono("3123531233")
                .build();

        usuarioRepository.save(admin);
    }
}
