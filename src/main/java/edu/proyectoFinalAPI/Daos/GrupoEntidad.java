package edu.proyectoFinalAPI.Daos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad / Daos de los atribuos de los grupos
 * 
 * @author jpribio - 20/01/25
 */
@Entity
@Table(name = "grupos")
public class GrupoEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_grupo")
	private Long idGrupo;

	@Column(name = "", unique = true)
	private String nombreGrupo = "aaaaa";

	@ManyToOne
	@JoinColumn(name = "creador_usu_id", nullable = false)
	private UsuarioEntidad creadorUsuId;

	@Column(name = "")
	private Long numeroUsuarios = (long) 0;

	@Column(nullable = false, name = "")
	private LocalDateTime fechaGrupo = LocalDateTime.now();

//	@ManyToOne
	@Column(name = "categoria_id")
	private Long categoriaIdLong;

//	@ManyToOne
    @Column(name = "subcategoria_id")
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

	public UsuarioEntidad getCreadorUsuId() {
		return creadorUsuId;
	}

	public void setCreadorUsuId(UsuarioEntidad creadorUsuId) {
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
