package edu.proyectoFinalAPI.Daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.proyectoFinalAPI.Dtos.UsuarioDto;
import jakarta.transaction.Transactional;

/**
 * Interfaz repositorio donde se encuentra la cabecera de los metodos basicos en
 * relacion con interacciones con la base de datos
 * 
 * @author jpribio - 18/01/25
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad, Long> {

	UsuarioEntidad findByAliasUsuEntidad(String aliasUsuEntidad);

	UsuarioEntidad findByCorreoElectronicoUsuEntidad(String correoElectronicoUsuEntidad);

	@Query(value = "SELECT u.* FROM usuarios u WHERE u.rol_usu = 'user'", nativeQuery = true)
	List<UsuarioEntidad> findByRol();
	
	@Query(value = "SELECT u.* FROM usuarios u WHERE u.rol_usu != 'sadmin'", nativeQuery = true)
	List<UsuarioEntidad> findAll();
	
	  @Transactional
	    @Modifying
	    @Query("UPDATE UsuarioEntidad u SET u.nombreCompletoUsuEntidad = :#{#usuario.nombreCompleto}, u.aliasUsuEntidad = :#{#usuario.alias}, u.movilUsuEntidad = :#{#usuario.movil}, u.fotoUsuEntidad = :#{#usuario.foto} WHERE u.correoElectronicoUsuEntidad = :#{#usuario.correo}")
	    int actualizarUsuarioPorCorreo(@Param("usuario") UsuarioDto usuario);
}
