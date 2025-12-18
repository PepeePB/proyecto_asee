package com.musicfly.backend.estadisticas.repositories;

import com.musicfly.backend.estadisticas.models.dao.Estadisticas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadisticasRepository extends JpaRepository<Estadisticas, Long> {
}
