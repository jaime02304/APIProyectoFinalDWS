package edu.proyectoFinalAPI.Servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.proyectoFinalAPI.Daos.GrupoEntidad;
import edu.proyectoFinalAPI.Daos.GruposRepositorio;
import edu.proyectoFinalAPI.Daos.SuscripcionEntidad;
import edu.proyectoFinalAPI.Daos.SuscripcionRepositorio;
import edu.proyectoFinalAPI.Daos.UsuarioEntidad;
import edu.proyectoFinalAPI.Daos.UsuarioRepositorio;
import edu.proyectoFinalAPI.Dtos.GrupoEspecificadoDto;
import edu.proyectoFinalAPI.Dtos.GruposDto;
import edu.proyectoFinalAPI.Dtos.GruposParaLasListasDto;
import edu.proyectoFinalAPI.Dtos.SuscripcionDto;
import edu.proyectoFinalAPI.Dtos.UsuarioPerfilDto;
import edu.proyectoFinalAPI.Dtos.UsuariodeGruposDto;

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

	@Autowired
	private SuscripcionRepositorio repositorioSuscripcion;

	@Autowired
	private UsuarioRepositorio repositorioUSuario;

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
		return gruposE.reversed().stream().map(grupoEntidad -> {
			GruposParaLasListasDto grupo = new GruposParaLasListasDto();
			grupo.setIdGrupo(grupoEntidad.getIdGrupo());
			grupo.setNombreGrupo(grupoEntidad.getNombreGrupo());
			grupo.setCategoriaNombre(grupoEntidad.getCategoriaId().getNombreCategoria());
			grupo.setSubCategoriaNombre(grupoEntidad.getSubCategoriaId().getNombreSubcategoria());
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
			grupo.setCategoriaNombre(grupoEntidad.getCategoriaId().getNombreCategoria());
			grupo.setSubCategoriaNombre(grupoEntidad.getSubCategoriaId().getNombreSubcategoria());
			grupo.setDescripcionGrupo(grupoEntidad.getDescripcionGrupo());
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
			grupo.setCategoriaNombre(grupoEntidad.getCategoriaId().getNombreCategoria());
			grupo.setSubCategoriaNombre(grupoEntidad.getSubCategoriaId().getNombreSubcategoria());
			grupo.setDescripcionGrupo(grupoEntidad.getDescripcionGrupo());
			return grupo;
		}).collect(Collectors.toList());
	}

	/**
	 * Metodo para recoger todo el listado de comentarios
	 * 
	 * @author jpribio - 30/04/25
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public List<GruposDto> recogerTodosLosGrupos() throws NullPointerException, IllegalArgumentException {

		List<GrupoEntidad> listaEnt = repositorioGrupos.findAllByOrderByNumeroUsuariosDesc();
		List<GruposDto> listaDto = new ArrayList<>();

		if (listaEnt != null) {
			for (GrupoEntidad ent : listaEnt) {
				GruposDto dto = new GruposDto();
				dto.setIdGrupo(ent.getIdGrupo());
				dto.setNombreGrupo(ent.getNombreGrupo());
				dto.setCreadorUsuId(ent.getCreadorUsuId().getIdUsuEntidad());
				dto.setAliasCreadorUString(ent.getCreadorUsuId().getAliasUsuEntidad());
				dto.setNumeroUsuarios(ent.getNumeroUsuarios());
				dto.setFechaGrupo(ent.getFechaGrupo());
				if (ent.getCategoriaId() != null) {
					dto.setCategoriaNombre(ent.getCategoriaId().getNombreCategoria());
				} else {
					dto.setCategoriaNombre("");
				}
				if (ent.getSubCategoriaId() != null) {
					dto.setSubCategoriaNombre(ent.getSubCategoriaId().getNombreSubcategoria());
				} else {
					dto.setSubCategoriaNombre("");
				}

				dto.setDescripcionGrupo(ent.getDescripcionGrupo());

				listaDto.add(dto);
			}
			return listaDto;
		}

		return null;
	}

	/**
	 * Metodo que me devuelve toda la informacion encesaria del grupo especificado
	 * 
	 * @author jpribio - 7/05/25
	 * @param grupo
	 * @return
	 */
	public GrupoEspecificadoDto grupoEspecificado(GrupoEspecificadoDto grupo) {
		GrupoEntidad grupoEntidad = repositorioGrupos.findByNombreGrupo(grupo.getNombreGrupo());

		List<UsuarioEntidad> listadoDeUsuariosEnElGrupo = repositorioSuscripcion
				.findUsuariosByNombreGrupo(grupo.getNombreGrupo());

		List<UsuariodeGruposDto> usuariosDto = listadoDeUsuariosEnElGrupo.stream().map(usuario -> {
			UsuariodeGruposDto dto = new UsuariodeGruposDto();
			dto.setNombreUsuario(usuario.getNombreCompletoUsuEntidad().split(" ")[0]);
			dto.setAliasUsuario(usuario.getAliasUsuEntidad());
			dto.setEsPremium(usuario.getEsPremiumEntidad());
			return dto;
		}).collect(Collectors.toList());

		// Construir el DTO de salida
		GrupoEspecificadoDto grupoEspecificado = new GrupoEspecificadoDto();
		grupoEspecificado.setGrupoId(grupoEntidad.getIdGrupo());
		grupoEspecificado.setUsuarioId(grupoEntidad.getCreadorUsuId().getIdUsuEntidad());
		grupoEspecificado.setNombreGrupo(grupoEntidad.getNombreGrupo());
		grupoEspecificado.setAliasCreadorGrupo(grupoEntidad.getCreadorUsuId().getAliasUsuEntidad());
		grupoEspecificado.setFechaCreacion(grupoEntidad.getFechaGrupo());
		grupoEspecificado.setDescripcionGrupoString(grupoEntidad.getDescripcionGrupo());
		grupoEspecificado.setCategoriaGrupo(grupoEntidad.getCategoriaId().getNombreCategoria());
		grupoEspecificado.setSubcategoriaGrupo(grupoEntidad.getSubCategoriaId().getNombreSubcategoria());
		grupoEspecificado.setNumeroUsuarios((long) usuariosDto.size());
		grupoEspecificado.setListadoDeUsuariosSuscritos(usuariosDto);
		return grupoEspecificado;
	}

	/**
	 * MEtodo para suscribirse al grupo deseado
	 * 
	 * @author jpribio - 7/05/25
	 * @param elementosNecesariosParaUnirme
	 * @return
	 */
	public String suscribirmeAlGrupo(SuscripcionDto elementosNecesariosParaUnirme) {
		try {
			GrupoEntidad grupoEntidad = repositorioGrupos
					.findByNombreGrupo(elementosNecesariosParaUnirme.getNombreGrupo());
			if (grupoEntidad == null) {
				return "Error: El grupo especificado no existe.";
			}

			UsuarioEntidad usuarioEnt = repositorioUSuario
					.findByAliasUsuEntidad(elementosNecesariosParaUnirme.getAliasUsuario());
			if (usuarioEnt == null) {
				return "Error: El usuario especificado no existe.";
			}

			boolean yaSuscrito = repositorioSuscripcion.existsByGrupoIdAndUsuarioId(grupoEntidad, usuarioEnt);
			if (yaSuscrito) {
				return "El usuario ya está suscrito a este grupo.";
			}

			SuscripcionEntidad susEntidad = new SuscripcionEntidad();
			susEntidad.setGrupoId(grupoEntidad);
			susEntidad.setUsuarioId(usuarioEnt);
			repositorioSuscripcion.save(susEntidad);

			Long actual = grupoEntidad.getNumeroUsuarios();
			grupoEntidad.setNumeroUsuarios(actual != null ? actual + 1 : 1);
			repositorioGrupos.save(grupoEntidad);

			return "Se ha realizado todo correctamente.";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error inesperado al procesar la suscripción: " + e.getMessage();
		}
	}

	/**
	 * MEtodo para abandonar al grupo deseado
	 * 
	 * @author jpribio - 7/05/25
	 * @param elementosNecesariosParaUnirme
	 * @return
	 */
	public String eliminarSuscripcionDelGrupo(SuscripcionDto elementosNecesarios) {
		try {
			GrupoEntidad grupo = repositorioGrupos.findByNombreGrupo(elementosNecesarios.getNombreGrupo());
			if (grupo == null) {
				return "Error: El grupo especificado no existe.";
			}

			UsuarioEntidad usuario = repositorioUSuario.findByAliasUsuEntidad(elementosNecesarios.getAliasUsuario());
			if (usuario == null) {
				return "Error: El usuario especificado no existe.";
			}

			Optional<SuscripcionEntidad> suscripcionOpt = repositorioSuscripcion.findByGrupoIdAndUsuarioId(grupo,
					usuario);
			if (suscripcionOpt.isEmpty()) {
				return "El usuario no está suscrito a este grupo.";
			}

			repositorioSuscripcion.delete(suscripcionOpt.get());
			Long actual = grupo.getNumeroUsuarios();
			if (actual != null && actual > 0) {
				grupo.setNumeroUsuarios(actual - 1);
				repositorioGrupos.save(grupo); // Guardar cambio
			}

			return "La suscripción ha sido eliminada correctamente.";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error inesperado al eliminar la suscripción: " + e.getMessage();
		}
	}

}
