package com.musicfly.backend.estadisticas.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "VisualizacionDTO",
        description = "DTO utilizado para registrar una visualización o reproducción de contenido en el sistema de estadísticas."
)
public class VisualizacionDTO implements KafkaDTO {

    @JsonProperty("id_visualizacion")
    @Schema(
            description = "Identificador único de la visualización. Generalmente compuesto por usuario + contenido.",
            example = "12-90"
    )
    private String idVisualizacion;

    @JsonProperty("id_perfil")
    @Schema(
            description = "ID del usuario que realizó la visualización.",
            example = "12"
    )
    private Long idPerfil;

    @JsonProperty("id_contenido")
    @Schema(
            description = "ID del contenido visualizado.",
            example = "90"
    )
    private Long idContenido;

    @JsonProperty("fecha_visualizacion")
    @Schema(
            description = "Fecha en la que se registró la visualización.",
            example = "2025-02-15"
    )
    private String fechaVisualizacion;

    @JsonProperty("progreso")
    @Schema(
            description = "Porcentaje o tiempo de progreso alcanzado por el usuario durante la visualización.",
            example = "0.75"
    )
    private Float progreso;

    @JsonProperty("accion")
    @Schema(
            description = "Acción realizada. Generalmente 'CREATED' para registrar una nueva visualización.",
            example = "CREATED"
    )
    private String accion;
}
