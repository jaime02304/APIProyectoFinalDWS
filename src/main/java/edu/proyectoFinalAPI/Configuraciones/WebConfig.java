package edu.proyectoFinalAPI.Configuraciones;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
				// Aplica CORS a todas las rutas de tu API
				.addMapping("/**")

				// Orígenes permitidos (ajusta a tus dominios)
				.allowedOrigins("http://127.0.0.1:8081")

				// Métodos HTTP permitidos: sólo GET y POST
				.allowedMethods("GET", "POST")

				// Cabeceras que el cliente puede enviar
				.allowedHeaders("Content-Type", "Authorization")

				// Expone estas cabeceras al cliente
				.exposedHeaders("X-Total-Count", "Link")

				// Si necesitas cookies o Authorization, habilita:
				.allowCredentials(true)

				// Tiempo en segundos que el navegador cachea la respuesta al preflight
				.maxAge(3600);
	}
}
