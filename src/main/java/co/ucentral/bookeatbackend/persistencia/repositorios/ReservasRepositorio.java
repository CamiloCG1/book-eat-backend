package co.ucentral.bookeatbackend.persistencia.repositorios;

import co.ucentral.bookeatbackend.persistencia.entidades.Reserva;
import co.ucentral.bookeatbackend.persistencia.entidades.Restaurante;
import co.ucentral.bookeatbackend.persistencia.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservasRepositorio extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuario(Usuario usuario);
    List<Reserva> findByRestaurante(Restaurante restaurante);
}
