package edu.proyectoFinalAPI.Servicios;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.proyectoFinalAPI.Daos.ComentarioRepositorio;
import edu.proyectoFinalAPI.Daos.ComentariosEntidad;
import edu.proyectoFinalAPI.Daos.GrupoEntidad;
import edu.proyectoFinalAPI.Daos.GruposRepositorio;
import edu.proyectoFinalAPI.Daos.TiposEntidad;
import edu.proyectoFinalAPI.Daos.TiposRepositorio;
import edu.proyectoFinalAPI.Daos.UsuarioEntidad;
import edu.proyectoFinalAPI.Daos.UsuarioRepositorio;
import edu.proyectoFinalAPI.Dtos.ComentariosPerfilDto;
import edu.proyectoFinalAPI.Dtos.EliminarElementoPerfilDto;
import edu.proyectoFinalAPI.Dtos.GruposDto;
import edu.proyectoFinalAPI.Dtos.GruposParaLasListasDto;
import edu.proyectoFinalAPI.Dtos.UsuarioDto;
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

	@Autowired
	private GruposRepositorio repositorioGrupo;

	@Autowired
	private TiposRepositorio repositorioTipos;

	/**
	 * MEtodo que mediante el parametro del correo electronico busca el mensaje del
	 * usuario
	 * 
	 * @author jpribio - 02/02/25
	 * @param PerfilUsuario
	 * @return
	 */
	public ComentariosPerfilDto obteneroComentarioDelPerfil(UsuarioPerfilDto PerfilUsuario) throws Exception {
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
	public List<UsuarioPerfilDto> obtenerLosUsuariosAdmin() throws Exception {
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
	public List<UsuarioPerfilDto> obtenerLosUsuarios() throws Exception {
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
	 * Metodo que elimina elemento por su nombre
	 * 
	 * @author jpribio - 15/02/25
	 * @param eliminarElemento
	 * @return
	 */
	public Boolean eliminarElemento(EliminarElementoPerfilDto eliminarElemento)
			throws IllegalArgumentException, Exception {
		int filasAfectadas = 0;
		if (eliminarElemento.isEsUsuarioEliminar()) {
			filasAfectadas = repositorioUsuario.eliminarUsuarioPorNombre(eliminarElemento.getElementoEliminar());
		} else {
			filasAfectadas = repositorioGrupo.eliminarGrupoPorNombre(eliminarElemento.getElementoEliminar());
		}
		return filasAfectadas > 0;
	}

	/**
	 * MEtodo que manda un usuario completo con los nuevos valores que se han
	 * modificado y las sustituye por las antiguas
	 * 
	 * @author jpribio - 15/02/25
	 * @param usuarioAModificar
	 * @return
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	public boolean modificarUsuarioComoAdministrador(UsuarioPerfilDto usuarioAModificar)
			throws IllegalArgumentException, Exception {
		int filasAfectadas = repositorioUsuario.actualizarUsuarioCompletoPorCorreoComoAdmin(usuarioAModificar);

		return filasAfectadas > 0;
	}

	/**
	 * MEtodo que manda un grupo completo con los nuevos valores que se han
	 * modificado y las sustituye por las antiguas
	 * 
	 * @author jpribio - 15/02/25
	 * @param grupoAModificar
	 * @return
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	public boolean modificarGrupoComoAdministrador(GruposParaLasListasDto grupoAModificar)
			throws IllegalArgumentException, Exception {
		int filasAfectadas = repositorioGrupo.actualizarGrupoNombre(grupoAModificar)
				+ repositorioGrupo.actualizarCategoria(grupoAModificar)
				+ repositorioGrupo.actualizarSubCategoria(grupoAModificar);

		return filasAfectadas > 0;
	}

	/**
	 * Metodo que inserta en la base de datos el nuevo usuario
	 * 
	 * @param usuarioACrear
	 * @return
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	public boolean crearUsuarioComoAdministrador(UsuarioDto usuarioACrear) throws IllegalArgumentException, Exception {
		UsuarioEntidad usuario = new UsuarioEntidad();

		usuario.setNombreCompletoUsuEntidad(usuarioACrear.getNombreCompletoUsu());
		usuario.setAliasUsuEntidad(usuarioACrear.getAliasUsu());
		usuario.setCorreoElectronicoUsuEntidad(usuarioACrear.getCorreoElectronicoUsu());
		usuario.setMovilUsuEntidad(usuarioACrear.getMovilUsu());
		usuario.setRolUsuEntidad(usuarioACrear.getRolUsu());
		usuario.setFotoUsuEntidad(usuarioACrear.getFotoUsu());
		usuario.setContraseniaUsuEntidad(usuarioACrear.getContraseniaUsu());
		usuario.setEsPremiumEntidad(usuarioACrear.getEsPremium());
		usuario.setEsVerificadoEntidad(usuarioACrear.getEsVerificadoEntidad());

		// Se guarda la entidad en la base de datos
		UsuarioEntidad usuarioGuardado = repositorioUsuario.save(usuario);

		// Se considera exitosa la inserción si la entidad guardada no es nula y tiene
		// un ID asignado.
		return usuarioGuardado != null && usuarioGuardado.getIdUsuEntidad() != null;
	}

	/**
	 * Metodo que crea un nuevo grupo en la base de datos
	 * 
	 * @author jpribio - 17/02/25
	 * @param grupoACrear
	 * @return
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	public boolean crearGrupoComoAdministrador(GruposDto grupoACrear) throws IllegalArgumentException, Exception {
		if (grupoACrear == null) {
			throw new IllegalArgumentException("El DTO de grupo no puede ser nulo.");
		}

		GrupoEntidad grupo = new GrupoEntidad();

		TiposEntidad categoria = repositorioTipos.findByNombreTipoAndNivelTipo(grupoACrear.getCategoriaNombre(), 1);
		if (categoria == null) {
			throw new IllegalArgumentException("La categoría especificada no existe.");
		}

		TiposEntidad subCategoria = repositorioTipos.findByNombreTipoAndNivelTipo(grupoACrear.getSubCategoriaNombre(),
				2);
		if (subCategoria == null) {
			throw new IllegalArgumentException("La subcategoría especificada no existe.");
		}
		UsuarioEntidad creador = repositorioUsuario.findByAliasUsuEntidad(grupoACrear.getAliasCreadorUString());
		if (creador == null) {
			throw new IllegalArgumentException(
					"El usuario creador con alias " + grupoACrear.getAliasCreadorUString() + " no existe.");
		}

		grupo.setNombreGrupo(grupoACrear.getNombreGrupo());
		grupo.setNumeroUsuarios(grupoACrear.getNumeroUsuarios() != null ? grupoACrear.getNumeroUsuarios() : 0L);
		grupo.setFechaGrupo(LocalDateTime.now());
		grupo.setCategoriaId(categoria);
		grupo.setSubCategoriaId(subCategoria);
		grupo.setCreadorUsuId(creador);
		GrupoEntidad grupoGuardado = repositorioGrupo.save(grupo);

		return grupoGuardado != null && grupoGuardado.getIdGrupo() != null;
	}

}
