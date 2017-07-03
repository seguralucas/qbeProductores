package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.DirectorioManager;
import exit.services.fileHandler.ConstantesGenerales;

public class EliminarGenerico extends AbstractHTTP {
	
	
	
	@Override
	protected Object procesarPeticionOK(BufferedReader in, String id,int responseCode) throws Exception{
		CSVHandler csv= new CSVHandler();
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(CSVHandler.PATH_BORRADOS_OK);
        csv.escribirCSV(fichero, "Id eliminado: "+id);
        return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in,  String id, int responseCode) throws Exception{
		String path=("error_borrado_servidor_codigo_"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        out.println("No se pudo borrar id: "+id);
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        out.println(ConstantesGenerales.SEPARADOR_ERROR_JSON);
        CSVHandler csvHandler = new CSVHandler();
        csvHandler.escribirCSV("error_borrado_servidor_codigo_"+responseCode+".csv", "No se pudo borrar id: "+id,false);            
        out.println(ConstantesGenerales.SEPARADOR_ERROR_PETICION);
        out.close();
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
