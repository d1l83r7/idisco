package models;

import java.io.Serializable;
import java.util.Date;

public class ListaVipItem implements Serializable {
	private long idListaVip;
	private long idDiscoteca;
	private long idUsuario;
	private int acompanyantes;
	private Date fecha;
	private String nombreUsuario;
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public long getIdListaVip() {
		return idListaVip;
	}
	public void setIdListaVip(long idListaVip) {
		this.idListaVip = idListaVip;
	}
	public long getIdDiscoteca() {
		return idDiscoteca;
	}
	public void setIdDiscoteca(long idDiscoteca) {
		this.idDiscoteca = idDiscoteca;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getAcompanyantes() {
		return acompanyantes;
	}
	public void setAcompanyantes(int acompanyantes) {
		this.acompanyantes = acompanyantes;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
