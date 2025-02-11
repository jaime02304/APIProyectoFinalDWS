package edu.proyectoFinalAPI.Servicios;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.proyectoFinalAPI.Daos.ComentarioRepositorio;
import edu.proyectoFinalAPI.Daos.ComentariosEntidad;
import edu.proyectoFinalAPI.Daos.UsuarioEntidad;
import edu.proyectoFinalAPI.Daos.UsuarioRepositorio;
import edu.proyectoFinalAPI.Dtos.ComentariosPerfilDto;
import edu.proyectoFinalAPI.Dtos.UsuarioPerfilDto;

/**
 * Clase donde se encuentra todos los metodos que tenga relacion con el perfil
 * 
 * @author jpribio - 02/02/25
 */
@Service
public class PerfilServicios {

	/**
	 * Nueva instancia del repositorios de los comentarios
	 */

	@Autowired
	private ComentarioRepositorio repositorioComentariorepositorio;

	@Autowired
	private UsuarioRepositorio repositorioUsuario;

	/**
	 * MEtodo que mediante el parametro del correo electronico busca el mensaje del
	 * usuario
	 * 
	 * @author jpribio - 02/02/25
	 * @param PerfilUsuario
	 * @return
	 */
	public ComentariosPerfilDto obteneroComentarioDelPerfil(UsuarioPerfilDto PerfilUsuario) {
		ComentariosEntidad comentarioPerfilE = repositorioComentariorepositorio
				.findLatestByCorreoElectronico(PerfilUsuario.getCorreoElectronicoUsu());
		if (comentarioPerfilE == null) {
			return null;
		}
		ComentariosPerfilDto comentarioPerfilDto = new ComentariosPerfilDto();
		comentarioPerfilDto.setComentarioTexto(comentarioPerfilE.getComentarioTexto());
		comentarioPerfilDto.setCategoriaTipo(comentarioPerfilE.getCategoriaId().getNombreTipo());
		comentarioPerfilDto.setSubCategoriaTipo(comentarioPerfilE.getSubCategoriaId().getNombreTipo());

		return comentarioPerfilDto;

	}

	/**
	 * Metodo que coge a todos los usuarios escepto los que son administradores y
	 * sadmin
	 * 
	 * @author jptibio - 06/02/25
	 * @return
	 */
	public List<UsuarioPerfilDto> obtenerLosUsuariosAdmin() {
		// Obtener los 5 grupos más populares por número de usuarios
		List<UsuarioEntidad> usuarioE = repositorioUsuario.findByRol();

		// Si no se encuentran grupos, retornar una lista vacía en lugar de null
		if (usuarioE == null || usuarioE.isEmpty()) {
			return Collections.emptyList(); // Mejor retornar una lista vacía en vez de null
		}

		// Mapear los grupos a la lista de DTOs
		return usuarioE.stream().map(usuarioEntidad -> {
			UsuarioPerfilDto usuario = new UsuarioPerfilDto();
			usuario.setIdUsu(usuarioEntidad.getIdUsuEntidad());
			usuario.setAliasUsu(usuarioEntidad.getAliasUsuEntidad());
			usuario.setCorreoElectronicoUsu(usuarioEntidad.getCorreoElectronicoUsuEntidad());
			usuario.setEsPremium(usuarioEntidad.getEsPremiumEntidad());
			usuario.setEsVerificadoEntidad(usuarioEntidad.getEsVerificadoEntidad());
			usuario.setFotoUsu(usuarioEntidad.getFotoUsuEntidad());
			usuario.setMovilUsu(usuarioEntidad.getMovilUsuEntidad());
			usuario.setNombreCompletoUsu(usuarioEntidad.getNombreCompletoUsuEntidad());
			usuario.setRolUsu(usuarioEntidad.getRolUsuEntidad());
			return usuario;
		}).collect(Collectors.toList());
	}

	/**
	 * Metodo que coge a todos los usuarios escepto los que son administradores y
	 * sadmin
	 * 
	 * @author jptibio - 06/02/25
	 * @return
	 */
	public List<UsuarioPerfilDto> obtenerLosUsuarios() {
		// Obtener los 5 grupos más populares por número de usuarios
		List<UsuarioEntidad> usuarioE = repositorioUsuario.findAll();

		// Si no se encuentran grupos, retornar una lista vacía en lugar de null
		if (usuarioE == null || usuarioE.isEmpty()) {
			return Collections.emptyList(); // Mejor retornar una lista vacía en vez de null
		}

		// Mapear los grupos a la lista de DTOs
		return usuarioE.stream().map(usuarioEntidad -> {
			UsuarioPerfilDto usuario = new UsuarioPerfilDto();
			usuario.setIdUsu(usuarioEntidad.getIdUsuEntidad());
			usuario.setAliasUsu(usuarioEntidad.getAliasUsuEntidad());
			usuario.setCorreoElectronicoUsu(usuarioEntidad.getCorreoElectronicoUsuEntidad());
			usuario.setEsPremium(usuarioEntidad.getEsPremiumEntidad());
			usuario.setEsVerificadoEntidad(usuarioEntidad.getEsVerificadoEntidad());
			usuario.setFotoUsu(usuarioEntidad.getFotoUsuEntidad());
			usuario.setMovilUsu(usuarioEntidad.getMovilUsuEntidad());
			usuario.setNombreCompletoUsu(usuarioEntidad.getNombreCompletoUsuEntidad());
			usuario.setRolUsu(usuarioEntidad.getRolUsuEntidad());
			return usuario;
		}).collect(Collectors.toList());
	}

	/**
	 * Metodo que modifica al usuario con los valores recibidos
	 * 
	 * @author jpribio - 11/02/25
	 * @return devuelve el usuario
	 */
	public UsuarioPerfilDto modificarUsuario(UsuarioPerfilDto usuarioAModificar){
		
		
		return null;
	}

}
