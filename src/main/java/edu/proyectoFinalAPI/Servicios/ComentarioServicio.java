package edu.proyectoFinalAPI.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.proyectoFinalAPI.Daos.ComentarioRepositorio;
import edu.proyectoFinalAPI.Daos.ComentariosEntidad;
import edu.proyectoFinalAPI.Dtos.ComentariosIndexDto;

/**
 * Clase donde se encuentra los metodos en relacion a los comentarios
 * 
 * @author jpribio - 22/01/25
 */
@Service
public class ComentarioServicio {

	/**
	 * Nueva instancia del repositorios de los comentarios
	 */
	@Autowired
	private ComentarioRepositorio repositorioComentariorepositorio;

	public ComentarioServicio(ComentarioRepositorio repositorioComentariorepositorio) {
		this.repositorioComentariorepositorio = repositorioComentariorepositorio;

	}

	/**
	 * Metodo que recoge los ultimos comentarios sin tematicas
	 * 
	 * @author jpribio - 22/01/25
	 */
	public List<ComentariosIndexDto> recogerComentariosParaElIndex() {
		List<ComentariosEntidad> comentarioEnt = repositorioComentariorepositorio.findByCategoriaId();
		for(ComentariosEntidad comentario: comentarioEnt) {
			ComentariosIndexDto comentarioDto = new ComentariosIndexDto();
			comentarioDto.setAliasUsuarioComentario(comentario.getUsuarioId().getAliasUsuEntidad());
			comentarioDto.setComentarioTexto(comentario.getComentarioTexto());
			comentarioDto.setMeGustaComentarios(comentario.getMeGusta());
			comentarioDto.setImagenUsuario(comentario.getUsuarioId().getFotoUsuEntidad());
		}
		return null;
	}

}
