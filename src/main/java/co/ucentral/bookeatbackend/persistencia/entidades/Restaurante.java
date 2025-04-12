package co.ucentral.bookeatbackend.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "restaurantes")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;

    private String descripcion;

    private String ciudad;

    private String direccion;

    private String imagenDestacada;

    private String tipoComida;

    private LocalDateTime fechaRegistro;

    private LocalDateTime fechaUltimaActualizacion;
}