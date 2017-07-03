package exit.services.principal.peticiones.vtex.borrado;

import java.io.BufferedReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.principal.peticiones.AbstractHTTP;
import exit.services.principal.peticiones.EPeticiones;
import exit.services.principal.peticiones.EliminarGenerico;
import exit.services.singletons.RecEntAct;
import exit.services.util.ConvertidorJson;

public class GetProductosVTEXIDAEliminar extends AbstractHTTP {

	@Override
	protected Object procesarPeticionOK(BufferedReader in, String id, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, String id, int responseCode) throws Exception {
		return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, int responseCode) throws Exception {
		JSONObject jsonObject = ConvertidorJson.convertir(in);
		JSONArray jsonArrayItems= (JSONArray) jsonObject.get("items");
		JSONArray jsonArrayRows= (JSONArray)((JSONObject) jsonArrayItems.get(0)).get("rows");
		for(int i=0;i<jsonArrayRows.size()-1;i++){
			String id= ((JSONArray)jsonArrayRows.get(i)).get(0).toString();
			System.out.println(id);
			EliminarGenerico e= new EliminarGenerico();
			e.realizarPeticion(EPeticiones.DELETE, "https://whirlpool.custhelp.com/services/rest/connect/v1.3/Whirlpool.Producto", id, null, RecEntAct.getInstance().getCep().getSubEntidad("producto").getCabecera(), RecEntAct.getInstance().getCep().getSubEntidad("producto"));
		}
		System.out.println("---------");
		String id= ((JSONArray)jsonArrayRows.get(jsonArrayRows.size()-1)).get(0).toString();
		System.out.println(id);
		System.out.println("---------");
		return null;
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
