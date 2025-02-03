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
import edu.proyectoFinalAPI.Dtos.GruposTopCincoDto;
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
		Map<String, Object> response = new HashMap<>();

		if (usuario == null) {
			response.put("error", "El usuario no puede ser nulo.");
			return response;
		}

		try {
			UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.nuevoUsuario(usuario);

			if (usuarioPerfilDto != null) {
				response.put("idUsu", usuarioPerfilDto.getIdUsu());
				response.put("nombreCompletoUsu", usuarioPerfilDto.getNombreCompletoUsu());
				response.put("aliasUsu", usuarioPerfilDto.getAliasUsu());
				response.put("correoElectronicoUsu", usuarioPerfilDto.getCorreoElectronicoUsu());
				response.put("movilUsu", usuarioPerfilDto.getMovilUsu());
				response.put("fotoUsu", usuarioPerfilDto.getFotoUsu());
				response.put("esPremium", usuarioPerfilDto.getEsPremium());
				response.put("rolUsu", usuarioPerfilDto.getRolUsu());
				response.put("esVerificadoEntidad", usuarioPerfilDto.getEsVerificadoEntidad());
			} else {
				response.put("error", "No se pudo crear el usuario.");
			}
		} catch (IllegalArgumentException iaE) {
			response.put("error", "Datos proporcionados no válidos: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			response.put("error", "Algunos campos están vacíos o nulos: " + nE.getMessage());
		} catch (Exception e) {
			response.put("error", "Error inesperado: " + e.getMessage());
		}

		return response;
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
		Map<String, Object> response = new HashMap<>();

		if (usuario == null) {
			response.put("error", "El usuario no puede ser nulo.");
			return response;
		}

		try {
			// Llamada al servicio para verificar el inicio de sesión
			UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.inicioSesionUsu(usuario);

			if (usuarioPerfilDto != null) {
				response.put("idUsu", usuarioPerfilDto.getIdUsu());
				response.put("nombreCompletoUsu", usuarioPerfilDto.getNombreCompletoUsu());
				response.put("aliasUsu", usuarioPerfilDto.getAliasUsu());
				response.put("correoElectronicoUsu", usuarioPerfilDto.getCorreoElectronicoUsu());
				response.put("movilUsu", usuarioPerfilDto.getMovilUsu());
				response.put("fotoUsu", usuarioPerfilDto.getFotoUsu());
				response.put("esPremium", usuarioPerfilDto.getEsPremium());
				response.put("rolUsu", usuarioPerfilDto.getRolUsu());
				response.put("esVerificadoEntidad", usuarioPerfilDto.getEsVerificadoEntidad());
			} else {
				response.put("error", "Usuario no encontrado.");
			}
		} catch (IllegalArgumentException iaE) {
			// Manejo de la excepción IllegalArgumentException
			response.put("error", "Argumento inválido: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			// Manejo de la excepción NullPointerException
			response.put("error", "Se produjo un error debido a un valor nulo: " + nE.getMessage());
		} catch (Exception e) {
			// Manejo de cualquier otra excepción
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

	@GetMapping("/index/grupos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> obtenerGruposMasNumerosos() {
		Map<String, Object> response = new HashMap<>();

		try {
			// Llamada al servicio para obtener los 5 grupos más populares
			List<GruposTopCincoDto> listadoGrupo = servicioGrupo.recogerLosGruposMasTop();

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
}
