package edu.proyectoFinalAPI.controlador;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.proyectoFinalAPI.Dtos.ComentariosIndexDto;
import edu.proyectoFinalAPI.Dtos.ComentariosPerfilDto;
import edu.proyectoFinalAPI.Dtos.EliminarElementoPerfilDto;
import edu.proyectoFinalAPI.Dtos.GruposDto;
import edu.proyectoFinalAPI.Dtos.GruposParaLasListasDto;
import edu.proyectoFinalAPI.Dtos.UsuarioDto;
import edu.proyectoFinalAPI.Dtos.UsuarioPerfilDto;
import edu.proyectoFinalAPI.Servicios.ComentarioServicio;
import edu.proyectoFinalAPI.Servicios.GrupoServicios;
import edu.proyectoFinalAPI.Servicios.PerfilServicios;
import edu.proyectoFinalAPI.Servicios.UsuariosServicios;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Controlador principal de la APi donde tiene las rutas de los metodos de la
 * misma
 * 
 * @author jpribio - 18/01/25
 */

@RestController
@RequestMapping("/api")
public class controladorApi {

	@Autowired
	private UsuariosServicios servicioUsuario;
	@Autowired
	private GrupoServicios servicioGrupo;
	@Autowired
	private ComentarioServicio servicioComentarios;
	@Autowired
	private PerfilServicios serviciosPerfil;

