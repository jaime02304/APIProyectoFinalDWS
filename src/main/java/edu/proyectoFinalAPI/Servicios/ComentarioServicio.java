
package edu.proyectoFinalAPI.Servicios;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.proyectoFinalAPI.Daos.ComentarioRepositorio;
import edu.proyectoFinalAPI.Daos.ComentariosEntidad;
import edu.proyectoFinalAPI.Dtos.ComentariosDto;
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
				byte[] fotoBytes = comentario.getUsuarioId().getFotoUsuEntidad();
				if (fotoBytes != null && fotoBytes.length > 0) {
					String imagenUsuarioBase64 = Base64.getEncoder().encodeToString(fotoBytes);
					comentarioDto.setImagenUsuario(imagenUsuarioBase64);
				} else {
					comentarioDto.setImagenUsuario(null);
				}
				listaComentariosIndex.add(comentarioDto);
			}
			return listaComentariosIndex;
		}
		return null;
	}

	/**
	 * Metod que recoge todos los comentarios para la seccion de comentarios
	 * 
	 * @author jpribio - 16/04/25
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public List<ComentariosDto> recogerTodosLosComentarios() throws NullPointerException, IllegalArgumentException {
		List<ComentariosEntidad> listaComentariosEnt = repositorioComentariorepositorio
				.findAllByOrderByFechaComentarioDesc();
		List<ComentariosDto> listaComentariosIndex = new ArrayList<>();
		if (listaComentariosEnt != null) {
			for (ComentariosEntidad comentario : listaComentariosEnt) {
				ComentariosDto comentarioDto = new ComentariosDto();
				comentarioDto.setAliasUsuarioComentario(comentario.getUsuarioId().getAliasUsuEntidad());
				comentarioDto.setComentarioTexto(comentario.getComentarioTexto());
				comentarioDto.setIdUsuario(comentario.getUsuarioId().getIdUsuEntidad());
				comentarioDto.setCategoriaTipo(comentario.getCategoriaId().getNombreCategoria());
				comentarioDto.setSubCategoriaTipo(comentario.getSubCategoriaId().getNombreSubcategoria());
				if (comentario.getGrupoId() != null) {
					comentarioDto.setGrupoComentario(comentario.getGrupoId().getNombreGrupo());
				} else {
					comentarioDto.setGrupoComentario("");
				}
				listaComentariosIndex.add(comentarioDto);
			}
			return listaComentariosIndex;
		}
		return null;
	}

}
