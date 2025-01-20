package edu.proyectoFinalAPI.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.proyectoFinalAPI.Daos.UsuarioEntidad;
import edu.proyectoFinalAPI.Daos.UsuarioRepositorio;
import edu.proyectoFinalAPI.Dtos.UsuarioDto;

/**
 * Clase donde se encuentra todos los metodos en relacion con el usuario
 * 
 * @author jpribio - 19/01/25
 */
@Service
public class usuariosImplementacion {

	/**
	 * Método que llama a usuarioRepositorio que contiene por lo que se va a buscar
	 * a los usuarios.
	 * 
	 * @author jpribio - 19/01/25 @
	 */
	@Autowired
	private UsuarioRepositorio repositorioUsuario;

	public usuariosImplementacion(UsuarioRepositorio repositorioUsuario) {
		this.repositorioUsuario = repositorioUsuario;
	}

	/**
	 * Metodo donde se crea un nuevo usuario en el registro
	 * 
	 * @author jpribio - 19/01/25
	 * @param nuevoUsuarioDatos
	 */
	public void nuevoUsuario(UsuarioDto nuevoUsuarioDatos) throws NullPointerException, IllegalArgumentException {

		UsuarioEntidad usuario = new UsuarioEntidad();
		usuario.setNombreCompletoUsuEntidad(nuevoUsuarioDatos.getNombreCompletoUsu());
		usuario.setAliasUsuEntidad(nuevoUsuarioDatos.getAliasUsu());
		usuario.setCorreoElectronicoUsuEntidad(nuevoUsuarioDatos.getCorreoElectronicoUsu());
		usuario.setContraseniaUsuEntidad(nuevoUsuarioDatos.getContraseniaUsu());

		// Guarda el nuevo usuario
		UsuarioEntidad nuevoUsuarioGuardado = repositorioUsuario.save(usuario);
		// Establece un ID al Dto para utilizarlo en el front si fuese necesario
		// nuevoUsuarioDatos.setIdUsu(nuevoUsuarioGuardado.getIdUsuEntidad());
	}

	/**
	 * Metodo que verifica el inicio de sesion del usuario
	 * 
	 * @param verificarUsu
	 * @return
	 */
	public UsuarioEntidad inicioSesionUsu(UsuarioDto verificarUsu)
			throws NullPointerException, IllegalArgumentException {
		System.out.println(verificarUsu.getCorreoElectronicoUsu());
		UsuarioEntidad usuarioEnt = repositorioUsuario
				.findByCorreoElectronicoUsuEntidad(verificarUsu.getCorreoElectronicoUsu());

		if (usuarioEnt != null) {
			System.out.println("Usuario-->" + usuarioEnt.getCorreoElectronicoUsuEntidad() + " encontrado");
			if (usuarioEnt.getContraseniaUsuEntidad().equals(verificarUsu.getContraseniaUsu())) {
				return usuarioEnt;
			} else {
				System.err.println("Contraseña erronea");
			}
		} else {
			System.err.println("Usuario no encontrado");
		}
		return null;
	}
}
