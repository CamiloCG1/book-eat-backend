package co.ucentral.bookeatbackend.servicios;

import co.ucentral.bookeatbackend.dto.RestauranteDTO;
import co.ucentral.bookeatbackend.dto.UsuarioDTO;
import co.ucentral.bookeatbackend.persistencia.entidades.Restaurante;
import co.ucentral.bookeatbackend.persistencia.entidades.Usuario;
import co.ucentral.bookeatbackend.persistencia.repositorios.RestaurantesRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.UsuariosRepositorio;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class RestaurantesServicio {
    RestaurantesRepositorio restaurantesRepositorio;

    public RestauranteDTO crear(RestauranteDTO restauranteDto){
        Restaurante restaurante = Restaurante.builder()
                .nombre(restauranteDto.nombre())
                .descripcion(restauranteDto.descripcion())
                .ciudad(restauranteDto.ciudad())
                .direccion(restauranteDto.direccion())
                .imagenDestacada(restauranteDto.imagenDestacada())
                .tipoComida(restauranteDto.tipoComida())
                .fechaRegistro(LocalDateTime.now())
                .fechaUltimaActualizacion(LocalDateTime.now())
                .build();

        if (restaurantesRepositorio.save(restaurante).getId() > 0)
            return restauranteDto;
        else return null;
    }
}
