
package edu.proyectoFinalAPI.Daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Clase repositorio con la relacion de los comentarios
 * 
 * @author jpribio - 22/01/25
 */
@Repository
public interface ComentarioRepositorio extends JpaRepository<ComentariosEntidad, Object> {

	@Query(value = "SELECT * FROM comentarios WHERE categoria_id = 9 AND subcategoria_id = 9 ORDER BY fecha_comentario DESC LIMIT 10", nativeQuery = true)
	List<ComentariosEntidad> findByCategoriaId();

}
