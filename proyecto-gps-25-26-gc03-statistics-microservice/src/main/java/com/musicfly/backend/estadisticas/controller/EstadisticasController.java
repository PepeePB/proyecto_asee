package com.musicfly.backend.estadisticas.controller;

import com.musicfly.backend.estadisticas.models.dto.FavoritosDTO;
import com.musicfly.backend.estadisticas.models.dto.RatingDTO;
import com.musicfly.backend.estadisticas.models.dto.VisualizacionDTO;
import com.musicfly.backend.estadisticas.listener.EstadisticasListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {

    // -----------------------------------------------
    // 1. Número de visitas de una canción
    // -----------------------------------------------
    @GetMapping("/visualizaciones/cancion/{id_contenido}")
    @Operation(
            summary = "Obtener número de visualizaciones de una canción",
            description = "Devuelve la cantidad de veces que una canción ha sido visualizada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    public ResponseEntity<Long> numeroVisitasCancion(
            @Parameter(description = "ID del contenido para consultar visualizaciones", example = "15")
            @PathVariable("id_contenido") Long idContenido
    ) {
        long count = EstadisticasListener.DB_VISUALIZACIONES.values().stream()
                .filter(v -> Objects.equals(v.getIdContenido(), idContenido))
                .count();
        return ResponseEntity.ok(count);
    }

    // -----------------------------------------------
    // 2. Valoración media de una canción
    // -----------------------------------------------
    @GetMapping("/ratings/cancion/{id_contenido}/media")
    @Operation(
            summary = "Obtener valoración media de una canción",
            description = "Calcula la valoración media basándose en los ratings registrados (thumbUp)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Media calculada correctamente")
    })
    public ResponseEntity<Double> valoracionMediaCancion(
            @Parameter(description = "ID del contenido para consultar la valoración media", example = "15")
            @PathVariable("id_contenido") Long idContenido
    ) {
        List<RatingDTO> ratings = EstadisticasListener.DB_RATINGS.values().stream()
                .filter(r -> Objects.equals(r.getIdContenido(), idContenido))
                .collect(Collectors.toList());

        if (ratings.isEmpty()) return ResponseEntity.ok(0.0);

        DoubleSummaryStatistics stats = ratings.stream()
                .mapToDouble(r -> r.getThumbUp() != null && r.getThumbUp() ? 1 : 0)
                .summaryStatistics();

        double media = stats.getAverage();
        return ResponseEntity.ok(media);
    }

    // -----------------------------------------------
    // 3. Número total de visualizaciones (global)
    // -----------------------------------------------
    @GetMapping("/visualizaciones/total")
    @Operation(
            summary = "Obtener el total de visualizaciones",
            description = "Devuelve el número total de visualizaciones acumuladas en todas las canciones."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Total obtenido correctamente")
    })
    public ResponseEntity<Long> totalVisualizaciones() {
        long total = EstadisticasListener.DB_VISUALIZACIONES.size();
        return ResponseEntity.ok(total);
    }

    // -----------------------------------------------
    // 4. Número total de favoritos de una canción
    // -----------------------------------------------
    @GetMapping("/favoritos/cancion/{id_contenido}/total")
    @Operation(
            summary = "Obtener número total de favoritos de una canción",
            description = "Devuelve cuántas veces una canción ha sido guardada como favorita."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Total obtenido correctamente")
    })
    public ResponseEntity<Long> totalFavoritosCancion(
            @Parameter(description = "ID del contenido para consultar favoritos", example = "15")
            @PathVariable("id_contenido") Long idContenido
    ) {
        long count = EstadisticasListener.DB_FAVORITOS.values().stream()
                .filter(f -> Objects.equals(f.getIdContenido(), idContenido))
                .count();
        return ResponseEntity.ok(count);
    }
}
