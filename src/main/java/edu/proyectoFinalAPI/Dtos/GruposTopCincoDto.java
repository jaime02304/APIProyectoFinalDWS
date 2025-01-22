package edu.proyectoFinalAPI.Dtos;

/**
 * Atributos de los grupos para tener lo necesario de los 5 con mas usuarios
 * 
 * @author jpribio - 21/01/25
 */
public class GruposTopCincoDto {

	private Long idGrupo;
	private String nombreGrupo = "aaaaa";
	private Long categoriaId;
	private String categoriaNombre = "aaaaa";
	private int categoriaNivel = 0;
	private Long subCategoriaId;
	private String subCategoriaNombre = "aaaaa";
	private int subCategoriaNivel = 0;

	// No se sabe si se debe de poner l entidad (el tipo) ahí

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

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Long getSubCategoriaId() {
		return subCategoriaId;
	}

	public void setSubCategoriaId(Long subCategoriaId) {
		this.subCategoriaId = subCategoriaId;
	}

	public String getCategoriaNombre() {
		return categoriaNombre;
	}

	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
	}

	public int getCategoriaNivel() {
		return categoriaNivel;
	}

	public void setCategoriaNivel(int categoriaNivel) {
		this.categoriaNivel = categoriaNivel;
	}

	public String getSubCategoriaNombre() {
		return subCategoriaNombre;
	}

	public void setSubCategoriaNombre(String subCategoriaNombre) {
		this.subCategoriaNombre = subCategoriaNombre;
	}

	public int getSubCategoriaNivel() {
		return subCategoriaNivel;
	}

	public void setSubCategoriaNivel(int subCategoriaNivel) {
		this.subCategoriaNivel = subCategoriaNivel;
	}

	public GruposTopCincoDto() {
		super();
	}

}
