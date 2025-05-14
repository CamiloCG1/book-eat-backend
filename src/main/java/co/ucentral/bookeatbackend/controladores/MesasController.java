package co.ucentral.bookeatbackend.controladores;

import co.ucentral.bookeatbackend.dto.MesaDTO;
import co.ucentral.bookeatbackend.persistencia.entidades.Mesa;
import co.ucentral.bookeatbackend.servicios.MesasServicio;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/mesas")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class MesasController {

    private final MesasServicio mesasServicio;

    /**
     * Listar todas las mesas de un restaurante
     */
    @GetMapping("/restaurante/{restauranteId}")
    public List<Mesa> listarMesasPorRestaurante(@PathVariable Long restauranteId) {
        return mesasServicio.listarMesasPorRestaurante(restauranteId);
    }

    /**
     * Consultar mesas disponibles para una fecha y hora
     */
    @GetMapping("/disponibles")
    public List<Mesa> obtenerMesasDisponibles(
            @RequestParam Long restauranteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHora
    ) {
        return mesasServicio.obtenerMesasDisponibles(restauranteId, fechaHora);
    }

    /**
     * Crear una nueva mesa para un restaurante
     */
    @PostMapping("")
    public Mesa crearMesa(@RequestBody MesaDTO mesaDTO) {
        return mesasServicio.crearMesa(mesaDTO.getRestauranteId(), mesaDTO.getNumero(), mesaDTO.getCapacidad());
    }
}
