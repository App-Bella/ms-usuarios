package com.pontebella.msusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del microservicio Ms-Usuarios.
 * Responsabilidad (ver SRS 3.1):
 *  - RF-US-01 Registro e inicio de sesión (validación de credenciales)
 *  - RF-US-02 Gestión de roles (Cliente, Alumna, Esteticista/Docente, Administrador)
 *  - RF-US-03 Gestión de perfiles
 *
 * Nota: la seguridad (JWT, filtros, rutas protegidas) se maneja en el API Gateway,
 * no en este microservicio.
 */
@SpringBootApplication
public class MsUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUsuariosApplication.class, args);
    }

}
