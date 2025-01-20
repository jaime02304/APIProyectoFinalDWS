package edu.proyectoFinalAPI.Daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
