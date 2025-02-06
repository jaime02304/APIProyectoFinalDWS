package edu.proyectoFinalAPI.Servicios;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.proyectoFinalAPI.Daos.GrupoEntidad;
import edu.proyectoFinalAPI.Daos.GruposRepositorio;
import edu.proyectoFinalAPI.Dtos.GruposParaLasListasDto;
import edu.proyectoFinalAPI.Dtos.UsuarioPerfilDto;

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
	public List<GruposParaLasListasDto> recogerLosGruposMasTop() {
		// Obtener los 5 grupos más populares por número de usuarios
		List<GrupoEntidad> gruposE = repositorioGrupos.findTop5GroupsByNumeroUsuariosDesc();

		// Si no se encuentran grupos, retornar una lista vacía en lugar de null
		if (gruposE == null || gruposE.isEmpty()) {
			return Collections.emptyList(); // Mejor retornar una lista vacía en vez de null
		}

		// Mapear los grupos a la lista de DTOs
		return gruposE.stream().map(grupoEntidad -> {
			GruposParaLasListasDto grupo = new GruposParaLasListasDto();
			grupo.setIdGrupo(grupoEntidad.getIdGrupo());
			grupo.setNombreGrupo(grupoEntidad.getNombreGrupo());
			grupo.setCategoriaNombre(grupoEntidad.getCategoriaId().getNombreTipo());
			grupo.setSubCategoriaNombre(grupoEntidad.getSubCategoriaId().getNombreTipo());
			return grupo;
		}).collect(Collectors.toList());
	}

	/**
	 * Metodo que busca el listado de grupos creado por un usuario en especifico
	 * 
	 * @author jpribio - 04/02/25
	 * @param usuarioEspecificado
	 * @return
	 */
	public List<GruposParaLasListasDto> recogerLosGruposDelUsuario(UsuarioPerfilDto usuarioEspecificado) {
		// Obtener los 5 grupos más populares por número de usuarios
		List<GrupoEntidad> gruposE = repositorioGrupos
				.findAllGroupsByUserEmail(usuarioEspecificado.getCorreoElectronicoUsu());

		// Si no se encuentran grupos, retornar una lista vacía en lugar de null
		if (gruposE == null || gruposE.isEmpty()) {
			return Collections.emptyList(); // Mejor retornar una lista vacía en vez de null
		}

		// Mapear los grupos a la lista de DTOs
		return gruposE.stream().map(grupoEntidad -> {
			GruposParaLasListasDto grupo = new GruposParaLasListasDto();
			grupo.setIdGrupo(grupoEntidad.getIdGrupo());
			grupo.setNombreGrupo(grupoEntidad.getNombreGrupo());
			grupo.setCategoriaNombre(grupoEntidad.getCategoriaId().getNombreTipo());
			grupo.setSubCategoriaNombre(grupoEntidad.getSubCategoriaId().getNombreTipo());
			return grupo;
		}).collect(Collectors.toList());
	}

	/**
	 * Metodo que recoge todos los grupos para el que lo vea los administradores
	 * 
	 * @author jpribio - 06/02/25
	 * @return
	 */
	public List<GruposParaLasListasDto> recogerLosGrupos() {
		// Obtener los 5 grupos más populares por número de usuarios
		List<GrupoEntidad> gruposE = repositorioGrupos.findAll();

		// Si no se encuentran grupos, retornar una lista vacía en lugar de null
		if (gruposE == null || gruposE.isEmpty()) {
			return Collections.emptyList(); // Mejor retornar una lista vacía en vez de null
		}

		// Mapear los grupos a la lista de DTOs
		return gruposE.stream().map(grupoEntidad -> {
			GruposParaLasListasDto grupo = new GruposParaLasListasDto();
			grupo.setIdGrupo(grupoEntidad.getIdGrupo());
			grupo.setNombreGrupo(grupoEntidad.getNombreGrupo());
			grupo.setCategoriaNombre(grupoEntidad.getCategoriaId().getNombreTipo());
			grupo.setSubCategoriaNombre(grupoEntidad.getSubCategoriaId().getNombreTipo());
			return grupo;
		}).collect(Collectors.toList());
	}

}
