package co.ucentral.bookeatbackend.controladores;

import co.ucentral.bookeatbackend.dto.RestauranteDTO;
import co.ucentral.bookeatbackend.servicios.RestaurantesServicio;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes")
@CrossOrigin(origins = "*")
public class RestaurantesController {
    RestaurantesServicio restaurantesServicio;
    @PostMapping("")
    public RestauranteDTO crear(@RequestBody RestauranteDTO restaurante){
        return restaurantesServicio.crear(restaurante);
    }

    @GetMapping("")
    public List<RestauranteDTO> listarRestaurantes() {
        return restaurantesServicio.listarTodos();
    }
}
