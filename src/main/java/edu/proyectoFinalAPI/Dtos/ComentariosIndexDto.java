package edu.proyectoFinalAPI.Dtos;

/**
 * DTO donde se encuentra los atributos hacia la vista en la pagina index
 */
public class ComentariosIndexDto {
	
	private String aliasUsuarioComentario="aaaaa";
	private String comentarioTexto="aaaaa";
	private byte[] imagenUsuario;
	private Long meGustaComentarios=(long) 0;
	
	public String getAliasUsuarioComentario() {
		return aliasUsuarioComentario;
	}
	public void setAliasUsuarioComentario(String aliasUsuarioComentario) {
		this.aliasUsuarioComentario = aliasUsuarioComentario;
	}
	public String getComentarioTexto() {
		return comentarioTexto;
	}
	public void setComentarioTexto(String comentarioTexto) {
		this.comentarioTexto = comentarioTexto;
	}
	public byte[] getImagenUsuario() {
		return imagenUsuario;
	}
	public void setImagenUsuario(byte[] imagenUsuario) {
		this.imagenUsuario = imagenUsuario;
	}
	public Long getMeGustaComentarios() {
		return meGustaComentarios;
	}
	public void setMeGustaComentarios(Long meGustaComentarios) {
		this.meGustaComentarios = meGustaComentarios;
	}
	
	
	

}
