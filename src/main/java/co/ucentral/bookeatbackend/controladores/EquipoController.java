package co.ucentral.bookeatbackend.controladores;

import co.ucentral.bookeatbackend.persistencia.entidades.Equipo;
import co.ucentral.bookeatbackend.servicios.EquipoServicio;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/equipos")
@CrossOrigin(origins = "*")
public class EquipoController {
    EquipoServicio equipoServicio;

    @GetMapping("")
    public List<Equipo> obtenerEquipos() {
        return equipoServicio.obtenerEquipos();
    }
}
