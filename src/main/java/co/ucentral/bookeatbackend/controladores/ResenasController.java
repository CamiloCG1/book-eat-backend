package co.ucentral.bookeatbackend.controladores;

import co.ucentral.bookeatbackend.dto.ResenaDTO;
import co.ucentral.bookeatbackend.servicios.ResenasServicio;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resenas")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ResenasController {

    private final ResenasServicio resenasServicio;

    @PostMapping("")
    public ResenaDTO crearResena(@RequestBody ResenaDTO dto) {
        return resenasServicio.crear(dto);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public List<ResenaDTO> listarPorRestaurante(@PathVariable Long restauranteId) {
        return resenasServicio.listarPorRestaurante(restauranteId);
    }
}
