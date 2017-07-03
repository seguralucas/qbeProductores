package exit.services.singletons;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.ConstantesGenerales;
import exit.services.procesadorJson.IProcesadorJson;
import exit.services.util.ConvertidorJson;

public class RecuperadorFormato {

	private String formato;
	private JSONObject jsonFormato;
	
	public RecuperadorFormato(String nombre) throws Exception{
		File f= new File(ConstantesGenerales.PATH_CONFIGURACION_ENTIDADES+"/"+nombre+"/formatoJson.json");
		String line;
		StringBuilder sb= new StringBuilder();
		BufferedReader br= new BufferedReader(new FileReader(f));
			while((line=br.readLine()) != null){
				sb.append(line);
				
			}
			this.formato=sb.toString();		
			jsonFormato = ConvertidorJson.convertir(this.formato);
		br.close();
	}
	public String getFormato() {
		return formato;
	}
	
	
	public JSONObject getJsonFormato() throws Exception{
		return ConvertidorJson.convertir(this.formato);
	}
	
/*	public static void main(String[] args) {
		ApuntadorDeEntidad.getInstance().siguienteEntidad();
		RecuperadorFormato.getInstancia().printJsonObject(RecuperadorFormato.getInstancia().jsonFormato);
	}*/
	
}
