package co.ucentral.bookeatbackend.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favoritos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;
}
