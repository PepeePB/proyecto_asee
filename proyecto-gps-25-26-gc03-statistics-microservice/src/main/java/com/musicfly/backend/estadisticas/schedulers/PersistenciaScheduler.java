package com.musicfly.backend.estadisticas.scheduler;

import com.musicfly.backend.estadisticas.listener.EstadisticasListener;
import com.musicfly.backend.estadisticas.models.dao.Estadisticas;
import com.musicfly.backend.estadisticas.repositories.EstadisticasRepository;
import com.musicfly.backend.estadisticas.models.dto.RatingDTO;
import com.musicfly.backend.estadisticas.models.dto.FavoritosDTO;
import com.musicfly.backend.estadisticas.models.dto.VisualizacionDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PersistenciaScheduler {

    private final EstadisticasRepository estadisticasRepository;

    public PersistenciaScheduler(EstadisticasRepository estadisticasRepository) {
        this.estadisticasRepository = estadisticasRepository;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000) // cada 5 minutos
    public void persistirEstadisticas() {

        // Obtenemos todas las canciones que tengan visualizaciones, ratings o favoritos
        Set<Long> idsCanciones = new HashSet<>();
        idsCanciones.addAll(EstadisticasListener.DB_VISUALIZACIONES.values().stream()
                .map(VisualizacionDTO::getIdContenido).collect(Collectors.toSet()));
        idsCanciones.addAll(EstadisticasListener.DB_RATINGS.values().stream()
                .map(RatingDTO::getIdContenido).collect(Collectors.toSet()));
        idsCanciones.addAll(EstadisticasListener.DB_FAVORITOS.values().stream()
                .map(FavoritosDTO::getIdContenido).collect(Collectors.toSet()));

        List<Estadisticas> batch = new ArrayList<>();

        for (Long idCancion : idsCanciones) {

            long visualizaciones = EstadisticasListener.DB_VISUALIZACIONES.values().stream()
                    .filter(v -> Objects.equals(v.getIdContenido(), idCancion))
                    .count();

            long favoritos = EstadisticasListener.DB_FAVORITOS.values().stream()
                    .filter(f -> Objects.equals(f.getIdContenido(), idCancion))
                    .count();

            List<RatingDTO> ratings = EstadisticasListener.DB_RATINGS.values().stream()
                    .filter(r -> Objects.equals(r.getIdContenido(), idCancion))
                    .collect(Collectors.toList());

            long valoraciones = ratings.size();
            double valoracionMedia = 0.0;
            if (!ratings.isEmpty()) {
                valoracionMedia = ratings.stream()
                        .mapToDouble(r -> r.getRating() != null ? r.getRating() : 0)
                        .average()
                        .orElse(0.0);
            }

            Estadisticas e = new Estadisticas(idCancion, visualizaciones, favoritos, valoraciones, valoracionMedia);
            batch.add(e);
        }

        estadisticasRepository.saveAll(batch);
        System.out.println("Batch de estadísticas persistido en MySQL: " + batch.size() + " canciones");
    }
}
