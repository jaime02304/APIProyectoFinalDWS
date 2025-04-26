package edu.proyectoFinalAPI.Servicios;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.proyectoFinalAPI.Daos.TokenEntidad;
import edu.proyectoFinalAPI.Daos.TokenRepositorio;
import edu.proyectoFinalAPI.Daos.UsuarioEntidad;
import edu.proyectoFinalAPI.Daos.UsuarioRepositorio;
import edu.proyectoFinalAPI.Dtos.UsuarioDto;
import edu.proyectoFinalAPI.Dtos.UsuarioPerfilDto;

/**
 * Clase donde se encuentra todos los metodos en relacion con el usuario
 * 
 * @author jpribio - 19/01/25
 */
@Service
public class UsuariosServicios {
	/**
	 * Método que llama a usuarioRepositorio que contiene por lo que se va a buscar
	 * a los usuarios.
	 * 
	 * @author jpribio - 19/01/25
	 */
	@Autowired
	private UsuarioRepositorio repositorioUsuario;
	@Autowired
	private TokenRepositorio repositorioToken;
	@Autowired
	private EmailServicios servicioEmail;

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

		if (repositorioUsuario.findByCorreoElectronicoUsuEntidad(nuevoUsuarioDatos.getCorreoElectronicoUsu()) != null) {
			throw new IllegalArgumentException("El usuario ya existe.");
		}

		// Mapeo común de DTO a entidad
		UsuarioEntidad usuario = mapearDtoAEntidad(nuevoUsuarioDatos);
		UsuarioEntidad guardado = repositorioUsuario.save(usuario);

		// Procesar registro según tipo de login
		if (nuevoUsuarioDatos.isEsConGoogle()) {
			procesarRegistroGoogle(guardado);
		} else {
			procesarRegistroNormal(guardado);
		}

