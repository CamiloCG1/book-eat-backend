package co.ucentral.bookeatbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MesaDTO {
    private Long restauranteId;
    private int numero;
    private int capacidad;
}
