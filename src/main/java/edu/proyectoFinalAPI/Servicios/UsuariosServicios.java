package edu.proyectoFinalAPI.Servicios;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.proyectoFinalAPI.Daos.UsuarioEntidad;
import edu.proyectoFinalAPI.Daos.UsuarioRepositorio;
import edu.proyectoFinalAPI.Dtos.UsuarioDto;
import edu.proyectoFinalAPI.Dtos.UsuarioPerfilDto;
import edu.proyectoFinalAPI.utilidades.*;

/**
 * Clase donde se encuentra todos los metodos en relacion con el usuario
 * 
 * @author jpribio - 19/01/25
 */
@Service
public class UsuariosServicios {

	/*
	 * Inicializa la utilizacion de los metodos util
	 *
	 */
	private Util metodosDeUtilidad = new Util();

	/**
	 * Método que llama a usuarioRepositorio que contiene por lo que se va a buscar
	 * a los usuarios.
	 * 
	 * @author jpribio - 19/01/25
	 */
	@Autowired
	private UsuarioRepositorio repositorioUsuario;

	public UsuariosServicios(UsuarioRepositorio repositorioUsuario) {
		this.repositorioUsuario = repositorioUsuario;

	}

	/**
	 * Metodo donde se crea un nuevo usuario en el registro
	 * 
	 * @author jpribio - 19/01/25
	 * @param nuevoUsuarioDatos
	 * @throws NoSuchAlgorithmException
	 */
	public UsuarioPerfilDto nuevoUsuario(UsuarioDto nuevoUsuarioDatos)
			throws NullPointerException, IllegalArgumentException {

		// Verificar si el usuario ya existe en la base de datos
		if (repositorioUsuario.findByCorreoElectronicoUsuEntidad(nuevoUsuarioDatos.getCorreoElectronicoUsu()) != null) {
			throw new IllegalArgumentException("El usuario ya existe.");
		}

		// Crear nuevo usuario
		UsuarioEntidad usuario = new UsuarioEntidad();
		usuario.setNombreCompletoUsuEntidad(nuevoUsuarioDatos.getNombreCompletoUsu());
		usuario.setAliasUsuEntidad(nuevoUsuarioDatos.getAliasUsu());
		usuario.setCorreoElectronicoUsuEntidad(nuevoUsuarioDatos.getCorreoElectronicoUsu());
		usuario.setContraseniaUsuEntidad(metodosDeUtilidad.encriptarASHA256(nuevoUsuarioDatos.getContraseniaUsu()));
		usuario.setRolUsuEntidad(nuevoUsuarioDatos.getRolUsu());
		usuario.setEsPremiumEntidad(nuevoUsuarioDatos.getEsPremiumB());
		usuario.setEsVerificadoEntidad(nuevoUsuarioDatos.getEsVerificadoEntidad());

		// Guardar usuario en la base de datos y devolver DTO
		return devolverInformacionUsuarioPerfil(repositorioUsuario.save(usuario));
	}

	/**
	 * Metodo que verifica el inicio de sesion del usuario
	 * 
	 * @param verificarUsu
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public UsuarioPerfilDto inicioSesionUsu(UsuarioDto verificarUsu) {
		if (verificarUsu == null || verificarUsu.getCorreoElectronicoUsu() == null
				|| verificarUsu.getContraseniaUsu() == null) {
			throw new IllegalArgumentException("Datos de usuario incompletos.");
		}

		// Buscar el usuario en la base de datos
		UsuarioEntidad usuarioEnt = repositorioUsuario
				.findByCorreoElectronicoUsuEntidad(verificarUsu.getCorreoElectronicoUsu());

		if (usuarioEnt == null) {
			// Si el usuario no es encontrado, retornar null
			return null;
		}

		// Verificar la contraseña
		String contrasenaEncriptada = metodosDeUtilidad.encriptarASHA256(verificarUsu.getContraseniaUsu());
		if (!usuarioEnt.getContraseniaUsuEntidad().equals(contrasenaEncriptada)) {
			// Si la contraseña es incorrecta, retornar null
			return null;
		}

		// Si todo es correcto, devolver el perfil del usuario
		return devolverInformacionUsuarioPerfil(usuarioEnt);
	}

	/**
	 * Metodo privado que devuelve la informacion necesaria para mostrar el perfil
	 * del usuario
	 * 
	 * @author jpribio - 21/01/25
	 * @param nuevoUsuarioGuardado entidad que se pasa a DTO
	 * @return devuelve la informacion en dto
	 */
	private UsuarioPerfilDto devolverInformacionUsuarioPerfil(UsuarioEntidad nuevoUsuarioGuardado) {
		UsuarioPerfilDto usuarioPerfil = new UsuarioPerfilDto();
		usuarioPerfil.setIdUsu(nuevoUsuarioGuardado.getIdUsuEntidad());
		usuarioPerfil.setNombreCompletoUsu(nuevoUsuarioGuardado.getNombreCompletoUsuEntidad());
		usuarioPerfil.setAliasUsu(nuevoUsuarioGuardado.getAliasUsuEntidad());
		usuarioPerfil.setCorreoElectronicoUsu(nuevoUsuarioGuardado.getCorreoElectronicoUsuEntidad());
		usuarioPerfil.setMovilUsu(nuevoUsuarioGuardado.getMovilUsuEntidad());
		usuarioPerfil.setEsPremium(nuevoUsuarioGuardado.getEsPremiumEntidad());
		usuarioPerfil.setEsVerificadoEntidad(nuevoUsuarioGuardado.getEsVerificadoEntidad());
		usuarioPerfil.setRolUsu(nuevoUsuarioGuardado.getRolUsuEntidad());
		// usuarioPerfil.setFotoUsu(nuevoUsuarioGuardado.getFotoUsuEntidad());
		return usuarioPerfil;
	}
}
