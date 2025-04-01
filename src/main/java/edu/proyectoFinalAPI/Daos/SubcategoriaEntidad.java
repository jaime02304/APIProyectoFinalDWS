package edu.proyectoFinalAPI.Daos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase donde se encuentra los atributos de la tabla categoria
 * 
 * @author jpribio - 01/04/2025
 */

@Entity
@Table(name = "subcategoria")
public class SubcategoriaEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_subcategoria")
	private Long idSubcategoria;

	@Column(name = "nombre_subcategoria")
	private String nombreSubcategoria = "aaaaa";

	@Column(name = "descripcion_subcategoria")
	private String descripcionSubcategoria = "aaaaa";

	public Long getIdSubcategoria() {
		return idSubcategoria;
	}

	public void setIdSubcategoria(Long idSubcategoria) {
		this.idSubcategoria = idSubcategoria;
	}

	public String getNombreSubcategoria() {
		return nombreSubcategoria;
	}

	public void setNombreSubcategoria(String nombreSubcategoria) {
		this.nombreSubcategoria = nombreSubcategoria;
	}

	public String getDescripcionSubcategoria() {
		return descripcionSubcategoria;
	}

	public void setDescripcionSubcategoria(String descripcionSubcategoria) {
		this.descripcionSubcategoria = descripcionSubcategoria;
	}

}
