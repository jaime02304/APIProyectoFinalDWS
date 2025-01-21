package edu.proyectoFinalAPI.Dtos;

import edu.proyectoFinalAPI.Daos.TiposEntidad;

/**
 * Atributos de los grupos para tener lo necesario de los 5 con mas usuarios
 * 
 * @author jpribio - 21/01/25
 */
public class GruposTopCincoDtos {

	private Long idGrupo;
	private String nombreGrupo = "aaaaa";
	private TiposEntidad categoriaId;
	private TiposEntidad subCategoriaId;
	
	//No se sabe si se debe de poner l entidad (el tipo) ahí

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public TiposEntidad getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(TiposEntidad categoriaId) {
		this.categoriaId = categoriaId;
	}

	public TiposEntidad getSubCategoriaId() {
		return subCategoriaId;
	}

	public void setSubCategoriaId(TiposEntidad subCategoriaId) {
		this.subCategoriaId = subCategoriaId;
	}

	public GruposTopCincoDtos() {
		super();
	}

}
