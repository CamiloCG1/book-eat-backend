package co.ucentral.bookeatbackend.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "equipos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String nombreCorto;
    private LocalDateTime fechaCreacion;
}
