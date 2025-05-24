package co.ucentral.bookeatbackend.controladores;

import co.ucentral.bookeatbackend.dto.ReservaDTO;
import co.ucentral.bookeatbackend.servicios.ReservasServicio;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReservasController {

    private final ReservasServicio reservasServicio;

    @PostMapping("")
    public ReservaDTO crear(@RequestBody ReservaDTO reservaDto) {
        return reservasServicio.crear(reservaDto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<ReservaDTO> listarPorUsuario(@PathVariable Long usuarioId) {
        return reservasServicio.listarPorUsuario(usuarioId);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public List<ReservaDTO> listarPorRestaurante(@PathVariable Long restauranteId) {
        return reservasServicio.listarPorRestaurante(restauranteId);
    }

    @PutMapping("/{reservaId}")
    public ReservaDTO actualizar(@PathVariable Long reservaId, @RequestBody ReservaDTO dto) {
        return reservasServicio.actualizar(reservaId, dto);
    }

    @DeleteMapping("/{reservaId}")
    public void cancelar(@PathVariable Long reservaId) {
        reservasServicio.cancelar(reservaId);
    }
}
