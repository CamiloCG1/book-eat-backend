package co.ucentral.bookeatbackend.persistencia.repositorios;

import co.ucentral.bookeatbackend.persistencia.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepositorio extends JpaRepository<Usuario, Long> {
}
