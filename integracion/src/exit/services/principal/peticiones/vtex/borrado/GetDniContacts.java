package exit.services.principal.peticiones.vtex.borrado;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.fileHandler.CSVHandler;
import exit.services.principal.peticiones.AbstractHTTP;
import exit.services.principal.peticiones.EPeticiones;
import exit.services.principal.peticiones.EliminarGenerico;
import exit.services.singletons.RecEntAct;
import exit.services.util.ConvertidorJson;

public class GetDniContacts extends AbstractHTTP{
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
		JSONObject jsonObject = ConvertidorJson.convertir(in);
		JSONArray jsonArrayItems= (JSONArray) jsonObject.get("items");
		JSONArray jsonArrayRows= (JSONArray)((JSONObject) jsonArrayItems.get(0)).get("rows");
		CSVHandler.cabeceraFichero="id;dni";
    	ExecutorService workers = Executors.newFixedThreadPool(RecEntAct.getInstance().getCep().getNivelParalelismo());      	
	    List<Callable<Void>> tasks = new ArrayList<>();
			for(Integer i=0;i<jsonArrayRows.size();i++){
				final Integer j=i;
			tasks.add(new Callable<Void>() {
		        public Void call() {
						String dni= ((JSONArray)jsonArrayRows.get(j)).get(0).toString();
						String url="https://whirlpool.custhelp.com/services/rest/connect/v1.3/queryResults/?query=select%20id,customFields.Whirlpool.numero_documento%20from%20contacts%20where%20customFields.Whirlpool.numero_documento%20=%20%27"+dni+"%27";
						GetClientesVTEXIDAEliminar g= new GetClientesVTEXIDAEliminar();
						System.out.println(url);
						g.realizarPeticion(EPeticiones.GET, url, null, null, RecEntAct.getInstance().getCep().getSubEntidad("contacto").getCabecera(), RecEntAct.getInstance().getCep().getSubEntidad("contacto"));
						return null;
					}
		       });
			}
		    workers.invokeAll(tasks);
		    workers.shutdown();
		return jsonArrayRows.size();
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json, int responseCode)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json, int responseCode)
			throws Exception {
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
	protected Object procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json, String id,
			int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
