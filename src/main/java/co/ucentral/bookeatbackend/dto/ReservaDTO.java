package co.ucentral.bookeatbackend.dto;

import java.time.LocalDateTime;

public record ReservaDTO(
        Long id,
        LocalDateTime fechaHora,
        int numeroPersonas,
        Long usuarioId,
        Long restauranteId
) {}
