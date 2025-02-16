package edu.proyectoFinalAPI.Daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio en relacion con la tabla tipos
 * 
 * @author jpribio - 21/01/25
 */
@Repository
public interface TiposRepositorio extends JpaRepository<TiposEntidad, Object> {

	TiposEntidad findByNombreTipoAndNivelTipo(String nombreTipo, int nivelTipo);
}
