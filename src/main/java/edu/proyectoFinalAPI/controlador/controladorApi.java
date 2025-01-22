package edu.proyectoFinalAPI.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.proyectoFinalAPI.Daos.UsuarioEntidad;
import edu.proyectoFinalAPI.Dtos.GruposTopCincoDto;
import edu.proyectoFinalAPI.Dtos.UsuarioDto;
import edu.proyectoFinalAPI.Dtos.UsuarioPerfilDto;
import edu.proyectoFinalAPI.Servicios.GrupoServicios;
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
	public Map<String, Object> registroAPi(@RequestBody UsuarioDto usuario) {
		Map<String, Object> response = new HashMap<>();
		try {
			if (usuario != null) {
				UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.nuevoUsuario(usuario);
				if (usuarioPerfilDto != null) {
					response.put("usuario", usuarioPerfilDto);
					response.put("success", true);
					response.put("encontrado", false);
				} else {
					response.put("success", true);
					response.put("encontrado", true);
				}
			} else {
				response.put("success", false);
			}
		} catch (IllegalArgumentException iaE) {
			// Manejo de la excepción IllegalArgumentException
			response.put("success", false);
			response.put("error", " Los datos proporcionados no son válidos." + iaE.getMessage());
		} catch (NullPointerException nE) {
			// Manejo de la excepción NullPointerException
			response.put("success", false);
			response.put("error", "Algunos campos del usuario están vacíos o nulos. " + nE.getMessage());
		} catch (Exception e) {
			// Manejo de cualquier otra excepción
			response.put("success", false);
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
	@GetMapping("/usuario/inicioSesion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> inicioSesionUsuario(@RequestBody UsuarioDto usuario) {
		Map<String, Object> response = new HashMap<>();

		try {
			// Llamada al servicio para verificar el inicio de sesión
			UsuarioPerfilDto usuarioPerfilDto = servicioUsuario.inicioSesionUsu(usuario);

			if (usuarioPerfilDto != null) {
				response.put("usuario", usuarioPerfilDto);
				response.put("success", true);
				response.put("encontrado", true);
			} else {
				response.put("success", false);
				response.put("encontrado", false);
			}
		} catch (IllegalArgumentException iaE) {
			// Manejo de la excepción IllegalArgumentException
			response.put("success", false);
			response.put("error", "Argumento inválido: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			// Manejo de la excepción NullPointerException
			response.put("success", false);
			response.put("error", "Se produjo un error debido a un valor nulo: " + nE.getMessage());
		} catch (Exception e) {
			// Manejo de cualquier otra excepción
			response.put("success", false);
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
			// Llamada al servicio para verificar el inicio de sesión
			List<GruposTopCincoDto> listadoGrupo = servicioGrupo.recogerLosGruposMasTop();

			if (listadoGrupo != null) {
				response.put("grupos", listadoGrupo);
				response.put("success", true);
				response.put("encontrado", true);
			} else {
				response.put("success", false);
				response.put("encontrado", false);
			}
		} catch (IllegalArgumentException iaE) {
			// Manejo de la excepción IllegalArgumentException
			response.put("success", false);
			response.put("error", "Argumento inválido: " + iaE.getMessage());
		} catch (NullPointerException nE) {
			// Manejo de la excepción NullPointerException
			response.put("success", false);
			response.put("error", "Se produjo un error debido a un valor nulo: " + nE.getMessage());
		} catch (Exception e) {
			// Manejo de cualquier otra excepción
			response.put("success", false);
			response.put("error", "Ocurrió un error inesperado: " + e.getMessage());
		}

		return response;
	}

}
