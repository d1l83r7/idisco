package models;

import java.io.Serializable;

public class User implements Serializable {
	private long id = 0;
	private String password;
	private String perfil;
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	private String usuario;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
