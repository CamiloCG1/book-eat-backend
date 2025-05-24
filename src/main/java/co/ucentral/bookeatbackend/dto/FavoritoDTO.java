package co.ucentral.bookeatbackend.dto;

public record FavoritoDTO(
        Long id,
        Long usuarioId,
        Long restauranteId
) {}
