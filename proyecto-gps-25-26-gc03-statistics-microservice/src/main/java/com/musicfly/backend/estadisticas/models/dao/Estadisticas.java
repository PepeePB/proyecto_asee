package com.musicfly.backend.estadisticas.models.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(
        name = "Estadisticas",
        description = "Representa las estadísticas agregadas de una canción, incluyendo visualizaciones, favoritos y valoraciones."
)
public class Estadisticas {

    @Id
    @Schema(
            description = "ID único de la canción a la que pertenecen estas estadísticas",
            example = "42"
    )
    private Long idCancion;

    @Column
    @Schema(
            description = "Número total de visualizaciones acumuladas",
            example = "1520"
    )
    private Long visualizaciones;

    @Column
    @Schema(
            description = "Número total de veces que la canción fue marcada como favorita",
            example = "120"
    )
    private Long favoritos;

    @Column
    @Schema(
            description = "Número total de valoraciones registradas",
            example = "89"
    )
    private Long valoraciones;

    @Column
    @Schema(
            description = "Valoración media calculada a partir de las valoraciones registradas",
            example = "4.5"
    )
    private Double valoracionMedia;

}
