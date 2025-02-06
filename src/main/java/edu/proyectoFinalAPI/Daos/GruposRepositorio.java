package edu.proyectoFinalAPI.Daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	@Query(value = "SELECT g.* FROM grupos g " + "INNER JOIN usuarios u ON g.creador_usu_id = u.id_usu "
			+ "WHERE u.correo_electronico_usu = :correoElectronico", nativeQuery = true)
	List<GrupoEntidad> findAllGroupsByUserEmail(@Param("correoElectronico") String correoElectronico);

	@Query(value = "SELECT g.* FROM grupos g", nativeQuery = true)
	List<GrupoEntidad> findAll();
}
