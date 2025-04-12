package co.ucentral.bookeatbackend.controladores;

import co.ucentral.bookeatbackend.dto.UsuarioDTO;
import co.ucentral.bookeatbackend.servicios.UsuariosServicio;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuariosController {
    UsuariosServicio usuariosServicio;
    @PostMapping("")
    public UsuarioDTO crear(@RequestBody UsuarioDTO usuario){
        return usuariosServicio.crear(usuario);
    }
}
