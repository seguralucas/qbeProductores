package exit.services.principal.peticiones.vtex.borrado;

import java.io.BufferedReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.principal.peticiones.AbstractHTTP;
import exit.services.principal.peticiones.EPeticiones;
import exit.services.singletons.RecEntAct;
import exit.services.util.ConvertidorJson;

public class GetVtexIdProductos extends AbstractHTTP{

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
		for(int i=0;i<jsonArrayRows.size();i++){
			String vtexID= ((JSONArray)jsonArrayRows.get(i)).get(0).toString();
			String url="https://whirlpool.custhelp.com/services/rest/connect/v1.3/queryResults/?query=select%20id%20from%20Whirlpool.Producto%20where%20vtexId%20=%27"+vtexID+"%27";
			GetProductosVTEXIDAEliminar g= new GetProductosVTEXIDAEliminar();
			System.out.println(url);
			g.realizarPeticion(EPeticiones.GET, url, null, null, RecEntAct.getInstance().getCep().getSubEntidad("producto").getCabecera(), RecEntAct.getInstance().getCep().getSubEntidad("producto"));
		}
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
