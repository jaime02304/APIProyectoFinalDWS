package edu.proyectoFinalAPI.Dtos;

/**
 * Clase donde se encuentra los atributos DTO del usuario
 * 
 * @author jpribio - 18/01/25
 */
public class UsuarioDto {

	// Variabkles (Atributos)
	private Long idUsu;
	private String nombreCompletoUsu = "aaaaa";
	private String aliasUsu = "aaaaa";
	private String correoElectronicoUsu = "aaaaa";
	private int movilUsu = 0;
	private String rolUsu = "user";
	private byte[] fotoUsu;
	private String contraseniaUsu = "aaaaa";
	private Boolean esPremium = false;
	private Boolean esVerificadoEntidad = false;

	// Getters y setters

	public Long getIdUsu() {
		return idUsu;
	}

	public Boolean getEsPremium() {
		return esPremium;
	}

	public void setEsPremium(Boolean esPremium) {
		this.esPremium = esPremium;
	}

	public Boolean getEsVerificadoEntidad() {
		return esVerificadoEntidad;
	}

	public void setEsVerificadoEntidad(Boolean esVerificadoEntidad) {
		this.esVerificadoEntidad = esVerificadoEntidad;
	}

	public void setIdUsu(Long idUsu) {
		this.idUsu = idUsu;
	}

	public String getNombreCompletoUsu() {
		return nombreCompletoUsu;
	}

	public void setNombreCompletoUsu(String nombreCompletoUsu) {
		this.nombreCompletoUsu = nombreCompletoUsu;
	}

	public String getAliasUsu() {
		return aliasUsu;
	}

	public void setAliasUsu(String aliasUsu) {
		this.aliasUsu = aliasUsu;
	}

	public String getCorreoElectronicoUsu() {
		return correoElectronicoUsu;
	}

	public void setCorreoElectronicoUsu(String correoElectronicoUsu) {
		this.correoElectronicoUsu = correoElectronicoUsu;
	}

	public int getMovilUsu() {
		return movilUsu;
	}

	public void setMovilUsu(int movilUsu) {
		this.movilUsu = movilUsu;
	}

	public String getRolUsu() {
		return rolUsu;
	}

	public void setRolUsu(String rolUsu) {
		this.rolUsu = rolUsu;
	}

	public byte[] getFotoUsu() {
		return fotoUsu;
	}

	public void setFotoUsu(byte[] fotoUsu) {
		this.fotoUsu = fotoUsu;
	}

	public String getContraseniaUsu() {
		return contraseniaUsu;
	}

	public void setContraseniaUsu(String contraseniaUsu) {
		this.contraseniaUsu = contraseniaUsu;
	}

	public Boolean getEsPremiumB() {
		return esPremium;
	}

	public void setEsPremiumB(Boolean esPremiumB) {
		this.esPremium = esPremiumB;
	}

	// Constructores
	public UsuarioDto() {
		super();
	}

	public UsuarioDto(String correoElectronicoUsu, String contraseniaUsu) {
		super();
		this.correoElectronicoUsu = correoElectronicoUsu;
		this.contraseniaUsu = contraseniaUsu;
	}

}
