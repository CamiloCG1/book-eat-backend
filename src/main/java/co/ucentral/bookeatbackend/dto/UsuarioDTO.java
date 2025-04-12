package co.ucentral.bookeatbackend.dto;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public record UsuarioDTO(@NotBlank String usuario, String correo, String contrasena){
}
