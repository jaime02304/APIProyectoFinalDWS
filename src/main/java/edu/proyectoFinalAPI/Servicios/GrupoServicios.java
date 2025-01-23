package edu.proyectoFinalAPI.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.proyectoFinalAPI.Daos.GrupoEntidad;
import edu.proyectoFinalAPI.Daos.GruposRepositorio;
import edu.proyectoFinalAPI.Dtos.GruposTopCincoDto;

/**
 * Clase donde se encuentra los metodos en relacion a los grupos
 * 
 * @author jpribio - 20/01/25
 */
@Service
public class GrupoServicios {

	/**
	 * Método que llama a usuarioRepositorio que contiene por lo que se va a buscar
	 * a los usuarios.
	 * 
	 * @author jpribio - 19/01/25 @
	 */
	@Autowired
	private GruposRepositorio repositorioGrupos;

	public GrupoServicios(GruposRepositorio repositorioGrupos) {
		this.repositorioGrupos = repositorioGrupos;

	}

	/**
	 * Metodo que devuelve los 5 primeros grupos con mas usuarios
	 * 
	 * @author jpribio - 20/01/25
	 * @return devuelve el listado de grupos
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public List<GruposTopCincoDto> recogerLosGruposMasTop() throws NullPointerException, IllegalArgumentException {

		List<GrupoEntidad> gruposE = repositorioGrupos.findTop5GroupsByNumeroUsuariosDesc();
		List<GruposTopCincoDto> grupos = new ArrayList<GruposTopCincoDto>();
		if (gruposE != null) {
			for (GrupoEntidad grupoEntidad : gruposE) {
				GruposTopCincoDto grupo = new GruposTopCincoDto();
				grupo.setIdGrupo(grupoEntidad.getIdGrupo());
				grupo.setNombreGrupo(grupoEntidad.getNombreGrupo());
				// Categoria
				grupo.setCategoriaNombre(grupoEntidad.getCategoriaId().getNombreTipo());
				// Subcategoria
				grupo.setSubCategoriaNombre(grupoEntidad.getSubCategoriaId().getNombreTipo());
				grupos.add(grupo);
			}
			return grupos;
		}
		return null;

	}

}
