package edu.proyectoFinalAPI.Dtos;

import java.time.LocalDateTime;

/**
 * Clase donde se encuentra los atributos de los usuarios
 * 
 * @author jpribio - 20/01/25
 */
public class GruposDtos {

	private Long idGrupo;
	private String nombreGrupo = "aaaaa";
	private Long creadorUsuId;
	private Long numeroUsuarios = (long) 0;
	private LocalDateTime fechaGrupo = LocalDateTime.now();
	private Long categoriaIdLong;
	private Long subCategoriaIdLong ;
	
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

	
}
