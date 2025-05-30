package co.ucentral.bookeatbackend.servicios;

import co.ucentral.bookeatbackend.dto.RestauranteDTO;
import co.ucentral.bookeatbackend.persistencia.entidades.Restaurante;
import co.ucentral.bookeatbackend.persistencia.repositorios.RestaurantesRepositorio;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<RestauranteDTO> listarTodos() {
        List<Restaurante> restaurantes = restaurantesRepositorio.findAll();

        return restaurantes.stream().map(r -> new RestauranteDTO(
                r.getId(),
                r.getNombre(),
                r.getDescripcion(),
                r.getCiudad(),
                r.getDireccion(),
                r.getImagenDestacada(),
                r.getTipoComida()
        )).collect(Collectors.toList());
    }
}
