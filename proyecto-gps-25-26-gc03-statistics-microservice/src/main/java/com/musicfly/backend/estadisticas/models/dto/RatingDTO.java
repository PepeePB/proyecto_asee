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
        name = "RatingDTO",
        description = "DTO utilizado para registrar un rating o valoración de un contenido."
)
public class RatingDTO implements KafkaDTO {

    @JsonProperty("id_rating")
    @Schema(
            description = "Identificador único para el registro del rating. Usualmente compuesto por usuario + contenido.",
            example = "12-42"
    )
    private String idRating;

    @JsonProperty("id_contenido")
    @Schema(
            description = "ID del contenido al que se le asigna la valoración.",
            example = "42"
    )
    private Long idContenido;

    @JsonProperty("id_perfil")
    @Schema(
            description = "ID del usuario que envía la valoración.",
            example = "12"
    )
    private Long idPerfil;

    @JsonProperty("thumb_up")
    @Schema(
            description = "Indica si el usuario dio un 'me gusta'. true = pulgar arriba, false = pulgar abajo.",
            example = "true"
    )
    private Boolean thumbUp;

    @JsonProperty("rating")
    @Schema(
            description = "Valor numérico de la valoración (si aplica). Puede representar estrellas o puntuación.",
            example = "5"
    )
    private Integer rating;

    @JsonProperty("accion")
    @Schema(
            description = "Acción realizada sobre el contenido. Normalmente 'CREATED' para registrar una nueva valoración.",
            example = "CREATED"
    )
    private String accion;

}
