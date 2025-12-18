package com.musicfly.backend.estadisticas.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "FavoritosDTO",
        description = "DTO utilizado para registrar una acción de favorito en el sistema de estadísticas."
)
public class FavoritosDTO implements KafkaDTO {

    @JsonProperty("id_favorito")
    @Schema(
            description = "Identificador único del registro de favorito (generalmente compuesto por usuario + contenido).",
            example = "12-58"
    )
    private String idFavorito;

    @JsonProperty("id_perfil")
    @Schema(
            description = "ID del usuario que marcó el contenido como favorito.",
            example = "12"
    )
    private Long idPerfil;

    @JsonProperty("id_contenido")
    @Schema(
            description = "ID del contenido que se marcó como favorito.",
            example = "58"
    )
    private Long idContenido;

    @JsonProperty("fecha_agregado")
    @Schema(
            description = "Fecha en que se registró la acción de favorito.",
            example = "2025-02-15"
    )
    private String fechaAgregado;

    @JsonProperty("accion")
    @Schema(
            description = "Acción realizada sobre el contenido. Normalmente 'CREATED' para registrar un favorito.",
            example = "CREATED"
    )
    private String accion;

}
