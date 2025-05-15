package edu.proyectoFinalAPI.Dtos;

/**
 * DTO donde se encuentra los atributos hacia la vista en la pagina index
 * 
 * @author jpribio - 22/01/25
 */
public class ComentariosIndexDto {

	private String aliasUsuarioComentario = "aaaaa";
	private String comentarioTexto = "aaaaa";
	private String imagenUsuario;

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

	public String getImagenUsuario() {
		return imagenUsuario;
	}

	public void setImagenUsuario(String imagenUsuario) {
		this.imagenUsuario = imagenUsuario;
	}

}
