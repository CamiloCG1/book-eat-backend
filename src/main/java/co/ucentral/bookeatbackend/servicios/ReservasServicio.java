package co.ucentral.bookeatbackend.servicios;

import co.ucentral.bookeatbackend.dto.ReservaDTO;
import co.ucentral.bookeatbackend.persistencia.entidades.Reserva;
import co.ucentral.bookeatbackend.persistencia.entidades.Restaurante;
import co.ucentral.bookeatbackend.persistencia.entidades.Usuario;
import co.ucentral.bookeatbackend.persistencia.repositorios.ReservasRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.RestaurantesRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.UsuariosRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservasServicio {

    ReservasRepositorio reservasRepositorio;
    UsuariosRepositorio usuariosRepositorio;
    RestaurantesRepositorio restaurantesRepositorio;

    public ReservaDTO crear(ReservaDTO reservaDto) {
        Optional<Usuario> usuarioOpt = usuariosRepositorio.findById(reservaDto.usuarioId());
        Optional<Restaurante> restauranteOpt = restaurantesRepositorio.findById(reservaDto.restauranteId());

        if (usuarioOpt.isEmpty() || restauranteOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario o Restaurante no encontrado");
        }

        Reserva reserva = Reserva.builder()
                .fechaHora(reservaDto.fechaHora())
                .numeroPersonas(reservaDto.numeroPersonas())
                .fechaCreacion(LocalDateTime.now())
                .usuario(usuarioOpt.get())
                .restaurante(restauranteOpt.get())
                .build();

        reserva = reservasRepositorio.save(reserva);

        return new ReservaDTO(reserva.getId(), reserva.getFechaHora(), reserva.getNumeroPersonas(),
                reserva.getUsuario().getId(), reserva.getRestaurante().getId());
    }

    public List<ReservaDTO> listarPorUsuario(Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuariosRepositorio.findById(usuarioId);
        if (usuarioOpt.isEmpty()) return List.of();

        return reservasRepositorio.findByUsuario(usuarioOpt.get()).stream()
                .map(r -> new ReservaDTO(r.getId(), r.getFechaHora(), r.getNumeroPersonas(),
                        r.getUsuario().getId(), r.getRestaurante().getId()))
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> listarPorRestaurante(Long restauranteId) {
        Optional<Restaurante> restauranteOpt = restaurantesRepositorio.findById(restauranteId);
        if (restauranteOpt.isEmpty()) return List.of();

        return reservasRepositorio.findByRestaurante(restauranteOpt.get()).stream()
                .map(r -> new ReservaDTO(r.getId(), r.getFechaHora(), r.getNumeroPersonas(),
                        r.getUsuario().getId(), r.getRestaurante().getId()))
                .collect(Collectors.toList());
    }
}
