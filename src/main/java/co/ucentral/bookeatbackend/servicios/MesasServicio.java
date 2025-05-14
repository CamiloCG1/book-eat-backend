package co.ucentral.bookeatbackend.servicios;

import co.ucentral.bookeatbackend.persistencia.entidades.Mesa;
import co.ucentral.bookeatbackend.persistencia.entidades.Reserva;
import co.ucentral.bookeatbackend.persistencia.entidades.Restaurante;
import co.ucentral.bookeatbackend.persistencia.repositorios.MesasRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.ReservasRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.RestaurantesRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MesasServicio {

    private final MesasRepositorio mesasRepositorio;
    private final RestaurantesRepositorio restaurantesRepositorio;
    private final ReservasRepositorio reservasRepositorio;

    /**
     * Crea una nueva mesa para un restaurante específico
     */
    public Mesa crearMesa(Long restauranteId, int numero, int capacidad) {
        Restaurante restaurante = restaurantesRepositorio.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));

        Mesa nuevaMesa = Mesa.builder()
                .restaurante(restaurante)
                .numero(numero)
                .capacidad(capacidad)
                .build();

        return mesasRepositorio.save(nuevaMesa);
    }

    /**
     * Lista todas las mesas de un restaurante
     */
    public List<Mesa> listarMesasPorRestaurante(Long restauranteId) {
        Restaurante restaurante = restaurantesRepositorio.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));

        return mesasRepositorio.findByRestaurante(restaurante);
    }

    /**
     * Obtiene las mesas disponibles en una fecha y hora específicas
     */
    public List<Mesa> obtenerMesasDisponibles(Long restauranteId, LocalDateTime fechaHora) {
        Restaurante restaurante = restaurantesRepositorio.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));

        List<Mesa> todasLasMesas = mesasRepositorio.findByRestaurante(restaurante);

        List<Reserva> reservasEnEseMomento = reservasRepositorio.findByRestauranteAndFechaHora(restaurante, fechaHora);

        List<Long> mesasReservadasIds = reservasEnEseMomento.stream()
                .map(reserva -> reserva.getMesa().getId())
                .collect(Collectors.toList());

        return todasLasMesas.stream()
                .filter(mesa -> !mesasReservadasIds.contains(mesa.getId()))
                .collect(Collectors.toList());
    }
}
