package models;

import java.io.Serializable;

public class DiscotecaDTO implements Serializable {
	private long idDiscoteca;
	private String nombre;
	private double latitud;
	private double longitud;
	private String descripcion;
	private String nombreImg;
	public String getNombreImg() {
		return nombreImg;
	}
	public void setNombreImg(String nombreImg) {
		this.nombreImg = nombreImg;
	}
	public long getIdDiscoteca() {
		return idDiscoteca;
	}
	public void setIdDiscoteca(long idDiscoteca) {
		this.idDiscoteca = idDiscoteca;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
