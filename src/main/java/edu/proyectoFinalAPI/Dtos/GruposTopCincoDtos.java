package edu.proyectoFinalAPI.Dtos;

/**
 * Atributos de los grupos para tener lo necesario de los 5 con mas usuarios
 * 
 * @author jpribio - 21/01/25
 */
public class GruposTopCincoDtos {

	private Long idGrupo;
	private String nombreGrupo = "aaaaa";
	private Long categoriaIdLong;
	private Long subCategoriaIdLong;

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

	public Long getCategoriaIdLong() {
		return categoriaIdLong;
	}

	public void setCategoriaIdLong(Long categoriaIdLong) {
		this.categoriaIdLong = categoriaIdLong;
	}

	public Long getSubCategoriaIdLong() {
		return subCategoriaIdLong;
	}

	public void setSubCategoriaIdLong(Long subCategoriaIdLong) {
		this.subCategoriaIdLong = subCategoriaIdLong;
	}

	public GruposTopCincoDtos() {
		super();
	}

}
