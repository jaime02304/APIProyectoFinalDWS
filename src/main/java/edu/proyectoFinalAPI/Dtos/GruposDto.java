package edu.proyectoFinalAPI.Dtos;

import java.time.LocalDateTime;

/**
 * Clase donde se encuentra los atributos de los usuarios
 * 
 * @author jpribio - 20/01/25
 */
public class GruposDto {

	private Long idGrupo;
	private String nombreGrupo = "aaaaa";
	private Long creadorUsuId;
	private String aliasCreadorUString = "aaaaa";
	private Long numeroUsuarios = (long) 0;
	private LocalDateTime fechaGrupo = LocalDateTime.now();
	private String categoriaNombre = "aaaaa";
	private String subCategoriaNombre = "aaaaa";

	// No se si se deberia de cambiar por los tipos entidad paara tener la
	// informacion

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

	public Long getCreadorUsuId() {
		return creadorUsuId;
	}

	public void setCreadorUsuId(Long creadorUsuId) {
		this.creadorUsuId = creadorUsuId;
	}

	public Long getNumeroUsuarios() {
		return numeroUsuarios;
	}

	public void setNumeroUsuarios(Long numeroUsuarios) {
		this.numeroUsuarios = numeroUsuarios;
	}

	public LocalDateTime getFechaGrupo() {
		return fechaGrupo;
	}

	public void setFechaGrupo(LocalDateTime fechaGrupo) {
		this.fechaGrupo = fechaGrupo;
	}

	public String getAliasCreadorUString() {
		return aliasCreadorUString;
	}

	public void setAliasCreadorUString(String aliasCreadorUString) {
		this.aliasCreadorUString = aliasCreadorUString;
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

	// Constructor
	public GruposDto() {
		super();
	}

}
