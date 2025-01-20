package edu.proyectoFinalAPI.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.proyectoFinalAPI.Daos.UsuarioEntidad;
import edu.proyectoFinalAPI.Dtos.UsuarioDto;
import edu.proyectoFinalAPI.Servicios.usuariosImplementacion;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Controlador principal de la APi donde tiene las rutas de los metodos de la
 * misma
 * 
 * @author jpribio - 18/01/25
 */

@RestController
@RequestMapping("/apiUsuario")
public class controladorApi {

	@Autowired
	private usuariosImplementacion servicioUsuario;

	/**
	 * Metodo que se enuentra el registro
	 * 
	 * @author jpribio - 19/01/25
	 * @param usuario (informacion necesaria para el registro)
	 * @return
	 */
	@PostMapping("/registro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registroAPi(@RequestBody UsuarioDto usuario) {
		try {
			servicioUsuario.nuevoUsuario(usuario);
			return Response.ok().entity("El usuario se ha registrado correctamente").build();
		} catch (NullPointerException nE) {
			// Manejo específico para errores de tipo NullPointerException
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Error: Algunos campos del usuario están vacíos o nulos.").build();
		} catch (IllegalArgumentException iaE) {
			// Manejo específico para errores de argumentos inválidos
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Error: Los datos proporcionados no son válidos.").build();
		} catch (Exception e) {
			// Manejo genérico para cualquier otra excepción
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error inesperado: " + e.getMessage())
					.build();
		}
	}

	/**
	 * Metodo que se encuentra el inicio de sesion
	 * 
	 * @author jpribio - 19/01/25
	 * @param usuario (informacion para el inicio de sesion)
	 * @return
	 */
	@GetMapping("/inicioSesion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> inicioSesionUsuario(@RequestBody UsuarioDto usuario) {
		Map<String, Object> response = new HashMap<>();

		try {
			// Llamada al servicio para verificar el inicio de sesión
			UsuarioEntidad usuarioEntidad = servicioUsuario.inicioSesionUsu(usuario);

			if (usuarioEntidad != null) {
				response.put("correoUsu", usuarioEntidad.getCorreoElectronicoUsuEntidad());
				response.put("contraseniaUsu", usuarioEntidad.getContraseniaUsuEntidad());
				response.put("success", true);
			} else {
				response.put("success", false);
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
