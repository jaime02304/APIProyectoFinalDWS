package edu.proyectoFinalAPI.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.proyectoFinalAPI.Daos.ComentarioRepositorio;
import edu.proyectoFinalAPI.Daos.ComentariosEntidad;
import edu.proyectoFinalAPI.Dtos.ComentariosPerfilDto;
import edu.proyectoFinalAPI.Dtos.UsuarioPerfilDto;

/**
 * Clase donde se encuentra todos los metodos que tenga relacion con el perfil
 * 
 * @author jpribio - 02/02/25
 */
@Service
public class PerfilServicios {

	/**
	 * Nueva instancia del repositorios de los comentarios
	 */

	@Autowired
	private ComentarioRepositorio repositorioComentariorepositorio;

	public PerfilServicios(ComentarioRepositorio repositorioComentariorepositorio) {
		super();
		this.repositorioComentariorepositorio = repositorioComentariorepositorio;
	}

	/**
	 * MEtodo que mediante el parametro del correo electronico busca el mensaje del usuario
	 * @author jpribio - 02/02/25
	 * @param PerfilUsuario
	 * @return
	 */
	public ComentariosPerfilDto obteneroComentarioDelPerfil(UsuarioPerfilDto PerfilUsuario) {
		ComentariosEntidad comentarioPerfilE = repositorioComentariorepositorio
				.findLatestByCorreoElectronico(PerfilUsuario.getCorreoElectronicoUsu());
		if (comentarioPerfilE == null) {
			return null;
		}
		ComentariosPerfilDto comentarioPerfilDto = new ComentariosPerfilDto();
		comentarioPerfilDto.setComentarioTexto(comentarioPerfilE.getComentarioTexto());
		comentarioPerfilDto.setCategoriaTipo(comentarioPerfilE.getCategoriaId().getNombreTipo());
		comentarioPerfilDto.setSubCategoriaTipo(comentarioPerfilE.getSubCategoriaId().getNombreTipo());

		return comentarioPerfilDto;

	}

}
