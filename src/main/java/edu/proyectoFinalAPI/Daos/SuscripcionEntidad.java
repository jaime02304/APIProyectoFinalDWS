package edu.proyectoFinalAPI.Daos;

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
 * Clase entidad donde se encuentra los elementos de la base de datos de la
 * parte suscripcion
 * 
 * @author jpribio - 07/05/25
 */
@Entity
@Table(name = "suscripcion_entidad")
public class SuscripcionEntidad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_suscripcion")
	private Long idSuscripcion;

	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UsuarioEntidad usuarioId;

	@ManyToOne
	@JoinColumn(name = "grupo_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private GrupoEntidad grupoId;

	public Long getIdSuscripcion() {
		return idSuscripcion;
	}

	public void setIdSuscripcion(Long idSuscripcion) {
		this.idSuscripcion = idSuscripcion;
	}

	public UsuarioEntidad getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(UsuarioEntidad usuarioId) {
		this.usuarioId = usuarioId;
	}

	public GrupoEntidad getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(GrupoEntidad grupoId) {
		this.grupoId = grupoId;
	}
}
