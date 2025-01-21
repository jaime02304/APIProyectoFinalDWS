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
	private UsuarioDto creadorUsuId;
	private Long numeroUsuarios = (long) 0;
	private LocalDateTime fechaGrupo = LocalDateTime.now();
	private TiposDto categoriaId;
	private TiposDto subCategoriaId;

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

	public UsuarioDto getCreadorUsuId() {
		return creadorUsuId;
	}

	public void setCreadorUsuId(UsuarioDto creadorUsuId) {
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

	public TiposDto getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(TiposDto categoriaId) {
		this.categoriaId = categoriaId;
	}

	public TiposDto getSubCategoriaId() {
		return subCategoriaId;
	}

	public void setSubCategoriaId(TiposDto subCategoriaId) {
		this.subCategoriaId = subCategoriaId;
	}

	// Constructor
	public GruposDtos() {
		super();
	}

}
