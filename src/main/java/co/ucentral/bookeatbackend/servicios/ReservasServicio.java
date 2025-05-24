package co.ucentral.bookeatbackend.servicios;

import co.ucentral.bookeatbackend.dto.ReservaDTO;
import co.ucentral.bookeatbackend.persistencia.entidades.Mesa;
import co.ucentral.bookeatbackend.persistencia.entidades.Reserva;
import co.ucentral.bookeatbackend.persistencia.entidades.Restaurante;
import co.ucentral.bookeatbackend.persistencia.entidades.Usuario;
import co.ucentral.bookeatbackend.persistencia.repositorios.ReservasRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.RestaurantesRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.UsuariosRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.MesasRepositorio;
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
    MesasRepositorio mesasRepositorio;

    public ReservaDTO crear(ReservaDTO reservaDto) {
        Optional<Usuario> usuarioOpt = usuariosRepositorio.findById(reservaDto.usuarioId());
        Optional<Restaurante> restauranteOpt = restaurantesRepositorio.findById(reservaDto.restauranteId());
        Optional<Mesa> mesaOpt = mesasRepositorio.findById(reservaDto.mesaId());

        if (usuarioOpt.isEmpty() || restauranteOpt.isEmpty() || mesaOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario, restaurante o mesa no encontrado");
        }

        Mesa mesa = mesaOpt.get();
        if (!mesa.getRestaurante().getId().equals(reservaDto.restauranteId())) {
            throw new IllegalArgumentException("La mesa no pertenece al restaurante");
        }

        boolean estaOcupada = reservasRepositorio.findAll().stream()
                .anyMatch(r ->
                        r.getMesa().getId().equals(mesa.getId()) &&
                                r.getFechaHora().equals(reservaDto.fechaHora())
                );

        if (estaOcupada) {
            throw new IllegalStateException("La mesa ya está reservada para esa fecha y hora");
        }

        Reserva reserva = Reserva.builder()
                .fechaHora(reservaDto.fechaHora())
                .numeroPersonas(reservaDto.numeroPersonas())
                .fechaCreacion(LocalDateTime.now())
                .usuario(usuarioOpt.get())
                .restaurante(restauranteOpt.get())
                .mesa(mesa)
                .build();

        reserva = reservasRepositorio.save(reserva);

        return new ReservaDTO(reserva.getId(), reserva.getFechaHora(), reserva.getNumeroPersonas(),
                reserva.getUsuario().getId(), reserva.getRestaurante().getId(), reserva.getMesa().getId());
    }

    public List<ReservaDTO> listarPorUsuario(Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuariosRepositorio.findById(usuarioId);
        if (usuarioOpt.isEmpty()) return List.of();

        return reservasRepositorio.findByUsuario(usuarioOpt.get()).stream()
                .map(r -> new ReservaDTO(
                        r.getId(),
                        r.getFechaHora(),
                        r.getNumeroPersonas(),
                        r.getUsuario().getId(),
                        r.getRestaurante().getId(),
                        r.getMesa().getId()
                ))
                .collect(Collectors.toList());
    }


    public List<ReservaDTO> listarPorRestaurante(Long restauranteId) {
        Optional<Restaurante> restauranteOpt = restaurantesRepositorio.findById(restauranteId);
        if (restauranteOpt.isEmpty()) return List.of();

        return reservasRepositorio.findByRestaurante(restauranteOpt.get()).stream()
                .map(r -> new ReservaDTO(
                        r.getId(),
                        r.getFechaHora(),
                        r.getNumeroPersonas(),
                        r.getUsuario().getId(),
                        r.getRestaurante().getId(),
                        r.getMesa().getId()
                ))
                .collect(Collectors.toList());
    }

    public ReservaDTO actualizar(Long reservaId, ReservaDTO dto) {
        Reserva reserva = reservasRepositorio.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        Mesa mesa = mesasRepositorio.findById(dto.mesaId())
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));

        if (!mesa.getRestaurante().getId().equals(dto.restauranteId())) {
            throw new IllegalArgumentException("La mesa no pertenece al restaurante");
        }

        boolean estaOcupada = reservasRepositorio.findAll().stream()
                .anyMatch(r -> !r.getId().equals(reservaId) &&
                        r.getMesa().getId().equals(mesa.getId()) &&
                        r.getFechaHora().equals(dto.fechaHora()));

        if (estaOcupada) {
            throw new IllegalStateException("La mesa ya está reservada en esa fecha y hora");
        }

        reserva.setFechaHora(dto.fechaHora());
        reserva.setNumeroPersonas(dto.numeroPersonas());
        reserva.setMesa(mesa);

        Reserva actualizada = reservasRepositorio.save(reserva);
        return new ReservaDTO(actualizada.getId(), actualizada.getFechaHora(), actualizada.getNumeroPersonas(),
                actualizada.getUsuario().getId(), actualizada.getRestaurante().getId(), actualizada.getMesa().getId());
    }

    public void cancelar(Long reservaId) {
        if (!reservasRepositorio.existsById(reservaId)) {
            throw new IllegalArgumentException("Reserva no encontrada");
        }
        reservasRepositorio.deleteById(reservaId);
    }
}
