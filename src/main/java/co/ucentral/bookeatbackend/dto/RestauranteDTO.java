package co.ucentral.bookeatbackend.dto;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.List;

public record RestauranteDTO(
        @NotBlank String nombre,
        String descripcion,
        String ciudad,
        String direccion,
        String imagenDestacada,
        String tipoComida
) {}
