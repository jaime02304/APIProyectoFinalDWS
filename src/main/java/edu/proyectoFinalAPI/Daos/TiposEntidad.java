package edu.proyectoFinalAPI.Daos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Informacion o atributos de las categorias y subcategorias
 * 
 * @author jpribio - 21/01/25
 */
@Entity
@Table(name = "tipos")
public class TiposEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo")
	private Long idTipo;

	@Column(name = "nombre_tipo")
	private String nombreTipo = "aaaaa";

	@Column(name = "descripcion_tipo")
	private String descripcionTipo = "aaaaa";

	@Column(name = "nivel_tipo")
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
