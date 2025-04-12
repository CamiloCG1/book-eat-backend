package co.ucentral.bookeatbackend.persistencia.repositorios;

import co.ucentral.bookeatbackend.persistencia.entidades.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantesRepositorio extends JpaRepository<Restaurante, Long> {
}
