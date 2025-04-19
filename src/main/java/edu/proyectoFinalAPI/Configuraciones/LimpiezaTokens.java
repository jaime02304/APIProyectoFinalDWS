package edu.proyectoFinalAPI.Configuraciones;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.proyectoFinalAPI.Daos.TokenRepositorio;

/**
 * Clase donde se encuentra los metodos para la limpieza de los tokens
 */
@Component
public class LimpiezaTokens {
	@Autowired
	private TokenRepositorio repositorioToken;

	@Scheduled(cron = "0 0 3 * * ?")
	public void eliminarToken() {
		repositorioToken.deleteByFechaExpiracionBefore(LocalDateTime.now());
	}

}
