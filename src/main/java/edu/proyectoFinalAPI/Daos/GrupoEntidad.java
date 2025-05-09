package edu.proyectoFinalAPI.Daos;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

	@Column(name = "nombre_grupo", unique = true)
	private String nombreGrupo = "aaaaa";

	@ManyToOne
	@JoinColumn(name = "creador_usu_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UsuarioEntidad creadorUsuId;

	@Column(name = "numero_usuarios")
	private Long numeroUsuarios = (long) 0;

	@Column(nullable = false, name = "fecha_grupo")
	private LocalDateTime fechaGrupo = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private CategoriaEntidad categoriaId;

	@ManyToOne
	@JoinColumn(name = "subcategoria_id")
	private SubcategoriaEntidad subCategoriaId;

	@Column(name = "descripcion")
	private String descripcionGrupo = "";

	public String getDescripcionGrupo() {
		return descripcionGrupo;
	}

	public void setDescripcionGrupo(String descripcionGrupo) {
		this.descripcionGrupo = descripcionGrupo;
	}


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

	public CategoriaEntidad getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(CategoriaEntidad categoriaId) {
		this.categoriaId = categoriaId;
	}

	public SubcategoriaEntidad getSubCategoriaId() {
		return subCategoriaId;
	}

	public void setSubCategoriaId(SubcategoriaEntidad subCategoriaId) {
		this.subCategoriaId = subCategoriaId;
	}

}
