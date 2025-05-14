package co.ucentral.bookeatbackend.servicios;

import co.ucentral.bookeatbackend.dto.ResenaDTO;
import co.ucentral.bookeatbackend.persistencia.entidades.Resena;
import co.ucentral.bookeatbackend.persistencia.entidades.Restaurante;
import co.ucentral.bookeatbackend.persistencia.entidades.Usuario;
import co.ucentral.bookeatbackend.persistencia.repositorios.ResenasRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.RestaurantesRepositorio;
import co.ucentral.bookeatbackend.persistencia.repositorios.UsuariosRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResenasServicio {

    private final ResenasRepositorio resenasRepositorio;
    private final UsuariosRepositorio usuariosRepositorio;
    private final RestaurantesRepositorio restaurantesRepositorio;

    public ResenaDTO crear(ResenaDTO dto) {
        Usuario usuario = usuariosRepositorio.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Restaurante restaurante = restaurantesRepositorio.findById(dto.restauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));

        Resena resena = Resena.builder()
                .usuario(usuario)
                .restaurante(restaurante)
                .calificacion(dto.calificacion())
                .comentario(dto.comentario())
                .fechaCreacion(LocalDateTime.now())
                .build();

        Resena guardada = resenasRepositorio.save(resena);

        return new ResenaDTO(
                guardada.getId(),
                usuario.getId(),
                restaurante.getId(),
                guardada.getCalificacion(),
                guardada.getComentario(),
                guardada.getFechaCreacion()
        );
    }

    public List<ResenaDTO> listarPorRestaurante(Long restauranteId) {
        Optional<Restaurante> restaurante = restaurantesRepositorio.findById(restauranteId);
        if (restaurante.isEmpty()) return List.of();

        return resenasRepositorio.findByRestaurante(restaurante.get()).stream()
                .map(r -> new ResenaDTO(
                        r.getId(),
                        r.getUsuario().getId(),
                        r.getRestaurante().getId(),
                        r.getCalificacion(),
                        r.getComentario(),
                        r.getFechaCreacion()
                )).collect(Collectors.toList());
    }
}
