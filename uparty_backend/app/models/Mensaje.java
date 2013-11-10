package models;

import java.io.Serializable;

public class Mensaje implements Serializable {
	private long idMensaje;
	private long idMuro;
	private long idDiscoteca;
	private long idUsuario;
	private String mensaje;
	private String usuario;
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public long getIdMensaje() {
		return idMensaje;
	}
	public void setIdMensaje(long idMensaje) {
		this.idMensaje = idMensaje;
	}
	public long getIdMuro() {
		return idMuro;
	}
	public void setIdMuro(long idMuro) {
		this.idMuro = idMuro;
	}
	public long getIdDiscoteca() {
		return idDiscoteca;
	}
	public void setIdDiscoteca(long idDiscoteca) {
		this.idDiscoteca = idDiscoteca;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
}
