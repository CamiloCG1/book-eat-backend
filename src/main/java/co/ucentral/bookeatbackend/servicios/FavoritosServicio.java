package co.ucentral.bookeatbackend.servicios;

import co.ucentral.bookeatbackend.dto.FavoritoDTO;
import co.ucentral.bookeatbackend.persistencia.entidades.Favorito;
import co.ucentral.bookeatbackend.persistencia.entidades.Restaurante;
import co.ucentral.bookeatbackend.persistencia.entidades.Usuario;
import co.ucentral.bookeatbackend.persistencia.repositorios.FavoritosRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.RestaurantesRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.UsuariosRepositorio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavoritosServicio {

    private final FavoritosRepositorio favoritosRepositorio;
    private final UsuariosRepositorio usuariosRepositorio;
    private final RestaurantesRepositorio restaurantesRepositorio;

    public FavoritoDTO guardarFavorito(Long usuarioId, Long restauranteId) {
        if (favoritosRepositorio.existsByUsuarioIdAndRestauranteId(usuarioId, restauranteId)) {
            throw new IllegalArgumentException("El restaurante ya está en favoritos");
        }

        Usuario usuario = usuariosRepositorio.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Restaurante restaurante = restaurantesRepositorio.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante no encontrado"));

        Favorito favorito = Favorito.builder()
                .usuario(usuario)
                .restaurante(restaurante)
                .build();

        favorito = favoritosRepositorio.save(favorito);

        return new FavoritoDTO(favorito.getId(), usuario.getId(), restaurante.getId());
    }

    @Transactional
    public void eliminarFavorito(Long usuarioId, Long restauranteId) {
        favoritosRepositorio.deleteByUsuarioIdAndRestauranteId(usuarioId, restauranteId);
    }

    public List<FavoritoDTO> listarFavoritos(Long usuarioId) {
        Usuario usuario = usuariosRepositorio.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return favoritosRepositorio.findByUsuario(usuario).stream()
                .map(f -> new FavoritoDTO(f.getId(), f.getUsuario().getId(), f.getRestaurante().getId()))
                .collect(Collectors.toList());
    }
}