	/**
	 * Metodo que se enuentra el registro
	 * 
	 * @author jpribio - 19/01/25
	 * @param usuario (informacion necesaria para el registro)
	 * @return
	 */
	@PostMapping("/usuario/registro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> registroApi(@RequestBody UsuarioDto usuario) {
		if (usuario == null) {
			return Map.of("error", "El usuario no puede ser nulo.");
		}

		try {
			UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.nuevoUsuario(usuario);
			if (usuarioPerfilDto != null) {
				return convertirUsuarioDtoAMap(usuarioPerfilDto);
			} else {
				return Map.of("error", "No se pudo crear el usuario.");
			}
		} catch (IllegalArgumentException iaE) {
			return Map.of("error", "Datos proporcionados no válidos: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			return Map.of("error", "Algunos campos están vacíos o nulos: " + nE.getMessage());
		} catch (Exception e) {
			return Map.of("error", "Error inesperado: " + e.getMessage());
		}
	}

	/**
	 * Metodo que se encuentra el inicio de sesion
	 * 
	 * @author jpribio - 19/01/25
	 * @param usuario (informacion para el inicio de sesion)
	 * @return
	 */
	@PostMapping("/usuario/inicioSesion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> inicioSesionUsuario(@RequestBody UsuarioDto usuario) {
		if (usuario == null) {
			return Map.of("error", "El usuario no puede ser nulo.");
		}

		try {
			// Llamada al servicio para verificar el inicio de sesión
			UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.inicioSesionUsu(usuario);

			if (usuarioPerfilDto != null) {
				return convertirUsuarioDtoAMap(usuarioPerfilDto);
			} else {
				return Map.of("error", "Usuario no encontrado.");
			}
		} catch (IllegalArgumentException iaE) {
			return Map.of("error", "Argumento inválido: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			return Map.of("error", "Se produjo un error debido a un valor nulo: " + nE.getMessage());
		} catch (Exception e) {
			return Map.of("error", "Ocurrió un error inesperado: " + e.getMessage());
		}
	}

	@GetMapping("/index/grupos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> obtenerGruposMasNumerosos() {
		Map<String, Object> response = new HashMap<>();

		try {
			// Llamada al servicio para obtener los 5 grupos más populares
			List<GruposParaLasListasDto> listadoGrupo = servicioGrupo.recogerLosGruposMasTop();

			if (listadoGrupo == null || listadoGrupo.isEmpty()) {
				// Si no hay grupos, retornar una lista vacía
				response.put("grupos", Collections.emptyList());
			} else {
				response.put("grupos", listadoGrupo);
			}
		} catch (Exception e) {
			// Manejo general de excepciones
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	/**
	 * Metodo que devuelve el array de comentarios para el index
	 * 
	 * @author jpribio - 26/01/25
	 * @return Devuelve el Map con lo necesario para los grupos del index
	 */
	@GetMapping("/index/comentarios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> obtenerComentarios() {
		Map<String, Object> response = new HashMap<>();

		try {
			List<ComentariosIndexDto> listadoComentarios = servicioComentarios.recogerComentariosParaElIndex();

			if (listadoComentarios == null || listadoComentarios.isEmpty()) {
				response.put("mensaje", "No se encuentra ningún comentario de bienvenida");
			} else {
				response.put("comentarios", listadoComentarios);
			}
		} catch (IllegalArgumentException iaE) {
			response.put("error", "Argumento inválido: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			response.put("error", "Se produjo un error debido a un valor nulo: " + nE.getMessage());
		} catch (Exception e) {
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	/**
	 * MEtodo que busca el comentario inicial del usuario en cuestion
	 * 
	 * @author jpribio - 04/02/25
	 * @param perfilUsuario
	 * @return
	 */
	@PostMapping("/perfil/comentario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> obtenerComentarioDelUsuario(@RequestBody UsuarioPerfilDto perfilUsuario) {
		Map<String, Object> response = new HashMap<>();

		try {
			ComentariosPerfilDto comentariosPerfil = serviciosPerfil.obteneroComentarioDelPerfil(perfilUsuario);

			if (comentariosPerfil == null) {
				response.put("mensaje", "No se encuentra ningún comentario de bienvenida del usuario");
			} else {
				response.put("comentarios", comentariosPerfil);
			}
		} catch (IllegalArgumentException iaE) {
			response.put("error", "Argumento inválido: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			response.put("error", "Se produjo un error debido a un valor nulo: " + nE.getMessage());
		} catch (Exception e) {
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;

	}

	/**
	 * MEtodo que busca el listado de grupos creado por el ususario
	 * 
	 * @author jpribio - 04/02/25
	 * @param ususarioParaFiltrar
	 * @return
	 */
	@PostMapping("/perfil/grupos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> obtenerGruposDelUSuario(@RequestBody UsuarioPerfilDto ususarioParaFiltrar) {
		Map<String, Object> response = new HashMap<>();

		try {
			List<GruposParaLasListasDto> listadoGrupo = servicioGrupo.recogerLosGruposDelUsuario(ususarioParaFiltrar);

			if (listadoGrupo == null || listadoGrupo.isEmpty()) {
				// Si no hay grupos, retornar una lista vacía
				response.put("gruposPerfil", Collections.emptyList());
			} else {
				response.put("gruposPerfil", listadoGrupo);
			}
		} catch (Exception e) {
			// Manejo general de excepciones
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	/**
	 * Metodo que recoge la peticion de todos los grupos y los manda a la web
	 * 
	 * @author jpribio - 06/02/25
	 * @return
	 */
	@GetMapping("/grupos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> obtenerTodosLOsGrupos() {
		Map<String, Object> response = new HashMap<>();

		try {
			List<GruposParaLasListasDto> listadoGrupo = servicioGrupo.recogerLosGrupos();

			if (listadoGrupo == null || listadoGrupo.isEmpty()) {
				// Si no hay grupos, retornar una lista vacía
				response.put("gruposPerfil", Collections.emptyList());
			} else {
				response.put("gruposPerfil", listadoGrupo);
			}
		} catch (Exception e) {
			// Manejo general de excepciones
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	/**
	 * Metodo que recoge la peticion de todos los usuarios de rol user y los manda a
	 * la web
	 * 
	 * @author jpribio - 06/02/25
	 * @return
	 */
	@GetMapping("/usuariosPerfil")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> obtenerTodosLOsUsuariosRolUser() {
		Map<String, Object> response = new HashMap<>();

		try {
			List<UsuarioPerfilDto> listadoUsuarios = serviciosPerfil.obtenerLosUsuariosAdmin();

			if (listadoUsuarios == null || listadoUsuarios.isEmpty()) {
				// Si no hay grupos, retornar una lista vacía
				response.put("usuarioPerfil", Collections.emptyList());
			} else {
				response.put("usuarioPerfil", listadoUsuarios);
			}
		} catch (Exception e) {
			// Manejo general de excepciones
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	/**
	 * Metodo que recoge la peticion de todos los usuarios y los manda a la web
	 * 
	 * @author jpribio - 06/02/25
	 * @return
	 */
	@GetMapping("/usuarioSAdminPerfil")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> obtenerTodosLosUsuarios() {
		Map<String, Object> response = new HashMap<>();

		try {
			List<UsuarioPerfilDto> listadoUsuarios = serviciosPerfil.obtenerLosUsuarios();

			if (listadoUsuarios == null || listadoUsuarios.isEmpty()) {
				// Si no hay grupos, retornar una lista vacía
				response.put("usuarioPerfil", Collections.emptyList());
			} else {
				response.put("usuarioPerfil", listadoUsuarios);
			}
		} catch (Exception e) {
			// Manejo general de excepciones
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	/**
	 * Metodo que modifica el perfil personal del usuario
	 * 
	 * @author jpribio - 15/02/25
	 * @param usuarioAModificar
	 * @return
	 */
	@PostMapping("/ModificarUsuario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> modificarUsuario(@RequestBody UsuarioPerfilDto usuarioAModificar) {
		if (usuarioAModificar == null) {
			return Map.of("error", "El usuario no puede ser nulo.");
		}

		try {
			UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.modificarUsuario(usuarioAModificar);
			if (usuarioPerfilDto == null) {
				return Map.of("error", "Usuario no encontrado.");
			}
			return convertirUsuarioDtoAMap(usuarioPerfilDto);
		} catch (IllegalArgumentException e) {
			return Map.of("error", "Argumento inválido: " + e.getMessage());
		} catch (Exception e) {
			return Map.of("error", "Ocurrió un error inesperado: " + e.getMessage());
		}
	}

	/**
	 * Metodo que elimina un elemento(usuario o grupo) por el identificador del
	 * alias o el nombre del grupo
	 * 
	 * @author jpribio - 15/02/25
	 * @param elementoAEliminar
	 * @return
	 */
	@PostMapping("/EliminarElemento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarElementoComoAdmin(@RequestBody EliminarElementoPerfilDto elementoAEliminar) {
		if (elementoAEliminar == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity(Map.of("error", "El elemento a eliminar no puede ser nulo.")).build();
		}

		try {
			// Aquí deberías llamar a la lógica de negocio o servicio que realice la
			// eliminación.
			// Por ejemplo:
			boolean eliminado = serviciosPerfil.eliminarElemento(elementoAEliminar);

			if (eliminado) {
				return Response.ok(Map.of("message", "Elemento eliminado correctamente.")).build();
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(Map.of("error", "No se pudo eliminar el elemento.")).build();
			}
		} catch (IllegalArgumentException e) {
			return Response.status(Status.BAD_REQUEST).entity(Map.of("error", "Argumento inválido: " + e.getMessage()))
					.build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage())).build();
		}
	}

	/**
	 * Metodo que modifica al usuario con las caracteristicas nuevas dadas
	 * 
	 * @author jpribio - 15/02/25
	 * @param usuarioAModificar
	 * @return
	 */
	@PostMapping("/ModificarUsuarioComoAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarUsuarioComoAdministrador(@RequestBody UsuarioPerfilDto usuarioAModificar) {
		if (usuarioAModificar == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity(Map.of("error", "El usuario a modificar no puede ser nulo.")).build();
		}

		try {
			// Llamada a la lógica de negocio o servicio que realiza la modificación.
			boolean modificado = serviciosPerfil.modificarUsuarioComoAdministrador(usuarioAModificar);

			if (modificado) {
				return Response.ok(Map.of("message", "Usuario modificado correctamente.")).build();
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(Map.of("error", "No se pudo modificar el usuario.")).build();
			}
		} catch (IllegalArgumentException e) {
			return Response.status(Status.BAD_REQUEST).entity(Map.of("error", "Argumento inválido: " + e.getMessage()))
					.build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage())).build();
		}
	}

	/**
	 * Metodo para modificar el grupo siendo administrador
	 * 
	 * @author jpribio - 15/02/25
	 * @param usuarioAModificar
	 * @return
	 */
	@PostMapping("/ModificarGrupoComoAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarGrupoComoAdministrador(@RequestBody GruposParaLasListasDto grupoAMNodificar) {
		if (grupoAMNodificar == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity(Map.of("error", "El grupo a modificar no puede ser nulo.")).build();
		}

		try {
			// Llamada a la lógica de negocio o servicio que realiza la modificación.
			boolean modificado = serviciosPerfil.modificarGrupoComoAdministrador(grupoAMNodificar);

			if (modificado) {
				return Response.ok(Map.of("message", "Grupo modificado correctamente.")).build();
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(Map.of("error", "No se pudo modificar el grupo.")).build();
			}
		} catch (IllegalArgumentException e) {
			return Response.status(Status.BAD_REQUEST).entity(Map.of("error", "Argumento inválido: " + e.getMessage()))
					.build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage())).build();
		}
	}

	/**
	 * Metodo que crea un nuevo usuario
	 * 
	 * @author jpribio - 16/02/25
	 * @param usuarioACrear
	 * @return
	 */
	@PostMapping("/CrearUsuarioComoAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearUnUsuarioComAdmin(@RequestBody UsuarioDto usuarioACrear) {
		if (usuarioACrear == null) {
			return Response.status(Status.BAD_REQUEST).entity(Map.of("error", "El usuario a crear no puede ser nulo."))
					.build();
		}

		try {
			// Llamada a la lógica de negocio o servicio que realiza la creación del usuario
			boolean creado = serviciosPerfil.crearUsuarioComoAdministrador(usuarioACrear);

			if (creado) {
				return Response.ok(Map.of("message", "Usuario creado correctamente.")).build();
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(Map.of("error", "No se pudo crear el usuario.")).build();
			}
		} catch (IllegalArgumentException e) {
			return Response.status(Status.BAD_REQUEST).entity(Map.of("error", "Argumento inválido: " + e.getMessage()))
					.build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage())).build();
		}
	}

	/**
	 * Método que crea un nuevo grupo.
	 * 
	 * @author jpribio - 16/02/25
	 * @param grupoACrear DTO con la información del grupo a crear.
	 * @return Response con el resultado de la operación.
	 */
	@PostMapping("/CrearGrupoComoAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearUnGrupoComAdmin(@RequestBody GruposDto grupoACrear) {
		if (grupoACrear == null) {
			return Response.status(Status.BAD_REQUEST).entity(Map.of("error", "El grupo a crear no puede ser nulo."))
					.build();
		}

		try {
			// Llamada a la lógica de negocio o servicio que realiza la creación del grupo.
			boolean creado = serviciosPerfil.crearGrupoComoAdministrador(grupoACrear);

			if (creado) {
				return Response.ok(Map.of("message", "Grupo creado correctamente.")).build();
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(Map.of("error", "No se pudo crear el grupo.")).build();
			}
		} catch (IllegalArgumentException e) {
			return Response.status(Status.BAD_REQUEST).entity(Map.of("error", "Argumento inválido: " + e.getMessage()))
					.build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage())).build();
		}
	}

	/**
	 * Método auxiliar que convierte el DTO de usuario en un Map para la respuesta.
	 * 
	 * @author jpribio - 13/02/25
	 * @param dto
	 * @return
	 */
	private Map<String, Object> convertirUsuarioDtoAMap(UsuarioPerfilDto dto) {
		Map<String, Object> response = new HashMap<>();
		response.put("idUsu", dto.getIdUsu());
		response.put("nombreCompletoUsu", dto.getNombreCompletoUsu());
		response.put("aliasUsu", dto.getAliasUsu());
		response.put("correoElectronicoUsu", dto.getCorreoElectronicoUsu());
		response.put("movilUsu", dto.getMovilUsu());
		// Manejo de la foto: si es null, se devuelve un array vacío
		response.put("fotoString", dto.getFotoUsu() != null ? dto.getFotoUsu() : new byte[0]);
		response.put("esPremium", dto.getEsPremium());
		response.put("rolUsu", dto.getRolUsu());
		response.put("esVerificadoEntidad", dto.getEsVerificadoEntidad());
		return response;
	}
}
