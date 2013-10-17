package es.uparty.utils;

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
}
