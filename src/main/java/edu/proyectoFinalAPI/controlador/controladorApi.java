package edu.proyectoFinalAPI.controlador;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.proyectoFinalAPI.Daos.ComentariosEntidad;
import edu.proyectoFinalAPI.Daos.GrupoEntidad;
import edu.proyectoFinalAPI.Daos.TokenEntidad;
import edu.proyectoFinalAPI.Daos.TokenRepositorio;
import edu.proyectoFinalAPI.Daos.UsuarioEntidad;
import edu.proyectoFinalAPI.Daos.UsuarioRepositorio;
import edu.proyectoFinalAPI.Dtos.ComentariosDto;
import edu.proyectoFinalAPI.Dtos.ComentariosIndexDto;
import edu.proyectoFinalAPI.Dtos.ComentariosPerfilDto;
import edu.proyectoFinalAPI.Dtos.EliminarElementoPerfilDto;
import edu.proyectoFinalAPI.Dtos.GrupoEspecificadoDto;
import edu.proyectoFinalAPI.Dtos.GruposDto;
import edu.proyectoFinalAPI.Dtos.GruposParaLasListasDto;
import edu.proyectoFinalAPI.Dtos.SuscripcionDto;
import edu.proyectoFinalAPI.Dtos.UsuarioDto;
import edu.proyectoFinalAPI.Dtos.UsuarioPerfilDto;
import edu.proyectoFinalAPI.Servicios.ComentarioServicio;
import edu.proyectoFinalAPI.Servicios.GrupoServicios;
import edu.proyectoFinalAPI.Servicios.PerfilServicios;
import edu.proyectoFinalAPI.Servicios.UsuariosServicios;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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

	@Autowired
	private UsuarioRepositorio repositorioUsuario;
	@Autowired
	private TokenRepositorio repositorioToken;

	private static final Logger logger = LoggerFactory.getLogger(controladorApi.class);

	/**
	 * Método que devuelve la lista de todos los alias existentes
	 * 
	 * @author jpribio - 26/04/25
	 * @return lista de alias
	 */
	@GetMapping("/usuario/alias")
	public List<String> obtenerTodosLosAlias() {
		return repositorioUsuario.obtenerTodosAlias();
	}

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
		logger.info("Inicio del registro de usuario: {}", usuario);
		try {
			UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.nuevoUsuario(usuario);
			if (usuarioPerfilDto != null) {
				logger.info("Usuario creado exitosamente: {}", usuarioPerfilDto);
				return convertirUsuarioDtoAMap(usuarioPerfilDto);
			} else {
				logger.warn("Registro fallido: No se pudo crear el usuario (resultado nulo).");
				return Map.of("error", "No se pudo crear el usuario.");
			}
		} catch (IllegalArgumentException iaE) {
			logger.warn("Registro fallido por datos no válidos: {}", iaE.getMessage(), iaE);
			return Map.of("error", "Datos proporcionados no válidos: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			logger.warn("Registro fallido: Algunos campos están vacíos o nulos: {}", nE.getMessage(), nE);
			return Map.of("error", "Algunos campos están vacíos o nulos: " + nE.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado durante el registro del usuario: {}", e.getMessage(), e);
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
		logger.info("Inicio de sesión solicitado para el usuario: {}", usuario);
		try {
			// Llamada al servicio para verificar el inicio de sesión
			UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.inicioSesionUsu(usuario);

			if (usuarioPerfilDto != null) {
				logger.info("Inicio de sesión exitoso para el usuario: {}", usuarioPerfilDto);
				return convertirUsuarioDtoAMap(usuarioPerfilDto);
			} else {
				logger.warn("Inicio de sesión fallido: Usuario no encontrado para {}", usuario);
				return Map.of("error", "Usuario no encontrado.");
			}
		} catch (IllegalArgumentException iaE) {
			logger.warn("Inicio de sesión fallido por argumento inválido: {}", iaE.getMessage(), iaE);
			return Map.of("error", "Argumento inválido: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			logger.warn("Inicio de sesión fallido debido a un valor nulo: {}", nE.getMessage(), nE);
			return Map.of("error", "Se produjo un error debido a un valor nulo: " + nE.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado durante el inicio de sesión: {}", e.getMessage(), e);
			return Map.of("error", "Ocurrió un error inesperado: " + e.getMessage());
		}
	}

	/**
	 * Metodo para hacer el registro/login de google
	 * 
	 * @author jpribio - 26/04/25
	 * @param usuario
	 * @return
	 */
	@PostMapping("/usuario/googleLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> loginRegistroGoogle(@RequestBody UsuarioDto usuario) {
		logger.info("Login/Registro mediante Google para usuario: {}", usuario);
		try {
			// Primero, verificamos si el usuario ya existe por su correo
			UsuarioEntidad usuarioExistente = repositorioUsuario
					.findByCorreoElectronicoUsuEntidad(usuario.getCorreoElectronicoUsu());

			if (usuarioExistente != null) {
				logger.info("Usuario ya existe. Procediendo a iniciar sesión: {}", usuarioExistente);
				// El usuario existe, intentamos iniciar sesión
				UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.inicioSesionUsu(usuario);
				return convertirUsuarioDtoAMap(usuarioPerfilDto);
			} else {
				logger.info("Usuario no encontrado. Procediendo a registrar nuevo usuario: {}", usuario);
				// El usuario no existe, registramos uno nuevo
				UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.nuevoUsuario(usuario);
				return convertirUsuarioDtoAMap(usuarioPerfilDto);
			}
		} catch (IllegalArgumentException iaE) {
			logger.warn("Fallo en login/registro de Google por argumento inválido: {}", iaE.getMessage(), iaE);
			return Map.of("error", "Argumento inválido: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			logger.warn("Fallo en login/registro de Google debido a un valor nulo: {}", nE.getMessage(), nE);
			return Map.of("error", "Valor nulo detectado: " + nE.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado durante el login/registro de Google: {}", e.getMessage(), e);
			return Map.of("error", "Error inesperado: " + e.getMessage());
		}
	}

	/**
	 * Trata de un metodo donde verifica el usuario con el token para verificar el
	 * perfil de dicho usuario
	 * 
	 * @author jpribio - 19/04/25
	 * @param token
	 * @return
	 */
	@GetMapping("/usuario/verificar")
	public ResponseEntity<String> verificar(@RequestParam String token) {
		TokenEntidad tokenEntidad = repositorioToken.findByToken(token);
		if (tokenEntidad == null)
			return ResponseEntity.status(404).body("Token inválido.");
		if (tokenEntidad.isUsado())
			return ResponseEntity.badRequest().body("Token ya usado.");
		if (tokenEntidad.getFechaExpiracion().isBefore(LocalDateTime.now()))
			return ResponseEntity.badRequest().body("Token expirado.");

		UsuarioEntidad usuario = tokenEntidad.getUsuario();
		usuario.setEsVerificadoEntidad(true);
		tokenEntidad.setUsado(true);
		repositorioUsuario.save(usuario);
		repositorioToken.delete(tokenEntidad);

		return ResponseEntity.ok("Usuario verificado correctamente.");
	}

	/**
	 * Metodo que recibe el correo electronico para poder mandar el email a ese
	 * correo para el cambio de contraseña
	 * 
	 * @author jpribio - 21/04/25
	 * @param datos
	 * @return
	 */
	@PostMapping("/usuario/recuperarContrasena")
	public ResponseEntity<String> recuperarContrasena(@RequestBody Map<String, String> datos) {
		String correo = datos.get("correo");

		if (correo == null || correo.isBlank()) {
			return ResponseEntity.badRequest().body("El correo es obligatorio.");
		}

		boolean enviado = servicioUsuario.iniciarRecuperacion(correo);

		if (enviado) {
			return ResponseEntity.ok("Correo de recuperación enviado.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado o error al procesar.");
		}
	}

	/**
	 * Metodo que cambia a la nueva contraseña el usuario y borra el token para
	 * dejarlo inutilizado
	 * 
	 * @author jpribio - 21/04/25
	 * @param datos
	 * @return
	 */
	@PostMapping("/usuario/cambiarContrasena")
	public ResponseEntity<String> cambiarContrasena(@RequestBody Map<String, String> datos) {
		String token = datos.get("token");
		String nuevaContrasena = datos.get("nuevaContrasena");

		boolean cambiado = servicioUsuario.cambiarContrasenaConToken(token, nuevaContrasena);

		if (cambiado) {
			return ResponseEntity.ok("Contraseña cambiada exitosamente.");
		} else {
			return ResponseEntity.badRequest().body("Token inválido, expirado o ya usado.");
		}
	}

	/**
	 * Metodo que muestra los grupos top en el index
	 * 
	 * @author jpribio - 21/04/25
	 * @return
	 */
	@GetMapping("/index/grupos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> obtenerGruposMasNumerosos() {
		Map<String, Object> response = new HashMap<>();
		logger.info("Solicitud para obtener los grupos más numerosos recibida.");
		try {
			// Llamada al servicio para obtener los 5 grupos más populares
			List<GruposParaLasListasDto> listadoGrupo = servicioGrupo.recogerLosGruposMasTop();

			if (listadoGrupo == null || listadoGrupo.isEmpty()) {
				logger.warn("No se encontraron grupos al consultar los más numerosos.");
				response.put("grupos", Collections.emptyList());
			} else {
				logger.info("Grupos obtenidos exitosamente. Cantidad: {}", listadoGrupo.size());
				response.put("grupos", listadoGrupo);
			}
		} catch (Exception e) {
			logger.error("Error inesperado al obtener los grupos: {}", e.getMessage(), e);
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
		logger.info("Solicitud para obtener comentarios de bienvenida recibida.");
		try {
			List<ComentariosIndexDto> listadoComentarios = servicioComentarios.recogerComentariosParaElIndex();

			if (listadoComentarios == null || listadoComentarios.isEmpty()) {
				logger.warn("No se encontraron comentarios de bienvenida.");
				response.put("mensaje", "No se encuentra ningún comentario de bienvenida");
			} else {
				logger.info("Comentarios obtenidos exitosamente. Cantidad: {}", listadoComentarios.size());
				response.put("comentarios", listadoComentarios);
			}
		} catch (IllegalArgumentException iaE) {
			logger.warn("Error en argumentos: {}", iaE.getMessage(), iaE);
			response.put("error", "Argumento inválido: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			logger.warn("Error debido a valor nulo: {}", nE.getMessage(), nE);
			response.put("error", "Se produjo un error debido a un valor nulo: " + nE.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al obtener comentarios: {}", e.getMessage(), e);
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
		logger.info("Solicitud para obtener comentario del perfil del usuario: {}", perfilUsuario);

		try {
			ComentariosPerfilDto comentariosPerfil = serviciosPerfil.obteneroComentarioDelPerfil(perfilUsuario);

			if (comentariosPerfil == null) {
				logger.warn("No se encontró ningún comentario para el perfil del usuario: {}", perfilUsuario);
				response.put("mensaje", "No se encuentra ningún comentario de bienvenida del usuario");
			} else {
				logger.info("Comentario obtenido exitosamente para el perfil del usuario: {}", perfilUsuario);
				response.put("comentarios", comentariosPerfil);
			}
		} catch (IllegalArgumentException iaE) {
			logger.warn("Error al obtener comentario del perfil, argumento inválido: {}", iaE.getMessage(), iaE);
			response.put("error", "Argumento inválido: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			logger.warn("Error al obtener comentario del perfil, valor nulo: {}", nE.getMessage(), nE);
			response.put("error", "Se produjo un error debido a un valor nulo: " + nE.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al obtener comentario del perfil: {}", e.getMessage(), e);
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
	public Map<String, Object> obtenerGruposDelUSuario(@RequestBody UsuarioPerfilDto usuarioParaFiltrar) {
		Map<String, Object> response = new HashMap<>();
		logger.info("Solicitud para obtener los grupos del usuario: {}", usuarioParaFiltrar);
		try {
			List<GruposParaLasListasDto> listadoGrupo = servicioGrupo.recogerLosGruposDelUsuario(usuarioParaFiltrar);

			if (listadoGrupo == null || listadoGrupo.isEmpty()) {
				logger.warn("El usuario {} no tiene grupos asociados.", usuarioParaFiltrar);
				response.put("gruposPerfil", Collections.emptyList());
			} else {
				logger.info("Se encontraron {} grupos para el usuario {}.", listadoGrupo.size(), usuarioParaFiltrar);
				response.put("gruposPerfil", listadoGrupo);
			}
		} catch (Exception e) {
			logger.error("Error inesperado al obtener grupos del usuario: {}", e.getMessage(), e);
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
		logger.info("Solicitud para obtener todos los grupos.");
		try {
			List<GruposParaLasListasDto> listadoGrupo = servicioGrupo.recogerLosGrupos();

			if (listadoGrupo == null || listadoGrupo.isEmpty()) {
				logger.warn("No se encontraron grupos en la base de datos.");
				response.put("gruposPerfil", Collections.emptyList());
			} else {
				logger.info("Se encontraron {} grupos en la base de datos.", listadoGrupo.size());
				response.put("gruposPerfil", listadoGrupo);
			}
		} catch (Exception e) {
			logger.error("Error inesperado al obtener todos los grupos: {}", e.getMessage(), e);
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
		logger.info("Solicitud para obtener todos los usuarios con rol 'User'.");
		try {
			List<UsuarioPerfilDto> listadoUsuarios = serviciosPerfil.obtenerLosUsuariosAdmin();

			if (listadoUsuarios == null || listadoUsuarios.isEmpty()) {
				logger.warn("No se encontraron usuarios con rol 'User' en la base de datos.");
				response.put("usuarioPerfil", Collections.emptyList());
			} else {
				logger.info("Se encontraron {} usuarios con rol 'User'.", listadoUsuarios.size());
				response.put("usuarioPerfil", listadoUsuarios);
			}
		} catch (Exception e) {
			logger.error("Error inesperado al obtener los usuarios con rol 'User': {}", e.getMessage(), e);
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
		logger.info("Solicitud para obtener todos los usuarios.");

		try {
			List<UsuarioPerfilDto> listadoUsuarios = serviciosPerfil.obtenerLosUsuarios();

			if (listadoUsuarios == null || listadoUsuarios.isEmpty()) {
				logger.warn("No se encontraron usuarios en la base de datos.");
				response.put("usuarioPerfil", Collections.emptyList());
			} else {
				logger.info("Se encontraron {} usuarios en la base de datos.", listadoUsuarios.size());
				response.put("usuarioPerfil", listadoUsuarios);
			}
		} catch (Exception e) {
			logger.error("Error inesperado al obtener todos los usuarios: {}", e.getMessage(), e);
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
		logger.info("Solicitud para modificar usuario con ID: {}", usuarioAModificar.getIdUsu());
		try {
			UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.modificarUsuario(usuarioAModificar);
			if (usuarioPerfilDto == null) {
				logger.warn("No se encontró el usuario con ID: {}", usuarioAModificar.getIdUsu());
				return Map.of("error", "Usuario no encontrado.");
			}
			logger.info("Usuario con ID: {} modificado exitosamente.", usuarioAModificar.getIdUsu());
			return convertirUsuarioDtoAMap(usuarioPerfilDto);
		} catch (IllegalArgumentException e) {
			logger.error("Error de argumento inválido al modificar usuario con ID {}: {}", usuarioAModificar.getIdUsu(),
					e.getMessage());
			return Map.of("error", "Argumento inválido: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al modificar usuario con ID {}: {}", usuarioAModificar.getIdUsu(),
					e.getMessage(), e);
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
	public Map<String, Object> eliminarElementoComoAdmin(@RequestBody EliminarElementoPerfilDto elementoAEliminar) {
		Map<String, Object> respuesta = new HashMap<>();
		logger.info("Solicitud para eliminar el elemento: {}", elementoAEliminar);
		try {
			boolean eliminado = serviciosPerfil.eliminarElemento(elementoAEliminar);
			respuesta.put("message",
					eliminado ? "Elemento eliminado correctamente." : "No se pudo eliminar el elemento.");
			if (eliminado) {
				logger.info("Elemento eliminado correctamente: {}", elementoAEliminar);
			} else {
				logger.warn("No se pudo eliminar el elemento: {}", elementoAEliminar);
			}
		} catch (IllegalArgumentException e) {
			logger.error("Error de argumento inválido al eliminar elemento: {}", e.getMessage());
			respuesta.put("error", "Argumento inválido: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al eliminar elemento: {}", e.getMessage(), e);
			respuesta.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return respuesta;
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
	public Map<String, String> modificarUsuarioComoAdministrador(@RequestBody UsuarioPerfilDto usuarioAModificar) {
		Map<String, String> respuesta = new HashMap<>();
		logger.info("Solicitud para modificar usuario como administrador: {}", usuarioAModificar);
		try {
			boolean modificado = serviciosPerfil.modificarUsuarioComoAdministrador(usuarioAModificar);
			respuesta.put("message",
					modificado ? "Usuario modificado correctamente." : "No se pudo modificar el usuario.");
			if (modificado) {
				logger.info("Usuario modificado correctamente: {}", usuarioAModificar);
			} else {
				logger.warn("No se pudo modificar el usuario: {}", usuarioAModificar);
			}
		} catch (IllegalArgumentException e) {
			logger.error("Error de argumento inválido al modificar usuario: {}", e.getMessage());
			respuesta.put("error", "Argumento inválido: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al modificar usuario: {}", e.getMessage(), e);
			respuesta.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}
		return respuesta;
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
	public Map<String, Object> modificarGrupoComoAdministrador(@RequestBody GruposParaLasListasDto grupoAModificar) {
		Map<String, Object> respuesta = new HashMap<>();
		logger.info("Solicitud para modificar grupo como administrador: {}", grupoAModificar);
		try {
			// Llamada a la lógica de negocio o servicio que realiza la modificación.
			boolean modificado = serviciosPerfil.modificarGrupoComoAdministrador(grupoAModificar);
			respuesta.put("message", modificado ? "Grupo modificado correctamente." : "No se pudo modificar el grupo.");
			if (modificado) {
				logger.info("Grupo modificado correctamente: {}", grupoAModificar);
			} else {
				logger.warn("No se pudo modificar el grupo: {}", grupoAModificar);
			}
		} catch (IllegalArgumentException e) {
			logger.error("Error de argumento inválido al modificar grupo: {}", e.getMessage());
			respuesta.put("error", "Argumento inválido: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al modificar grupo: {}", e.getMessage(), e);
			respuesta.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return respuesta;
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
	public Map<String, Object> crearUnUsuarioComAdmin(@RequestBody UsuarioDto usuarioACrear) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			logger.info("Solicitud para crear un usuario como administrador: {}", usuarioACrear);
			boolean creado = serviciosPerfil.crearUsuarioComoAdministrador(usuarioACrear);
			respuesta.put("message", creado ? "Usuario creado correctamente." : "No se pudo crear el usuario.");
			if (creado) {
				logger.info("Usuario creado correctamente: {}", usuarioACrear);
			} else {
				logger.warn("No se pudo crear el usuario: {}", usuarioACrear);
			}
		} catch (IllegalArgumentException e) {
			logger.error("Error de argumento inválido al crear usuario: {}", e.getMessage());
			respuesta.put("error", "Argumento inválido: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al crear usuario: {}", e.getMessage(), e);
			respuesta.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return respuesta;
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
	public Map<String, Object> crearUnGrupoComAdmin(@RequestBody GruposDto grupoACrear) {
		Map<String, Object> response = new HashMap<>();
		logger.info("Solicitud para crear un grupo como administrador: {}", grupoACrear);
		try {
			GrupoEntidad grupoCreado = serviciosPerfil.crearGrupoComoAdministrador(grupoACrear);
			GruposDto grupo = cambiaraDTO(grupoCreado);
			response.put("grupo", grupo);
			logger.info("Grupo creado correctamente: {}", grupo);
		} catch (IllegalArgumentException e) {
			logger.error("Error de argumento inválido al crear grupo: {}", e.getMessage());
			response.put("error", "Argumento inválido: " + e.getMessage());
			response.put("grupo", "");
		} catch (Exception e) {
			logger.error("Error inesperado al crear grupo: {}", e.getMessage(), e);
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
			response.put("grupo", "");
		}
		return response;
	}

	/**
	 * Metodo privado para cambiar de entidad a dto el grupo
	 * 
	 * @author jpribio - 20/02/25
	 * @param grupoCreado
	 * @return
	 */
	private GruposDto cambiaraDTO(GrupoEntidad grupoCreado) {
		GruposDto grupoDto = new GruposDto();
		grupoDto.setIdGrupo(grupoCreado.getIdGrupo());
		grupoDto.setNombreGrupo(grupoCreado.getNombreGrupo());
		grupoDto.setCreadorUsuId(
				grupoCreado.getCreadorUsuId() != null ? grupoCreado.getCreadorUsuId().getIdUsuEntidad() : null);
		grupoDto.setAliasCreadorUString(
				grupoCreado.getCreadorUsuId() != null ? grupoCreado.getCreadorUsuId().getAliasUsuEntidad() : null);
		grupoDto.setNumeroUsuarios(grupoCreado.getNumeroUsuarios());
		grupoDto.setFechaGrupo(grupoCreado.getFechaGrupo());
		grupoDto.setCategoriaNombre(
				grupoCreado.getCategoriaId() != null ? grupoCreado.getCategoriaId().getNombreCategoria() : null);
		grupoDto.setSubCategoriaNombre(
				grupoCreado.getSubCategoriaId() != null ? grupoCreado.getSubCategoriaId().getNombreSubcategoria()
						: null);
		grupoDto.setDescripcionGrupo(grupoCreado.getDescripcionGrupo());
		return grupoDto;
	}

	/**
	 * Metodo que recoge los valores de un comentario e inserta en la base de datos
	 * 
	 * @author jpribio - 20/02/25
	 * @param nuevoComentario
	 * @return
	 */
	@PostMapping("/CrearComentarioPerfil")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> crearComentarioNuevo(@RequestBody ComentariosPerfilDto nuevoComentario) {
		Map<String, Object> response = new HashMap<>();
		logger.info("Solicitud para crear un nuevo comentario de perfil: {}", nuevoComentario);
		try {
			ComentariosEntidad comentarioCreado = serviciosPerfil.crearNuevoComentario(nuevoComentario);
			ComentariosPerfilDto comentarioDto = convertirAComentarioDTO(comentarioCreado);
			response.put("comentario", comentarioDto);
			response.put("message", "Comentario creado correctamente.");
			logger.info("Comentario creado exitosamente: {}", comentarioDto);
		} catch (IllegalArgumentException e) {
			logger.error("Error de argumento inválido al crear comentario: {}", e.getMessage());
			response.put("error", "Argumento inválido: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al crear comentario: {}", e.getMessage(), e);
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	/**
	 * Metodo para recoger todos los comentarios
	 * 
	 * @author jpribio - 16/04/25
	 * @return
	 */
	@GetMapping("/RecogerComentarios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> recogerComentarios() {
		Map<String, Object> response = new HashMap<>();
		logger.info("Recogida de todos los comentarios.");
		try {
			List<ComentariosDto> comentarioDto = servicioComentarios.recogerTodosLosComentarios();
			response.put("listaCompletaComentarios", comentarioDto);
		} catch (IllegalArgumentException e) {
			logger.error("Error de argumento inválido al recoger los comentarios: {}", e.getMessage());
			response.put("error", "Argumento inválido: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al recoger los comentarios: {}", e.getMessage(), e);
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	/**
	 * Metodo para buscar el contenido del grupo a ver
	 * 
	 * @author jpribio - 7/05/25
	 * @param grupoEspecifico
	 * @return
	 */
	@PostMapping("/VerGrupoEspecificado")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> verGrupoEspecificado(@RequestBody GrupoEspecificadoDto grupoEspecifico) {
		Map<String, Object> response = new HashMap<>();
		logger.info("Recogida del grupo especificado.");
		try {
			GrupoEspecificadoDto grupo = servicioGrupo.grupoEspecificado(grupoEspecifico);
			response.put("grupoEspecifico", grupo);
		} catch (IllegalArgumentException e) {
			logger.error("Error de argumento inválido al recoger el grupo: {}", e.getMessage());
			response.put("error", "Argumento inválido: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al recoger el grupo: {}", e.getMessage(), e);
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	/**
	 * MEtodo para unirme al grupo deseado
	 * 
	 * @author jpribio - 7/05/25
	 * @param elementosNecesariosParaUnirme
	 * @return
	 */
	@PostMapping("/UnirmeAlGrupo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> unirmeAlGrupoEspecificado(@RequestBody SuscripcionDto elementosNecesariosParaUnirme) {
		Map<String, Object> response = new HashMap<>();
		logger.info("Suscripcion al grupo.");
		try {
			String mensaje = servicioGrupo.suscribirmeAlGrupo(elementosNecesariosParaUnirme);
			response.put("mensaje", mensaje);
		} catch (IllegalArgumentException e) {
			logger.error("Error de argumento inválido al suscribirse al grupo: {}", e.getMessage());
			response.put("error", "Argumento inválido: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al al suscribirse grupo: {}", e.getMessage(), e);
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	/**
	 * MEtodo para recoger todos los grupos desde la api
	 * 
	 * @author jpribio - 30/04/25
	 * @return
	 */
	@GetMapping("/RecogerGruposTotales")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> recogerTodosLOsGrupos() {
		Map<String, Object> response = new HashMap<>();
		logger.info("Recogida de todos los grupos.");
		try {
			List<GruposDto> gruposDto = servicioGrupo.recogerTodosLosGrupos();
			response.put("listaCompletaGrupos", gruposDto);
		} catch (IllegalArgumentException e) {
			logger.error("Error de argumento inválido al recoger los grupos: {}", e.getMessage());
			response.put("error", "Argumento inválido: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al recoger los grupos: {}", e.getMessage(), e);
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	/**
	 * Método privado para cambiar de entidad a DTO el comentario
	 * 
	 * @param comentarioCreado
	 * @return
	 */
	private ComentariosPerfilDto convertirAComentarioDTO(ComentariosEntidad comentarioCreado) {
		ComentariosPerfilDto comentarioDto = new ComentariosPerfilDto();
		comentarioDto.setComentarioTexto(comentarioCreado.getComentarioTexto());
		comentarioDto.setIdUsuario(comentarioCreado.getUsuarioId().getIdUsuEntidad());
		comentarioDto.setCategoriaTipo(comentarioCreado.getCategoriaId().getNombreCategoria());
		comentarioDto.setSubCategoriaTipo(comentarioCreado.getSubCategoriaId().getNombreSubcategoria());
		return comentarioDto;
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
