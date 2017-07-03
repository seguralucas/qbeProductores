package exit.services.principal.peticiones.vtex;

import java.io.BufferedReader;

import org.json.simple.JSONObject;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.principal.peticiones.AbstractHTTP;
import exit.services.util.ConvertidorJson;

public class GetVTEXEmailDesencriptado extends AbstractHTTP {

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

		try {
		JSONObject jsonObject = ConvertidorJson.convertir(in);
		String salida= (String)jsonObject.get("email");
		return salida;
		}
		catch(Exception e){
			return null;
		}
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json, int responseCode) throws Exception {
		
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
