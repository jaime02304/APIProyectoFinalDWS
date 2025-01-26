
package edu.proyectoFinalAPI.Daos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Clase donde se encuentra los Daos de los comentarios
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

	@Lob
	@Column(name = "archivo_comentario")
	private byte[] archivoComentario;

	@Column(name = "fecha_comentario")
	private LocalDateTime fechaComentario = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private UsuarioEntidad usuarioId;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private TiposEntidad categoriaId;

	@ManyToOne
	@JoinColumn(name = "subcategoria_id")
	private TiposEntidad subCategoriaId;

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

	public byte[] getArchivoComentario() {
		return archivoComentario;
	}

	public void setArchivoComentario(byte[] archivoComentario) {
		this.archivoComentario = archivoComentario;
	}

	public UsuarioEntidad getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(UsuarioEntidad usuarioId) {
		this.usuarioId = usuarioId;
	}

	public TiposEntidad getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(TiposEntidad categoriaId) {
		this.categoriaId = categoriaId;
	}

	public TiposEntidad getSubCategoriaId() {
		return subCategoriaId;
	}

	public void setSubCategoriaId(TiposEntidad subCategoriaId) {
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
