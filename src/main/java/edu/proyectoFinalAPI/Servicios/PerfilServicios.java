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
		if (eliminarElemento == null) {
			throw new IllegalArgumentException("El DTO de elemento no puede ser nulo.");
		}
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
		if (repositorioUsuario.existsByAliasUsuEntidadAndIdUsuEntidadNot(usuarioAModificar.getAliasUsu(),
				usuarioAModificar.getIdUsu())) {
			return false;
		}
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
		if (repositorioGrupo.existsByNombreGrupoAndIdGrupoNot(grupoAModificar.getNombreGrupo(),
				grupoAModificar.getIdGrupo())) {
			return false;
		}
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
		if (repositorioUsuario.existsByAliasUsuEntidad(usuarioACrear.getAliasUsu())) {
			return false; // Alias ya existe, no se crea el usuario
		}
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
		UsuarioEntidad usuarioGuardado = repositorioUsuario.save(usuario);

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
	public GrupoEntidad crearGrupoComoAdministrador(GruposDto grupoACrear) throws IllegalArgumentException, Exception {
		GrupoEntidad grupo = new GrupoEntidad();
		TiposEntidad categoria = repositorioTipos.findByNombreTipo(grupoACrear.getCategoriaNombre());
		TiposEntidad subCategoria = repositorioTipos.findByNombreTipo(grupoACrear.getSubCategoriaNombre());
		UsuarioEntidad creador = repositorioUsuario.findByAliasUsuEntidad(grupoACrear.getAliasCreadorUString());
		grupo.setNombreGrupo(grupoACrear.getNombreGrupo());
		grupo.setNumeroUsuarios(grupoACrear.getNumeroUsuarios() != null ? grupoACrear.getNumeroUsuarios() : 0L);
		grupo.setFechaGrupo(LocalDateTime.now());
		grupo.setCategoriaId(categoria);
		grupo.setSubCategoriaId(subCategoria);
		grupo.setCreadorUsuId(creador);
		return repositorioGrupo.save(grupo);
	}

	/**
	 * Metodo que inserta en la base de datos el comentario del usuario
	 * 
	 * @author jpribio - 20/02/25
	 * @param nuevoComentario
	 * @return
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	public ComentariosEntidad crearNuevoComentario(ComentariosPerfilDto nuevoComentario)
			throws IllegalArgumentException, Exception {
		ComentariosEntidad comentario = new ComentariosEntidad();
		TiposEntidad categoria = repositorioTipos.findByNombreTipo(nuevoComentario.getCategoriaTipo());
		TiposEntidad subCategoria = repositorioTipos.findByNombreTipo(nuevoComentario.getSubCategoriaTipo());
		UsuarioEntidad creador = repositorioUsuario.findByIdUsuEntidad(nuevoComentario.getIdUsuario());
		// Mapear los campos del DTO a la entidad.
		comentario.setComentarioTexto(nuevoComentario.getComentarioTexto());
		comentario.setFechaComentario(LocalDateTime.now());
		comentario.setUsuarioId(creador);
		comentario.setCategoriaId(categoria);
		comentario.setSubCategoriaId(subCategoria);
		return repositorioComentariorepositorio.save(comentario);
	}
}
