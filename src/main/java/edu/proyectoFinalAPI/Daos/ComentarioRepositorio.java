
package edu.proyectoFinalAPI.Daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Clase repositorio con la relacion de los comentarios
 * 
 * @author jpribio - 22/01/25
 */
@Repository
public interface ComentarioRepositorio extends JpaRepository<ComentariosEntidad, Object> {

	@Query(value = "SELECT * FROM comentarios WHERE categoria_id = 3 AND subcategoria_id = 7 ORDER BY fecha_comentario DESC LIMIT 10", nativeQuery = true)
	List<ComentariosEntidad> findByCategoriaId();

	@Query(value = "SELECT c.* FROM comentarios c INNER JOIN usuarios u ON c.usuario_id = u.id_usu WHERE u.correo_electronico_usu = :correoElectronico AND c.categoria_id = 3 AND c.subcategoria_id = 7 ORDER BY c.fecha_comentario DESC LIMIT 1", nativeQuery = true)
	ComentariosEntidad findLatestByCorreoElectronico(@Param("correoElectronico") String correoElectronico);
	
	List<ComentariosEntidad> findAllByOrderByFechaComentarioDesc();

}
