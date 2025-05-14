package co.ucentral.bookeatbackend.persistencia.repositorios;

import co.ucentral.bookeatbackend.persistencia.entidades.Resena;
import co.ucentral.bookeatbackend.persistencia.entidades.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResenasRepositorio extends JpaRepository<Resena, Long> {
    List<Resena> findByRestaurante(Restaurante restaurante);
}
