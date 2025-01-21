package edu.proyectoFinalAPI.Dtos;

/**
 * Clase donde se encuentra los atributos de los tipos en las categorias y
 * subcategoria
 * 
 * @author jpribio - 21/01/25
 */
public class TiposDto {

	private Long idTipo;
	private String nombreTipo = "aaaaa";
	private String descripcionTipo = "aaaaa";
	private int nivelTipo = 0;

	public Long getIdTipo() {
		return idTipo;
	}

	public String getNombreTipo() {
		return nombreTipo;
	}

	public String getDescripcionTipo() {
		return descripcionTipo;
	}

	public int getNivelTipo() {
		return nivelTipo;
	}

}
