package edu.proyectoFinalAPI.Daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositorio donde estan los metodos de los grupos y ejecucion a la base de
 * datos
 * 
 * @author jpribio - 20/01/25
 */
@Repository
public interface GruposRepositorio extends JpaRepository<GrupoEntidad, Long> {

	@Query(value = "SELECT * FROM grupos ORDER BY numero_usuarios DESC LIMIT 5", nativeQuery = true)
	List<GrupoEntidad> findTop5GroupsByNumeroUsuariosDesc();
}
