package exit.services.singletons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.ConstantesGenerales;

public class RecuperadorPropierdadesJson {
	private JSONObject jsonPropiedades;
	private HashMap<String, JSONObject> mapCabeceraJson;
	public static final String PROPIEDAD_TIPO="tipo";
	public static final String PROPIEDAD_BORRAR_SI_ES_NULL="borrarSiEsNull";
	public static final String PROPIEDAD_BORRAR_CAR_NO_NUMERICOS="borrarCarNoNumericos";
	public static final String PROPIEDAD_REEMPLAZAR_CAR_ESPANOL="reemplazarCarEspanol";
	public static final String PROPIEDAD_VALOR_POR_DEFAULT="valorPorDefault";
	public static final String PROPIEDAD_COMPLETAR10CEROS="completar10Ceros";

	public static final String PROPIEDAD_VALORES_PERMITIDOS="valoresPermitidos";
	public static final String PROPIEDAD_VALORES_PERMITIDOS_LISTA="lista";
	public static final String PROPIEDAD_VALORES_PERMITIDOS_CASE_SENSITIVE="caseSensitive";
	
	public static final String TIPO_FECHA="fecha";
	public static final String TIPO_ENTERO="entero";
	public static final String TIPO_CADENA="cadena";


	
	public JSONObject getPropiedades(String key){
		return mapCabeceraJson.get(key);
	}
	
	public String getTipo(String key){
		JSONObject j= this.getPropiedades(key);
		return j==null?"":(String)j.get(PROPIEDAD_TIPO);
	}
	
	public RecuperadorPropierdadesJson(String nombreEntidad) throws ParseException, IOException{
		mapCabeceraJson=new HashMap<String, JSONObject>();
		File f= new File(ConstantesGenerales.PATH_CONFIGURACION_ENTIDADES+"/"+nombreEntidad+"/tiposDeDatos.json");
		BufferedReader br= new BufferedReader(new FileReader(f));
			String line;
			StringBuilder sb= new StringBuilder();
			while((line=br.readLine()) != null){
				sb.append(line);
			}
			JSONParser parser = new JSONParser();
			this.jsonPropiedades = (JSONObject) parser.parse(sb.toString());
	
			for (Object key : this.jsonPropiedades.keySet()) {
		        String keyStr = (String)key;
		        Object keyvalue = this.jsonPropiedades.get(keyStr);
		        if (keyvalue instanceof JSONObject)
		        	mapCabeceraJson.put(keyStr, (JSONObject)keyvalue);
		    }
		br.close();

		/*JSONArray arr=(JSONArray)json.get("informacion");
		for(int i=0;i<arr.size();i++){
			JSONObject aux=(JSONObject)arr.get(i);
			mapCabeceraJson.put((String)aux.get("nombre"), aux);
		}*/
	}
	

	
	public boolean isBorrarCarNoNumericos(String key){
		JSONObject j= this.getPropiedades(key);
		Object aux=j==null?null:j.get(PROPIEDAD_BORRAR_CAR_NO_NUMERICOS);
		return aux==null?false:(Boolean)j.get(PROPIEDAD_BORRAR_CAR_NO_NUMERICOS);
	}
	
	public boolean isReemplazarCarEspanol(String key){
		JSONObject j= this.getPropiedades(key);
		Object aux=j==null?null:j.get(PROPIEDAD_REEMPLAZAR_CAR_ESPANOL);
		return aux==null?false:(Boolean)j.get(PROPIEDAD_REEMPLAZAR_CAR_ESPANOL);
	}
	
	public boolean isValorPorDefault(String key){
		JSONObject j= this.getPropiedades(key);
		Object aux=j==null?null:j.get(PROPIEDAD_VALOR_POR_DEFAULT);
		return aux==null?false:j.get(PROPIEDAD_VALOR_POR_DEFAULT).toString().length()>0;
	}
	
	public String getValorPorDefault(String key){
		JSONObject j= this.getPropiedades(key);
		return (String)j.get(PROPIEDAD_VALOR_POR_DEFAULT);
	}
	
	public boolean isCompletar10Ceros(String key){
		JSONObject j= this.getPropiedades(key);
		Object aux=j==null?null:j.get(PROPIEDAD_COMPLETAR10CEROS);
		return aux==null?false:(Boolean)j.get(PROPIEDAD_COMPLETAR10CEROS);
	}
	
	
	public String getBorrarSiEsNull(String key){
		JSONObject j= this.getPropiedades(key);
		String aux=j==null?null:(String)j.get(PROPIEDAD_BORRAR_SI_ES_NULL);
		return aux;
	}
	
	public JSONObject getValoresPermitidos(String key){
		JSONObject j= this.getPropiedades(key);
		JSONObject aux=j==null?null:(JSONObject)j.get(PROPIEDAD_VALORES_PERMITIDOS);
		return aux;
	}
		
	public JSONArray getValoresPermitidosLista(String key){
		JSONObject json= this.getValoresPermitidos(key);
		if(json==null)
			return null;
		return (JSONArray)json.get(PROPIEDAD_VALORES_PERMITIDOS_LISTA);
	}
	
	public boolean isValoresPermitidosCaseSensitive(String key){
		JSONObject json= this.getValoresPermitidos(key);
		if(json==null)
			return false;
		Boolean isCaseSensitive=(Boolean)json.get(PROPIEDAD_VALORES_PERMITIDOS_CASE_SENSITIVE);
		return isCaseSensitive==null?false:isCaseSensitive;
	}
	
	public boolean isBorrarSiEsNull(String key){
		JSONObject j= this.getPropiedades(key);
		Object aux=j==null?null:j.get(PROPIEDAD_BORRAR_SI_ES_NULL);
		return aux!=null;
	}
	
	
	public JSONObject getJsonPropiedades() {
		return jsonPropiedades;
	}
	
}
