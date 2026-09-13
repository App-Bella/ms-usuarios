package com.pontebella.msusuarios.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pontebella.msusuarios.entity.Usuario;
import com.pontebella.msusuarios.enums.RolUsuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByRol(RolUsuario rol);
}
