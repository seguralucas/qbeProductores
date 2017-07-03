package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.json.simple.JSONObject;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.ConstantesGenerales;
import exit.services.fileHandler.DirectorioManager;
import exit.services.singletons.RecEntAct;

public class UpdateGenericoRightNow extends AbstractHTTP{


	@Override
	protected Object procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json, String id, int responseCode)
			throws Exception {
		CSVHandler csv= new CSVHandler();
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(json.getConfEntidadPart(), CSVHandler.PATH_UPDATES_OK);
        csv.escribirCSV(fichero, id+RecEntAct.getInstance().getCep().getSeparadorCSV()+json.getLine(), "ID"+RecEntAct.getInstance().getCep().getSeparadorCSV()+CSVHandler.cabeceraFichero,true);        
        JSONObject propiedadesExtra=(JSONObject)json.getJson().get(PROPIEDADES_EXTRA);
        if(propiedadesExtra==null)
        	propiedadesExtra= new JSONObject();
        else
        	json.getJson().remove(PROPIEDADES_EXTRA);
        propiedadesExtra.put("id"+json.getConfEntidadPart().getEntidadNombre(), id);
        json.getJson().put(PROPIEDADES_EXTRA, propiedadesExtra);
        return json;		
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json, String id, int responseCode)
			throws Exception {
		String path=("error_update_servidor_codigo_"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        out.println(json.toString());
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        out.println(ConstantesGenerales.SEPARADOR_ERROR_JSON);
        CSVHandler csvHandler = new CSVHandler();
        csvHandler.escribirCSV("error_update_servidor_codigo_"+responseCode+".csv",json);            
        out.println(ConstantesGenerales.SEPARADOR_ERROR_PETICION);
        out.close();
        return null;	
	}
	@Override
	protected Object procesarPeticionOK(BufferedReader in, String id, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, String id, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected Object procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
