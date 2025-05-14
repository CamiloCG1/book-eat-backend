package co.ucentral.bookeatbackend.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mesas")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int numero;

    private int capacidad;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;
}
