package co.ucentral.bookeatbackend.controladores;

import co.ucentral.bookeatbackend.dto.FavoritoDTO;
import co.ucentral.bookeatbackend.servicios.FavoritosServicio;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/favoritos")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class FavoritosController {

    private final FavoritosServicio favoritosServicio;

    @PostMapping("")
    public FavoritoDTO agregarFavorito(@RequestBody FavoritoDTO dto) {
        return favoritosServicio.guardarFavorito(dto.usuarioId(), dto.restauranteId());
    }

    @DeleteMapping("")
    public void eliminarFavorito(@RequestBody FavoritoDTO dto) {
        favoritosServicio.eliminarFavorito(dto.usuarioId(), dto.restauranteId());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<FavoritoDTO> listarFavoritos(@PathVariable Long usuarioId) {
        return favoritosServicio.listarFavoritos(usuarioId);
    }
}
