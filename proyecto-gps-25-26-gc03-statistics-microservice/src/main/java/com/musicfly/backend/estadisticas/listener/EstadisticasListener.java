package com.musicfly.backend.estadisticas.listener;

import com.musicfly.backend.estadisticas.models.dto.FavoritosDTO;
import com.musicfly.backend.estadisticas.models.dto.RatingDTO;
import com.musicfly.backend.estadisticas.models.dto.VisualizacionDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
public class EstadisticasListener {

    // MAPAS CONCURRENTE
    public static final ConcurrentMap<String, VisualizacionDTO> DB_VISUALIZACIONES = new ConcurrentHashMap<>();
    public static final ConcurrentMap<String, RatingDTO> DB_RATINGS = new ConcurrentHashMap<>();
    public static final ConcurrentMap<String, FavoritosDTO> DB_FAVORITOS = new ConcurrentHashMap<>();

    // Map secundario para historial por perfil
    public static final ConcurrentMap<Long, ConcurrentSkipListSet<String>> PERFIL_VISUALIZACIONES = new ConcurrentHashMap<>();

    // ------------------- VISUALIZACIONES -------------------
    @KafkaListener(topics = "${app.kafka.topic.visualizaciones}", groupId = "grupo-estadisticas")
    public void gestionarVisualizacion(VisualizacionDTO v) {
        String accion = v.getAccion() != null ? v.getAccion() : "CREATED";
        String idVisualizacion = v.getIdVisualizacion();
        Long idPerfil = v.getIdPerfil();

        switch (accion.toUpperCase()) {
            case "DELETED":
                DB_VISUALIZACIONES.remove(idVisualizacion);
                if (PERFIL_VISUALIZACIONES.containsKey(idPerfil)) {
                    PERFIL_VISUALIZACIONES.get(idPerfil).remove(idVisualizacion);
                }
                break;
            case "DELETED_PROFILE":
                if (PERFIL_VISUALIZACIONES.containsKey(idPerfil)) {
                    PERFIL_VISUALIZACIONES.get(idPerfil).forEach(DB_VISUALIZACIONES::remove);
                    PERFIL_VISUALIZACIONES.remove(idPerfil);
                }
                break;
            case "UPDATED":
            case "CREATED":
            default:
                DB_VISUALIZACIONES.put(idVisualizacion, v);
                PERFIL_VISUALIZACIONES.computeIfAbsent(idPerfil, k -> new ConcurrentSkipListSet<>()).add(idVisualizacion);
        }
    }

    // ------------------- RATINGS -------------------
    @KafkaListener(topics = "${app.kafka.topic.ratings}", groupId = "grupo-estadisticas")
    public void gestionarRating(RatingDTO r) {
        String accion = r.getAccion() != null ? r.getAccion() : "CREATED";
        String key = r.getIdPerfil() + "_" + r.getIdContenido();

        switch (accion.toUpperCase()) {
            case "DELETED":
                DB_RATINGS.remove(key);
                break;
            case "UPDATED":
            case "CREATED":
            default:
                DB_RATINGS.put(key, r);
        }
    }

    // ------------------- FAVORITOS -------------------
    @KafkaListener(topics = "${app.kafka.topic.favoritos}", groupId = "grupo-estadisticas")
    public void gestionarFavorito(FavoritosDTO f) {
        String accion = f.getAccion() != null ? f.getAccion() : "CREATED";
        String key = f.getIdPerfil() + "_" + f.getIdContenido();

        switch (accion.toUpperCase()) {
            case "DELETED":
                DB_FAVORITOS.remove(key);
                break;
            case "CREATED":
            default:
                DB_FAVORITOS.putIfAbsent(key, f);
        }
    }
}
