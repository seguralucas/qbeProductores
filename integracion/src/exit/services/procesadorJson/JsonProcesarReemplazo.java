package exit.services.procesadorJson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.singletons.ConfiguracionEntidadParticular;
import exit.services.singletons.RecEntAct;
import exit.services.singletons.RecuperadorPropierdadesJson;
import exit.services.util.JsonUtils;

public class JsonProcesarReemplazo implements IProcesadorJson {
	private Object valorPorReemplazaR;
	private String cabeceraPorReemplazar;
	private ConfiguracionEntidadParticular configEntidadPart;
	public  JsonProcesarReemplazo(Object valorPorReemplazaR, String cabeceraPorReemplazar,ConfiguracionEntidadParticular configEntidadPart) {
		this.valorPorReemplazaR=valorPorReemplazaR;
		this.cabeceraPorReemplazar=cabeceraPorReemplazar;
		this.configEntidadPart=configEntidadPart;
	}
	
	public static Integer ICont=0;
	
	@SuppressWarnings("unchecked")
	@Override
	public void procesarJson(JSONObject json, String keyValue) {
		ConfiguracionEntidadParticular r=configEntidadPart;
		Object o= json.get(keyValue);
		if(o instanceof String){
			String identificador=RecEntAct.getInstance().getCep().getIdentificadorAtributo();
			String valor=(String)o;
			if(r.getRecuperadorPropiedadesJson().getTipo(cabeceraPorReemplazar).equalsIgnoreCase(RecuperadorPropierdadesJson.TIPO_CADENA)){
				if(valor.contains(identificador+cabeceraPorReemplazar+identificador)){
					if(valorPorReemplazaR==null && valor.replaceAll(identificador+JsonUtils.reemplazarCorcheteParaRegex(cabeceraPorReemplazar)+identificador, "").trim().length()==0)
						json.put(keyValue,null);
					else if(valorPorReemplazaR==null){
						json.put(keyValue, valor.replaceAll(identificador+JsonUtils.reemplazarCorcheteParaRegex(cabeceraPorReemplazar)+identificador, ""));
					}
					else
						json.put(keyValue, valor.replaceAll(identificador+JsonUtils.reemplazarCorcheteParaRegex(cabeceraPorReemplazar)+identificador, insertarStringNoNull((String)valorPorReemplazaR)));				
				}
			}
			else if(valor.equalsIgnoreCase(identificador+cabeceraPorReemplazar+identificador))
				json.put(keyValue, valorPorReemplazaR);
		}
	}
	

	
	@Override
	public void procesarJson(JSONArray json, Integer index) {
		ConfiguracionEntidadParticular r= configEntidadPart;

		Object o= json.get(index);
		if(o instanceof String){
			String identificador=RecEntAct.getInstance().getCep().getIdentificadorAtributo();
			String valor=(String)json.get(index);
			if(r.getRecuperadorPropiedadesJson().getTipo(cabeceraPorReemplazar).equalsIgnoreCase(RecuperadorPropierdadesJson.TIPO_CADENA)){
				if(valor.matches(identificador+JsonUtils.reemplazarCorcheteParaRegex(cabeceraPorReemplazar)+identificador)){
					json.remove(index);
					if(valorPorReemplazaR==null)
						json.add(index,null);
					else
					json.add(index, valor.replaceAll(identificador+cabeceraPorReemplazar+identificador, insertarStringNoNull((String)valorPorReemplazaR)));				
				}
			}
			else if(valor.equalsIgnoreCase(identificador+cabeceraPorReemplazar+identificador)){
				json.remove(index);
				json.add(valorPorReemplazaR);	
			}
		}
	}
}
