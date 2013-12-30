package es.uparty.comunes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Utils {
	
	private final static String ENCRIPTA_TAG = "ENCRIPTA";
	
	public static String encrypt(String cadena) {
        return encrypt(cadena,Constants.CLAVE_ENCRYPT);
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
        return decrypt(cadena,Constants.CLAVE_ENCRYPT);
    }
    
    public static String encrypt(String cadena,String clave) {
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
        s.setPassword(clave);
        return s.encrypt(cadena);
    }
    
    public static String encryptURL(String cadena) {
        String encrypt = encrypt(cadena,Constants.CLAVE_ENCRYPT);
        String encode="";
        try {
            encode = URLEncoder.encode(encrypt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }
}
