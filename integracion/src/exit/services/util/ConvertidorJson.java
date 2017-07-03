package exit.services.util;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ConvertidorJson {
	
	

	
	
	public static JSONObject convertir(BufferedReader in) throws Exception{
		StringBuilder builder = new StringBuilder();
		String line;
        while ((line = in.readLine()) != null) {
            builder.append(line);
        }
        String jsonString = builder.toString();
		return convertir(jsonString);
	}
	
	public static JSONArray convertirArray(BufferedReader in) throws Exception{
		StringBuilder builder = new StringBuilder();
		String line;
        while ((line = in.readLine()) != null) {
            builder.append(line);
        }
        String jsonString = builder.toString();
		return convertirArray(jsonString);
	}
	
	public static JSONObject convertir(String in) throws Exception{
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(in);
	}
	
	public static JSONArray convertirArray(String in) throws Exception{
		JSONParser parser = new JSONParser();
		return (JSONArray) parser.parse(in);
	}
}
