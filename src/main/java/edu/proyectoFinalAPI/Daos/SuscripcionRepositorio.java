package edu.proyectoFinalAPI.Daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositorio donde se encuentra la relacion con la suscripcion
 * 
 * @author jpribio - 07/05/25
 */
@Repository
public interface SuscripcionRepositorio extends JpaRepository<SuscripcionEntidad, Long> {

	@Query("SELECT s.usuarioId FROM SuscripcionEntidad s WHERE s.grupoId.nombreGrupo = :nombreGrupo")
	List<UsuarioEntidad> findUsuariosByNombreGrupo(@Param("nombreGrupo") String nombreGrupo);

	boolean existsByGrupoIdAndUsuarioId(GrupoEntidad grupo, UsuarioEntidad usuario);

}
