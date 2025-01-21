package edu.proyectoFinalAPI.Daos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase entidad donde se encuentra los atributos en formato Dao
 * 
 * @author jpribio - 18/01/25
 */
@Entity
@Table(name = "usuarios")
public class UsuarioEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usu")
	private Long idUsuEntidad;

	@Column(name = "nombre_completo_usu")
	private String nombreCompletoUsuEntidad;

	@Column(name = "alias_usu", unique = true)
	private String aliasUsuEntidad;

	@Column(name = "correo_electronico_usu", unique = true)
	private String correoElectronicoUsuEntidad;

	@Column(name = "movil_usu")
	private int movilUsuEntidad;

	@Column(name = "rol_usu")
	private String rolUsuEntidad;

	@Column(name = "foto_usu")
	private byte[] fotoUsuEntidad;

	@Column(name = "contrasenia_usu")
	private String contraseniaUsuEntidad;;

	@Column(name = "es_premium")
	private Boolean esPremiumEntidad;

//	@OneToMany(mappedBy = "creadorUsuId")
//	private List<GrupoEntidad> grupos;

	// Getters y setters
	public Long getIdUsuEntidad() {
		return idUsuEntidad;
	}

	public void setIdUsuEntidad(Long idUsuEntidad) {
		this.idUsuEntidad = idUsuEntidad;
	}

	public String getNombreCompletoUsuEntidad() {
		return nombreCompletoUsuEntidad;
	}

	public void setNombreCompletoUsuEntidad(String nombreCompletoUsuEntidad) {
		this.nombreCompletoUsuEntidad = nombreCompletoUsuEntidad;
	}

	public String getAliasUsuEntidad() {
		return aliasUsuEntidad;
	}

	public void setAliasUsuEntidad(String aliasUsuEntidad) {
		this.aliasUsuEntidad = aliasUsuEntidad;
	}

	public String getCorreoElectronicoUsuEntidad() {
		return correoElectronicoUsuEntidad;
	}

	public void setCorreoElectronicoUsuEntidad(String correoElectronicoUsuEntidad) {
		this.correoElectronicoUsuEntidad = correoElectronicoUsuEntidad;
	}

	public int getMovilUsuEntidad() {
		return movilUsuEntidad;
	}

	public void setMovilUsuEntidad(int movilUsuEntidad) {
		this.movilUsuEntidad = movilUsuEntidad;
	}

	public String getRolUsuEntidad() {
		return rolUsuEntidad;
	}

	public void setRolUsuEntidad(String rolUsuEntidad) {
		this.rolUsuEntidad = rolUsuEntidad;
	}

	public byte[] getFotoUsuEntidad() {
		return fotoUsuEntidad;
	}

	public void setFotoUsuEntidad(byte[] fotoUsuEntidad) {
		this.fotoUsuEntidad = fotoUsuEntidad;
	}

	public String getContraseniaUsuEntidad() {
		return contraseniaUsuEntidad;
	}

	public void setContraseniaUsuEntidad(String contraseniaUsuEntidad) {
		this.contraseniaUsuEntidad = contraseniaUsuEntidad;
	}

	public Boolean getEsPremiumEntidad() {
		return esPremiumEntidad;
	}

	public void setEsPremiumEntidad(Boolean esPremiumEntidad) {
		this.esPremiumEntidad = esPremiumEntidad;
	}
//
//	public List<GrupoEntidad> getGrupos() {
//		return grupos;
//	}
//
//	public void setGrupos(List<GrupoEntidad> grupos) {
//		this.grupos = grupos;
//	}

	// Contructores
	public UsuarioEntidad() {
		super();
	}

	public UsuarioEntidad(String correoElectronicoUsuEntidad, String contraseniaUsuEntidad) {
		super();
		this.correoElectronicoUsuEntidad = correoElectronicoUsuEntidad;
		this.contraseniaUsuEntidad = contraseniaUsuEntidad;
	}

}
