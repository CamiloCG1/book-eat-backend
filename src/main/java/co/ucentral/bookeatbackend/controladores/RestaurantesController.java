package co.ucentral.bookeatbackend.controladores;

import co.ucentral.bookeatbackend.dto.RestauranteDTO;
import co.ucentral.bookeatbackend.dto.UsuarioDTO;
import co.ucentral.bookeatbackend.servicios.RestaurantesServicio;
import co.ucentral.bookeatbackend.servicios.UsuariosServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
}
