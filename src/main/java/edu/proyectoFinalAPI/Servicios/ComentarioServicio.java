
package edu.proyectoFinalAPI.Servicios;

import java.util.ArrayList;
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
	public List<ComentariosIndexDto> recogerComentariosParaElIndex()
			throws NullPointerException, IllegalArgumentException {
		List<ComentariosEntidad> listaComentarioEnt = repositorioComentariorepositorio.findByCategoriaId();
		List<ComentariosIndexDto> listaComentariosIndex = new ArrayList<ComentariosIndexDto>();
		if (listaComentarioEnt != null) {
			for (ComentariosEntidad comentario : listaComentarioEnt) {
				ComentariosIndexDto comentarioDto = new ComentariosIndexDto();
				comentarioDto.setAliasUsuarioComentario(comentario.getUsuarioId().getAliasUsuEntidad());
				comentarioDto.setComentarioTexto(comentario.getComentarioTexto());
				comentarioDto.setMeGustaComentarios(comentario.getMeGusta()); //
				if (comentario.getUsuarioId().getFotoUsuEntidad() != null) {
					comentarioDto.setImagenUsuario(comentario.getUsuarioId().getFotoUsuEntidad());
				} else {
					comentarioDto.setImagenUsuario(null);
				}
				listaComentariosIndex.add(comentarioDto);
			}
			return listaComentariosIndex;
		}
		return null;
	}

}
