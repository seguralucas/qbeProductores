package exit.services.singletons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.ConstantesGenerales;
import exit.services.util.ConvertidorJson;

public class RecuperadorEspecificacionesCSV {
	private String formato;
	private JSONObject jsonFormato;
	
	
	public RecuperadorEspecificacionesCSV(String nombreEntidad) throws Exception{
	File f= new File(ConstantesGenerales.PATH_CONFIGURACION_ENTIDADES+"/"+nombreEntidad+"/especificacionesCSV.json");
	String line;
	StringBuilder sb= new StringBuilder();
	BufferedReader br= new BufferedReader(new FileReader(f));
		while((line=br.readLine()) != null){
			sb.append(line);
		}
		this.formato=sb.toString();		
		jsonFormato = ConvertidorJson.convertir(this.formato);
	
	}
	
	public JSONArray getFuncion(String cabecera){
		Object o= jsonFormato.get(cabecera);
		if (o==null)
			return null;
		JSONObject json=(JSONObject)o;
		Object arr= json.get("functions");
		if(arr==null)
			return null;
		return (JSONArray)arr;
		
	}

}
