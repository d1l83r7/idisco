package es.uparty.dto;

import java.io.Serializable;

public class MensajeDTO implements Serializable {
	private static final long serialVersionUID = 1037635397154920905L;
	
	private String idMensaje;
	private String usuario;
	private String texto;
	public String getIdMensaje() {
		return idMensaje;
	}
	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}

}
