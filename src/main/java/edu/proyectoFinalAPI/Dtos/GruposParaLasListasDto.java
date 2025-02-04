package edu.proyectoFinalAPI.Dtos;

/**
 * Atributos de los grupos para tener lo necesario de los 5 con mas usuarios
 * 
 * @author jpribio - 21/01/25
 */
public class GruposParaLasListasDto {

	private Long idGrupo;
	private String nombreGrupo = "aaaaa";
	private String categoriaNombre = "aaaaa";
	private String subCategoriaNombre = "aaaaa";

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

	public String getCategoriaNombre() {
		return categoriaNombre;
	}

	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
	}

	public String getSubCategoriaNombre() {
		return subCategoriaNombre;
	}

	public void setSubCategoriaNombre(String subCategoriaNombre) {
		this.subCategoriaNombre = subCategoriaNombre;
	}

	public GruposParaLasListasDto() {
		super();
	}

}
