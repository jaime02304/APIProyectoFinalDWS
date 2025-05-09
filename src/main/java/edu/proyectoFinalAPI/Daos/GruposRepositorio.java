package edu.proyectoFinalAPI.Daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.proyectoFinalAPI.Dtos.GruposParaLasListasDto;
import jakarta.transaction.Transactional;

/**
 * Repositorio donde estan los metodos de los grupos y ejecucion a la base de
 * datos
 * 
 * @author jpribio - 20/01/25
 */
@Repository
public interface GruposRepositorio extends JpaRepository<GrupoEntidad, Long> {

	@Query(value = "SELECT * FROM grupos ORDER BY numero_usuarios LIMIT 5", nativeQuery = true)
	List<GrupoEntidad> findTop5GroupsByNumeroUsuariosDesc();

	@Query(value = "SELECT g.* FROM grupos g " + "INNER JOIN usuarios u ON g.creador_usu_id = u.id_usu "
			+ "WHERE u.correo_electronico_usu = :correoElectronico", nativeQuery = true)
	List<GrupoEntidad> findAllGroupsByUserEmail(@Param("correoElectronico") String correoElectronico);

	@Query(value = "SELECT g.* FROM grupos g", nativeQuery = true)
	List<GrupoEntidad> findAll();

	@Transactional
	@Modifying
	@Query("DELETE FROM GrupoEntidad g WHERE g.nombreGrupo = :nombreGrupo")
	int eliminarGrupoPorNombre(@Param("nombreGrupo") String nombreGrupo);

	@Transactional
	@Modifying
	@Query("UPDATE GrupoEntidad g " + "SET g.nombreGrupo = :#{#grupo.nombreGrupo} "
			+ "WHERE g.idGrupo = :#{#grupo.idGrupo}")
	int actualizarGrupoNombre(@Param("grupo") GruposParaLasListasDto grupo);

	@Transactional
	@Modifying
	@Query("UPDATE GrupoEntidad g SET g.categoriaId = (SELECT c.idCategoria FROM CategoriaEntidad c WHERE c.nombreCategoria = :#{#grupo.categoriaNombre}) WHERE g.idGrupo = :#{#grupo.idGrupo}")
	int actualizarCategoria(@Param("grupo") GruposParaLasListasDto grupo);

	@Transactional
	@Modifying
	@Query("UPDATE GrupoEntidad g SET g.subCategoriaId = (SELECT s.idSubcategoria FROM SubcategoriaEntidad s WHERE s.nombreSubcategoria = :#{#grupo.subCategoriaNombre}) WHERE g.idGrupo = :#{#grupo.idGrupo}")
	int actualizarSubCategoria(@Param("grupo") GruposParaLasListasDto grupo);

	@Transactional
	@Modifying
	@Query("UPDATE GrupoEntidad g SET g.descripcionGrupo = :#{#grupo.descripcionGrupo} WHERE g.idGrupo = :#{#grupo.idGrupo}")
	int actualizarDescripcion(@Param("grupo") GruposParaLasListasDto grupo);

	boolean existsByNombreGrupo(String nombreGrupo);

	boolean existsByNombreGrupoAndIdGrupoNot(String nombreGrupo, Long idGrupo);

	@Transactional
	@Modifying
	@Query("DELETE FROM GrupoEntidad g WHERE g.creadorUsuId = :creador")
	void deleteByCreador(@Param("creador") UsuarioEntidad creador);

	List<GrupoEntidad> findAllByOrderByNumeroUsuariosDesc();

	GrupoEntidad findByNombreGrupo(String nombreGrupo);

}
