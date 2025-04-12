package co.ucentral.bookeatbackend.servicios;

import co.ucentral.bookeatbackend.dto.UsuarioDTO;
import co.ucentral.bookeatbackend.persistencia.entidades.Usuario;
import co.ucentral.bookeatbackend.persistencia.repositorios.UsuariosRepositorio;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class UsuariosServicio {
    UsuariosRepositorio usuarioRepositorio;

    public UsuarioDTO crear(UsuarioDTO usuarioDto){
        Usuario usuario = Usuario.builder()
                .usuario(usuarioDto.usuario())
                .correo(usuarioDto.correo())
                .contrasena(usuarioDto.contrasena())
                .fechaRegistro(LocalDateTime.now())
                .build();

        if (usuarioRepositorio.save(usuario).getId() > 0)
            return usuarioDto;
        else return null;
    }

    public UsuarioDTO autenticacion(String correo, String contrasena) {
        Optional<Usuario> usuario = usuarioRepositorio.findByCorreo(correo);

        if (usuario.isPresent() && usuario.get().getContrasena().equals(contrasena)) {
            Usuario u = usuario.get();
            return new UsuarioDTO(u.getUsuario(), u.getCorreo(), null);
        }

        return null;
    }
}
