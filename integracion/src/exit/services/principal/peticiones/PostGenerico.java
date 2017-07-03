package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.json.simple.JSONObject;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.DirectorioManager;
import exit.services.principal.peticiones.AbstractHTTP;
import exit.services.fileHandler.ConstantesGenerales;
import exit.services.singletons.RecEntAct;

public class PostGenerico extends AbstractHTTP{
	@Override
	protected Object procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json, int responseCode) throws Exception {
		CSVHandler csv= new CSVHandler();
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(json.getConfEntidadPart(), CSVHandler.PATH_INSERTADOS_OK);
        String inputLine;
        boolean marca = true; //Recuperamos el ID
        String id=null;
        while ((inputLine = in.readLine()) != null) {
        	//out.println(inputLine);
        	if(marca && inputLine.contains("id")){
        		id=inputLine.replaceAll("\"id\": ", "").replaceAll(",", "");
        		marca=false;
        	}
        }
        String cabecera;
        if(json.getCabeceraCSV()==null)//Esta validacion es sólo por algo que quedo viejo cuando va de CSV a Servicio
        	cabecera=CSVHandler.cabeceraFichero;
       	else
       		cabecera=json.getCabeceraCSV();
        csv.escribirCSV(fichero, id+RecEntAct.getInstance().getCep().getSeparadorCSV()+json.getLine(), "ID"+RecEntAct.getInstance().getCep().getSeparadorCSV()+cabecera,true);        
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
	protected Object procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json, int responseCode) throws Exception{
		String path=("error_insercion_servidor_codigo_"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(json.getConfEntidadPart(),path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        out.println(json.toString());
        out.println(ConstantesGenerales.SEPARADOR_ERROR_JSON);

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        out.println(ConstantesGenerales.SEPARADOR_ERROR_PETICION);
        out.close();

        CSVHandler csvHandler = new CSVHandler();
        String cabecera;
        if(json.getCabeceraCSV()==null)//Esta validacion es sólo por algo que quedo viejo cuando va de CSV a Servicio
        	cabecera=CSVHandler.cabeceraFichero;
       	else
       		cabecera=json.getCabeceraCSV();
        csvHandler.escribirCSV("error_insercion_servidor_codigo_"+responseCode+".csv", json.getConfEntidadPart() ,RecEntAct.getInstance().getCep().getSeparadorCSV()+json.getLine(), "ID"+RecEntAct.getInstance().getCep().getSeparadorCSV()+cabecera,true);                
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
