package models;

import java.io.Serializable;

public class Discoteca implements Serializable {
	private long idDiscoteca;
	private String nombre;
	private double latitud;
	private double longitud;
	private String descripcion;
	private String nombreImg;
	private String airbopAppKey;
	private String airbopAppSecret;
	private String googleProjectNumber;
	public String getAirbopAppKey() {
		return airbopAppKey;
	}
	public void setAirbopAppKey(String airbopAppKey) {
		this.airbopAppKey = airbopAppKey;
	}
	public String getAirbopAppSecret() {
		return airbopAppSecret;
	}
	public void setAirbopAppSecret(String airbopAppSecret) {
		this.airbopAppSecret = airbopAppSecret;
	}
	public String getGoogleProjectNumber() {
		return googleProjectNumber;
	}
	public void setGoogleProjectNumber(String googleProjectNumber) {
		this.googleProjectNumber = googleProjectNumber;
	}
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