		return devolverInformacionUsuarioPerfil(guardado);
	}

	/**
	 * Metodo auxiliar para mappear el dto hacia la entidad
	 * 
	 * @author jpribio - 26/04/25
	 * @param dto
	 * @return
	 */
	private UsuarioEntidad mapearDtoAEntidad(UsuarioDto dto) {
		UsuarioEntidad usuario = new UsuarioEntidad();
		usuario.setNombreCompletoUsuEntidad(dto.getNombreCompletoUsu());
		usuario.setAliasUsuEntidad(dto.getAliasUsu());
		usuario.setCorreoElectronicoUsuEntidad(dto.getCorreoElectronicoUsu());
		usuario.setContraseniaUsuEntidad(dto.getContraseniaUsu());
		usuario.setRolUsuEntidad(dto.getRolUsu());
		usuario.setEsPremiumEntidad(dto.getEsPremiumB());
		usuario.setEsLoginDeGoogle(dto.isEsConGoogle());
		usuario.setEsVerificadoEntidad(dto.isEsConGoogle());
		return usuario;
	}

	/**
	 * Metodo que verifica el usuario si el registro es de google
	 * 
	 * @author jpribio - 26/04/25
	 * @param usuario
	 */
	private void procesarRegistroGoogle(UsuarioEntidad usuario) {
		usuario.setEsVerificadoEntidad(true);
		repositorioUsuario.save(usuario);
	}

	/**
	 * Metodo que verifica el usuario si el registro es tradicional
	 * 
	 * @author jpribio - 26/04/25
	 * @param usuario
	 */
	private void procesarRegistroNormal(UsuarioEntidad usuario) {
		String token = UUID.randomUUID().toString();
		TokenEntidad tokenVer = new TokenEntidad();
		tokenVer.setToken(token);
		tokenVer.setCorreoUsuario(usuario.getCorreoElectronicoUsuEntidad());
		tokenVer.setFechaExpiracion(LocalDateTime.now().plusDays(30));
		tokenVer.setUsado(false);
		tokenVer.setUsuario(usuario);
		tokenVer.setVerificacion(true);
		repositorioToken.save(tokenVer);
		// Enviar email de verificación
		servicioEmail.enviarVerificacioEmail(usuario.getCorreoElectronicoUsuEntidad(), token);
	}

	/**
	 * Metodo que verifica el inicio de sesion del usuario
	 * 
	 * @author jpribio - 19/01/25
	 * @param verificarUsu
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public UsuarioPerfilDto inicioSesionUsu(UsuarioDto verificarUsu) {
		if (verificarUsu == null || verificarUsu.getCorreoElectronicoUsu() == null
				|| verificarUsu.getContraseniaUsu() == null) {
			throw new IllegalArgumentException("Datos de usuario incompletos.");
		}
		UsuarioEntidad usuarioEnt = repositorioUsuario
				.findByCorreoElectronicoUsuEntidad(verificarUsu.getCorreoElectronicoUsu());
		if (usuarioEnt == null) {
			return null;
		}
		if (!usuarioEnt.getContraseniaUsuEntidad().equals(verificarUsu.getContraseniaUsu())) {
			return null;
		}
		return devolverInformacionUsuarioPerfil(usuarioEnt);
	}

	/**
	 * Metodo que modifica al usuario con los valores recibidos
	 * 
	 * @author jpribio - 11/02/25
	 * @return devuelve el usuario
	 */
	public UsuarioPerfilDto modificarUsuario(UsuarioPerfilDto usuarioAModificar)
			throws Exception, NullPointerException, IllegalArgumentException {
		if (usuarioAModificar == null) {
			throw new IllegalArgumentException("Datos de usuario incompletos. No hay usuario");
		}
		repositorioUsuario.actualizarUsuarioPorCorreo(usuarioAModificar);
		return Optional
				.ofNullable(repositorioUsuario
						.findByCorreoElectronicoUsuEntidad(usuarioAModificar.getCorreoElectronicoUsu()))
				.map(this::devolverInformacionUsuarioPerfil).orElse(null);
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
		usuarioPerfil.setFotoUsu(nuevoUsuarioGuardado.getFotoUsuEntidad());
		byte[] fotoBytes = nuevoUsuarioGuardado.getFotoUsuEntidad();
		usuarioPerfil.setFotoUsu(fotoBytes);
		if (fotoBytes != null && fotoBytes.length > 0) {
			String fotoBase64 = Base64.getEncoder().encodeToString(fotoBytes);
			usuarioPerfil.setFotoString(fotoBase64);
		} else {
			usuarioPerfil.setFotoString(null);
		}
		return usuarioPerfil;
	}

	/**
	 * MEtodo que inicia la recuperacion para el cambio de contraseña
	 * 
	 * @author jpribio - 21/04/25
	 * @param correo
	 * @return
	 */
	public boolean iniciarRecuperacion(String correo) {
		UsuarioEntidad usuario = repositorioUsuario.findByCorreoElectronicoUsuEntidad(correo);
		if (usuario == null) {
			return false;
		}
		String tokenGenerado = UUID.randomUUID().toString();
		TokenEntidad tokenEntidad = new TokenEntidad();
		tokenEntidad.setToken(tokenGenerado);
		tokenEntidad.setCorreoUsuario(correo);
		tokenEntidad.setFechaExpiracion(LocalDateTime.now().plusHours(1));
		tokenEntidad.setUsado(false);
		tokenEntidad.setVerificacion(false);
		tokenEntidad.setUsuario(usuario);
		repositorioToken.save(tokenEntidad);
		servicioEmail.enviarRecuperacionEmail(correo, tokenGenerado);

		return true;
	}

	/**
	 * Metodo que efectua el cambio de contraseña
	 * 
	 * @author jpribio - 21/04/25
	 * @param token
	 * @param nuevaContrasena
	 * @return
	 */
	public boolean cambiarContrasenaConToken(String token, String nuevaContrasena) {
		TokenEntidad tokenEntidad = repositorioToken.findByToken(token);

		if (tokenEntidad == null || tokenEntidad.getFechaExpiracion().isBefore(LocalDateTime.now())) {
			return false;
		}

		UsuarioEntidad usuario = tokenEntidad.getUsuario();
		usuario.setContraseniaUsuEntidad(nuevaContrasena);
		repositorioUsuario.save(usuario);

		// ❌ Borra el token después de usarlo
		repositorioToken.delete(tokenEntidad);
		return true;
	}
}
