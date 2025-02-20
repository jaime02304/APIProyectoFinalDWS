package edu.proyectoFinalAPI.Daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.proyectoFinalAPI.Dtos.UsuarioDto;
import edu.proyectoFinalAPI.Dtos.UsuarioPerfilDto;
import jakarta.transaction.Transactional;

/**
 * Interfaz repositorio donde se encuentra la cabecera de los metodos basicos en
 * relacion con interacciones con la base de datos
 * 
 * @author jpribio - 18/01/25
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad, Long> {

	UsuarioEntidad findByIdUsuEntidad(Long idUsuEntidad);

	UsuarioEntidad findByAliasUsuEntidad(String aliasUsuEntidad);

	UsuarioEntidad findByCorreoElectronicoUsuEntidad(String correoElectronicoUsuEntidad);

	@Query(value = "SELECT u.* FROM usuarios u WHERE u.rol_usu = 'user'", nativeQuery = true)
	List<UsuarioEntidad> findByRol();

	@Query(value = "SELECT u.* FROM usuarios u WHERE u.rol_usu != 'sadmin'", nativeQuery = true)
	List<UsuarioEntidad> findAll();

	@Transactional
	@Modifying
	@Query("UPDATE UsuarioEntidad u SET u.nombreCompletoUsuEntidad = :#{#usuario.nombreCompletoUsu}, u.aliasUsuEntidad = :#{#usuario.aliasUsu}, u.movilUsuEntidad = :#{#usuario.movilUsu}, u.fotoUsuEntidad = :#{#usuario.fotoUsu} WHERE u.correoElectronicoUsuEntidad = :#{#usuario.correoElectronicoUsu}")
	int actualizarUsuarioPorCorreo(@Param("usuario") UsuarioPerfilDto usuario);

	@Transactional
	@Modifying
	@Query("DELETE FROM UsuarioEntidad u WHERE u.aliasUsuEntidad = :alias")
	int eliminarUsuarioPorNombre(@Param("alias") String alias);

	@Transactional
	@Modifying
	@Query("UPDATE UsuarioEntidad u SET " + "u.nombreCompletoUsuEntidad = :#{#usuario.nombreCompletoUsu}, "
			+ "u.aliasUsuEntidad = :#{#usuario.aliasUsu}, " + "u.movilUsuEntidad = :#{#usuario.movilUsu}, "
			+ "u.rolUsuEntidad = :#{#usuario.rolUsu}, " + "u.fotoUsuEntidad = :#{#usuario.fotoUsu}, "
			+ "u.esPremiumEntidad = :#{#usuario.esPremium}, "
			+ "u.esVerificadoEntidad = :#{#usuario.esVerificadoEntidad} "
			+ "WHERE u.correoElectronicoUsuEntidad = :#{#usuario.correoElectronicoUsu}")
	int actualizarUsuarioCompletoPorCorreoComoAdmin(@Param("usuario") UsuarioPerfilDto usuario);

	boolean existsByCorreoElectronicoUsuEntidad(String correoElectronico);

	boolean existsByAliasUsuEntidadAndIdUsuEntidadNot(String alias, Long idUsu);

	boolean existsByAliasUsuEntidad(String alias);
}
