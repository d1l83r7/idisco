package es.uparty.dto;

import java.io.Serializable;

public class DiscotecaDTO implements Serializable {

	private static final long serialVersionUID = 251175242693617029L;

	private String id;
	private String nombre;
	private String longitud;
	private String latitud;
	private String descripcio;
	private String nombreImagen;
	private String airbopAppKey;
	private String airbopAppSecret;
	private String googleProjectNumber;
	private String listaVipActiva;
	
	public String getListaVipActiva() {
		return listaVipActiva;
	}
	public void setListaVipActiva(String listaVipActiva) {
		this.listaVipActiva = listaVipActiva;
	}
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
	public String getNombreImagen() {
		return nombreImagen;
	}
	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getDescripcio() {
		return descripcio;
	}
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
}
