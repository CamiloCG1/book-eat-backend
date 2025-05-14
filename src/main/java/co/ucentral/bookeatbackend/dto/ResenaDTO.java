package co.ucentral.bookeatbackend.dto;

import java.time.LocalDateTime;

public record ResenaDTO(
        Long id,
        Long usuarioId,
        Long restauranteId,
        int calificacion,
        String comentario,
        LocalDateTime fechaCreacion
) {}
