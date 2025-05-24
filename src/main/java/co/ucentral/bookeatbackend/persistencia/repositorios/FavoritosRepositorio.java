package co.ucentral.bookeatbackend.persistencia.repositorios;

import co.ucentral.bookeatbackend.persistencia.entidades.Favorito;
import co.ucentral.bookeatbackend.persistencia.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritosRepositorio extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuario(Usuario usuario);
    boolean existsByUsuarioIdAndRestauranteId(Long usuarioId, Long restauranteId);
    void deleteByUsuarioIdAndRestauranteId(Long usuarioId, Long restauranteId);
}