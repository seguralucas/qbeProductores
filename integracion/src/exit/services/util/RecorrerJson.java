package exit.services.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RecorrerJson {

	
	
	private static Object getObjectJSONObject(Object json, String key){
		return ((JSONObject)json).get(key);
	}
	
	private static Object getObjectJSONArray(Object jsonArr, String datos){
		String[] datosPartidos=datos.split("\\[");
		Integer indice=Integer.parseInt(datosPartidos[1].substring(0, datosPartidos[1].length()-1));
		return ((JSONArray)((JSONObject)jsonArr).get(datosPartidos[0])).get(indice);	
	}
	
	private static boolean isJsonObject(String valor){
		return valor.equalsIgnoreCase("JSONObject");
	}
	
	public static String[] getArrayTipos(String accesoAlJson){
		String datos[]=accesoAlJson.split("\\.");
		String[] tipoDatos= new String[datos.length];
		for(int i=0;i<datos.length;i++){
			String dato=datos[i];
			String[] partes=dato.split("\\[");
			if(partes.length>1){
			   	tipoDatos[i]="JSONArray";
			}
			else
			   	tipoDatos[i]="JSONObject";
		}
		return tipoDatos;
	}
	
	public static Object getValue(String accesoAlJson, JSONObject json){
		try{
		String[] tiposDatos=getArrayTipos(accesoAlJson);
		String datos[]=accesoAlJson.split("\\.");
		Object posicionActual=json;
		Object resultado;
		for(int i=0;i<tiposDatos.length-1;i++){
			if(isJsonObject(tiposDatos[i]))/*Es jsonObject*/
					posicionActual=getObjectJSONObject(posicionActual,datos[i]);
			else/*Es un array*/
				posicionActual=getObjectJSONArray(posicionActual,datos[i]);
		}
		if(isJsonObject(tiposDatos[tiposDatos.length-1]))
			resultado=getObjectJSONObject(posicionActual,datos[tiposDatos.length-1]);
		else/*Es un array*/
			resultado=getObjectJSONArray(posicionActual,datos[tiposDatos.length-1]);
		return resultado;
		}
		catch(Exception e){
			return null;
		}
		}
}
