package edu.proyectoFinalAPI.Configuraciones;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.proyectoFinalAPI.Daos.TokenEntidad;
import edu.proyectoFinalAPI.Daos.TokenRepositorio;
import edu.proyectoFinalAPI.Daos.UsuarioEntidad;
import edu.proyectoFinalAPI.Daos.UsuarioRepositorio;

/**
 * Clase donde se encuentra los metodos para la limpieza de los tokens
 */
@Component
public class LimpiezaTokens {
	@Autowired
	private TokenRepositorio repositorioToken;
	@Autowired
	private UsuarioRepositorio repositorioUsuario;

	/**
	 * MEtodo para eliminar los token expirados y los usuarios no validados tras la
	 * expiracion y que sean tokens de validacions
	 * 
	 * @author jpribio - 21/04/25
	 */
	@Scheduled(cron = "0 0 3 * * ?")
	public void eliminarTokensYUsuariosNoVerificados() {
		LocalDateTime ahora = LocalDateTime.now();
		List<TokenEntidad> tokensExpirados = repositorioToken
				.findByVerificacionTrueAndUsadoFalseAndFechaExpiracionBefore(ahora);

		for (TokenEntidad token : tokensExpirados) {
			UsuarioEntidad usuario = token.getUsuario();
			repositorioToken.delete(token);
			if (!usuario.getEsVerificadoEntidad()) {
				repositorioUsuario.delete(usuario);
			}
		}
	}
}
