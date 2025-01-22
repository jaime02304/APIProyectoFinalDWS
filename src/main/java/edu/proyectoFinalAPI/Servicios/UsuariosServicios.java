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

		UsuarioEntidad usuarioEnt = repositorioUsuario
				.findByCorreoElectronicoUsuEntidad(nuevoUsuarioDatos.getCorreoElectronicoUsu());

		if (usuarioEnt == null) {
			UsuarioEntidad usuario = new UsuarioEntidad();
			usuario.setNombreCompletoUsuEntidad(nuevoUsuarioDatos.getNombreCompletoUsu());
			usuario.setAliasUsuEntidad(nuevoUsuarioDatos.getAliasUsu());
			usuario.setCorreoElectronicoUsuEntidad(nuevoUsuarioDatos.getCorreoElectronicoUsu());
			usuario.setContraseniaUsuEntidad(metodosDeUtilidad.encriptarASHA256(nuevoUsuarioDatos.getContraseniaUsu()));
			usuario.setRolUsuEntidad(nuevoUsuarioDatos.getRolUsu());
			usuario.setEsPremiumEntidad(nuevoUsuarioDatos.getEsPremiumB());

			// Guarda el nuevo usuario
			UsuarioEntidad nuevoUsuarioGuardado = repositorioUsuario.save(usuario);
			UsuarioPerfilDto usuarioPerfil = devolverInformacionUsuarioPerfil(nuevoUsuarioGuardado);
			return usuarioPerfil;
		} else {
			System.err.println("Usuario encontrado");
		}
		return null;
	}

	/**
	 * Metodo que verifica el inicio de sesion del usuario
	 * 
	 * @param verificarUsu
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public UsuarioPerfilDto inicioSesionUsu(UsuarioDto verificarUsu)
			throws NullPointerException, IllegalArgumentException {
		System.out.println(verificarUsu.getCorreoElectronicoUsu());
		UsuarioEntidad usuarioEnt = repositorioUsuario
				.findByCorreoElectronicoUsuEntidad(verificarUsu.getCorreoElectronicoUsu());

		if (usuarioEnt != null) {
			System.out.println("Usuario-->" + usuarioEnt.getCorreoElectronicoUsuEntidad() + " encontrado");
			if (usuarioEnt.getContraseniaUsuEntidad()
					.equals(metodosDeUtilidad.encriptarASHA256(verificarUsu.getContraseniaUsu()))) {
				UsuarioPerfilDto usuarioPerfilDto = devolverInformacionUsuarioPerfil(usuarioEnt);
				return usuarioPerfilDto;
			} else {
				System.err.println("Contraseña erronea");
			}
		} else {
			System.err.println("Usuario no encontrado");
		}
		return null;
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
		usuarioPerfil.setNombreCompletoUsu(nuevoUsuarioGuardado.getNombreCompletoUsuEntidad());
		usuarioPerfil.setAliasUsu(nuevoUsuarioGuardado.getAliasUsuEntidad());
		usuarioPerfil.setCorreoElectronicoUsu(nuevoUsuarioGuardado.getCorreoElectronicoUsuEntidad());
		usuarioPerfil.setMovilUsu(nuevoUsuarioGuardado.getMovilUsuEntidad());
		usuarioPerfil.setFotoUsu(nuevoUsuarioGuardado.getFotoUsuEntidad());
		return usuarioPerfil;
	}
}
