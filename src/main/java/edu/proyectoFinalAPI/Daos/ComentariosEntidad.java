
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
 * Clase donde se encuentra los Daos de los comentarios
 * 
 * @author jpribio - 22/01/25
 */
@Entity
@Table(name = "comentarios")
public class ComentariosEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comentarios")
	private Long idComentario;

	@Column(name = "comentario")
	private String comentarioTexto = "aaaaa";

	@Column(name = "fecha_comentario")
	private LocalDateTime fechaComentario = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UsuarioEntidad usuarioId;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private CategoriaEntidad categoriaId;

	@ManyToOne
	@JoinColumn(name = "subcategoria_id")
	private SubcategoriaEntidad subCategoriaId;

	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private GrupoEntidad grupoId;

	@Column(name = "me_gusta_comentario")
	private Long meGusta = (long) 0;

	public GrupoEntidad getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(GrupoEntidad grupoId) {
		this.grupoId = grupoId;
	}

	public Long getMeGusta() {
		return meGusta;
	}

	public void setMeGusta(Long meGusta) {
		this.meGusta = meGusta;
	}

	public Long getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(Long idComentario) {
		this.idComentario = idComentario;
	}

	public String getComentarioTexto() {
		return comentarioTexto;
	}

	public void setComentarioTexto(String comentarioTexto) {
		this.comentarioTexto = comentarioTexto;
	}

	public UsuarioEntidad getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(UsuarioEntidad usuarioId) {
		this.usuarioId = usuarioId;
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

	public LocalDateTime getFechaComentario() {
		return fechaComentario;
	}

	public void setFechaComentario(LocalDateTime fechaComentario) {
		this.fechaComentario = fechaComentario;
	}

	public ComentariosEntidad() {
		super();
	}

}
