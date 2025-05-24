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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public List<String> obtenerHorariosDisponibles(Long restauranteId, String fechaStr) {
        Optional<Restaurante> restauranteOpt = restaurantesRepositorio.findById(restauranteId);
        if (restauranteOpt.isEmpty()) {
            throw new IllegalArgumentException("Restaurante no encontrado");
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr); // Formato esperado: "2025-05-23"
        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha con formato inválido");
        }

        // Define el rango horario (ej. de 12:00 a 22:00 cada 1 hora)
        List<LocalTime> posiblesHoras = IntStream.rangeClosed(12, 22)
                .mapToObj(hora -> LocalTime.of(hora, 0))
                .toList();

        LocalDateTime inicioDia = fecha.atStartOfDay();
        LocalDateTime finDia = fecha.atTime(LocalTime.MAX);
        List<Reserva> reservasDelDia = reservasRepositorio.findByRestauranteAndFechaHoraBetween(
                restauranteOpt.get(), inicioDia, finDia
        );

        if (reservasDelDia.isEmpty()) {
            return posiblesHoras.stream().map(LocalTime::toString).toList();
        }

        List<Mesa> mesas = mesasRepositorio.findByRestauranteId(restauranteId);
        List<Reserva> reservas = reservasRepositorio.findByRestaurante(restauranteOpt.get());

        List<String> horariosDisponibles = new ArrayList<>();

        for (LocalTime hora : posiblesHoras) {
            LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
            boolean algunaMesaDisponible = mesas.stream().anyMatch(mesa ->
                    reservas.stream().noneMatch(reserva ->
                            reserva.getMesa().getId().equals(mesa.getId()) &&
                                    reserva.getFechaHora().equals(fechaHora)
                    )
            );
            if (algunaMesaDisponible) {
                horariosDisponibles.add(hora.toString());
            }
        }

        return horariosDisponibles;
    }

}
