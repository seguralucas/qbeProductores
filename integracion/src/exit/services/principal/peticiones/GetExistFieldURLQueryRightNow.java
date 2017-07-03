package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.fileHandler.DirectorioManager;
import exit.services.util.ConvertidorJson;

public class GetExistFieldURLQueryRightNow extends AbstractHTTP{

	@Override
	protected Object procesarPeticionOK(BufferedReader in, String id, int responseCode) throws Exception {
		// No utilizado
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, String id, int responseCode) throws Exception {
		// No utilizado
		return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, int responseCode) throws Exception {
		JSONObject jsonObject = ConvertidorJson.convertir(in);
		JSONArray jsonArrayItems= (JSONArray) jsonObject.get("items");
		JSONObject jsonCuerpo=(JSONObject) jsonArrayItems.get(0);
		JSONArray resultados=(JSONArray) jsonCuerpo.get("rows");
		if(resultados.size()>=1)
			return ((JSONArray)resultados.get(0)).get(0);
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, int responseCode) throws Exception {
		String path=("error_ger_verificarExistencia"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        out.close();
        return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json, int responseCode) throws Exception {
		String path=("error_ger_verificarExistencia"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        out.close();
        return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json, String id, int responseCode)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json, String id, int responseCode)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
