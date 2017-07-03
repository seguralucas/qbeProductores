package exit.services.convertidor.jsonAJson;

import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.convertidos.csvAJson.JsonGenerico;
import exit.services.convertidos.csvAJson.JsonVTEX;
import exit.services.singletons.ConfiguracionEntidadParticular;
import exit.services.util.RecorrerJson;

public class ConvertirJsonAJson {
	
	private ConfiguracionEntidadParticular configEnt;
	private JSONObject obtenedorDeDatos;
	private AbstractJsonRestEstructura jsonAbstract;
	public ConvertirJsonAJson(ConfiguracionEntidadParticular configEnt, JSONObject obtenedorDeDatos) {
		super();
		this.configEnt = configEnt;
		this.obtenedorDeDatos=obtenedorDeDatos;
	}
	
	public JSONObject convertir() throws Exception{
		jsonAbstract= new JsonVTEX(configEnt);
		for(Object key:configEnt.getRecuperadorPropiedadesJson().getJsonPropiedades().keySet()){
			try{
			Object o=RecorrerJson.getValue(key.toString(), obtenedorDeDatos);
				if(o!=null){
					String valorExtraido=o.toString();
					jsonAbstract.agregarCampo(key.toString(), valorExtraido);
				}
				else
					jsonAbstract.agregarCampo(key.toString(), (String)o);
			}catch(Exception e){}
		}
		System.out.println(jsonAbstract.getJsonFormato());
		return jsonAbstract.getJsonFormato();
	}

	public AbstractJsonRestEstructura getJsonAbstract() {
		return jsonAbstract;
	}
	
	@Override
	public String toString() {
		return obtenedorDeDatos.toString().replace("\\", "").replace(",", ",\n");
	}
	
	public String toStringSinEnter(){
		return obtenedorDeDatos.toString().replace("\\", "");
	}
	
	public String toStringNormal(){
		return obtenedorDeDatos.toString();
	}


	
	
	
	
	
	
	
}
