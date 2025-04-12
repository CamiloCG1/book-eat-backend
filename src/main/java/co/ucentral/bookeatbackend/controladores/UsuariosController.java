package co.ucentral.bookeatbackend.controladores;

import co.ucentral.bookeatbackend.dto.UsuarioDTO;
import co.ucentral.bookeatbackend.servicios.UsuariosServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/autenticacion")
    public ResponseEntity<UsuarioDTO> autenticar(@RequestBody Map<String, String> datos) {
        String correo = datos.get("correo");
        String contrasena = datos.get("contrasena");

        UsuarioDTO usuario = usuariosServicio.autenticacion(correo, contrasena);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
