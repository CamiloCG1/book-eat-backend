package co.ucentral.bookeatbackend.persistencia.repositorios;

import co.ucentral.bookeatbackend.persistencia.entidades.Mesa;
import co.ucentral.bookeatbackend.persistencia.entidades.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesasRepositorio extends JpaRepository<Mesa, Long> {
    List<Mesa> findByRestaurante(Restaurante restaurante);
    List<Mesa> findByRestauranteId(Long restauranteId);
}
