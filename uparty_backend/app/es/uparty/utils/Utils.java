package es.uparty.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Utils {
	public static double calculaDistancia(double latitud1, double longitud1, double latitud2, double longitud2){
		double distancia = 0;
        //Radio de la tierra en km
		double R = 6378.137;
		double valueLat = latitud1 - latitud2;
		double dLat = extraeRadianes(valueLat);
		double valueLong = longitud1 - longitud2;
		double dLong = extraeRadianes(valueLong);
		
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(extraeRadianes(latitud1)) * Math.cos(extraeRadianes(latitud2)) * Math.sin(dLong/2) * Math.sin(dLong/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		distancia = R * c;
		return distancia;
	}
	
	private static double extraeRadianes(double value){
		double radianes = (value*Math.PI)/180;
		return radianes;
	}
	
	public static String encrypt(String cadena,String clave) {
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
        s.setPassword(clave);
        return s.encrypt(cadena);
    }
 
    public static String encrypt(String cadena) {
        return encrypt(cadena,MisConstantes.CLAVE_ENCRYPT);
    }
    
    public static String decrypt(String cadena,String clave) {
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
        s.setPassword(clave);
        String devuelve = "";
        try {
            devuelve = s.decrypt(cadena);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return devuelve;
    }
    public static String decrypt(String cadena) {
        return decrypt(cadena,MisConstantes.CLAVE_ENCRYPT);
    }
    
    public static String encryptURL(String cadena) {
        String encrypt = encrypt(cadena,MisConstantes.CLAVE_ENCRYPT);
        String encode="";
        try {
            encode = URLEncoder.encode(encrypt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }
    
    public static Date dateToString(String fecha)throws ParseException{
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	return formatter.parse(fecha);
    }
    
    public static String dateToString(Date fecha)throws ParseException{
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	return formatter.format(fecha);
    }
}
