package exit.services.procesadorJson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface IProcesadorJson {
	void procesarJson(JSONObject json, String keyValue);
    void procesarJson(JSONArray json, Integer index);
    
    default String insertarStringNoNull(String valorPorReemplazaR){
    	return valorPorReemplazaR==null?"":(String)valorPorReemplazaR;
    }
}

