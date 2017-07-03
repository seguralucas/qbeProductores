package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.DirectorioManager;
import exit.services.fileHandler.ConstantesGenerales;
import exit.services.singletons.ApuntadorDeEntidad;
import exit.services.singletons.RecEntAct;
import exit.services.util.ConvertidorJson;

public class GetIdsAEliminar extends AbstractHTTP{

	@Override
	protected Object procesarPeticionOK(BufferedReader in, String id, int responseCode) throws Exception {
			//NO APLICA		
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, String id, int responseCode) throws Exception {
		// NO APLICA
		return null;		
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, int responseCode) throws Exception {
		JSONObject jsonObject = ConvertidorJson.convertir(in);
		JSONArray jsonArrayItems= (JSONArray) jsonObject.get(("items"));
		EliminarGenerico e= new EliminarGenerico();
		Integer resultado=jsonArrayItems.size();
		ExecutorService workers = Executors.newFixedThreadPool(RecEntAct.getInstance().getCep().getNivelParalelismo());      	
	    List<Callable<Void>> tasks = new ArrayList<>();
		for(int i=0;i<jsonArrayItems.size();i++){
			final Integer j=i;
			tasks.add(new Callable<Void>() {
		        public Void call() {
    		JSONObject jsonItem;
			jsonItem=(JSONObject)jsonArrayItems.get(j);
			Long id=(Long)jsonItem.get("id");
			e.realizarPeticion(EPeticiones.DELETE, String.valueOf(id),RecEntAct.getInstance().getCep().getCabecera());
			return null;
		        }
			});
		}
	    workers.invokeAll(tasks);
	    workers.shutdown();
		return resultado; //Devuelve la cantidad de registros encontradas
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, int responseCode) throws Exception {
		String path=("error_recuperacion_servidor_codigo_"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        out.println("No se pudo recuerar informacion de la entidad: "+ApuntadorDeEntidad.getInstance().getEntidadActual());
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        out.println(ConstantesGenerales.SEPARADOR_ERROR_JSON);
        CSVHandler csvHandler = new CSVHandler();
        csvHandler.escribirCSV("error_recuperacion_servidor_codigo_"+responseCode+".csv", "No se pudo recuerar informacion de la entidad: "+ApuntadorDeEntidad.getInstance().getEntidadActual(),false);            
        out.println(ConstantesGenerales.SEPARADOR_ERROR_PETICION);
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
