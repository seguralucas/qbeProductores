package exit.services.convertidos.csvAJson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import exit.services.fileHandler.CSVHandler;
import exit.services.singletons.ConfiguracionEntidadParticular;
import exit.services.singletons.RecEntAct;
import exit.services.singletons.RecuperadorPropierdadesJson;

public class JsonGenerico extends AbstractJsonRestEstructura{
	/*************************************/
	
	/*************************************/

	/**
	 * @throws Exception *********************************************/
	
	public JsonGenerico(ConfiguracionEntidadParticular confEntidadPart) throws Exception {
		super(confEntidadPart);
	}
	
	@Override
	public Object alterarValor(String cabecera, String valor) {
		switch(confEntidadPart.getRecuperadorPropiedadesJson().getTipo(cabecera)){
			case RecuperadorPropierdadesJson.TIPO_FECHA: return procesarFecha(cabecera,valor);
			case RecuperadorPropierdadesJson.TIPO_ENTERO: return procesarEntero(cabecera,valor);
			case RecuperadorPropierdadesJson.TIPO_CADENA: return  procesarCadena(cabecera,valor);
			default: return valor;
		}
	}
	
	@Override
	public void agregarCampo(String cabecera, String valor) {
		insertarValorJson(cabecera,valor);
		insertarValorMap(cabecera,valor);
	}	
	
	/**
	 * Método Exclusivo para debuguear
	 * @param dataJson
	 * @throws ParseException
	 */

	public void mostrar() {
		System.out.println();
	}
	
	private boolean compararCampos(String valorPermitido, String valor,String cabecera){
		if(confEntidadPart.getRecuperadorPropiedadesJson().isValoresPermitidosCaseSensitive(cabecera))
			return valor.equals(valorPermitido);
		else
			return valor.equalsIgnoreCase(valorPermitido);
	}

	@Override
	public boolean validarCampos() {
		RecuperadorPropierdadesJson rec=confEntidadPart.getRecuperadorPropiedadesJson();
		for (Map.Entry<String, Object> entry : getMapCabeceraValor().entrySet()) {
			String cabecera=entry.getKey();
			JSONArray jsonArray= rec.getValoresPermitidosLista(cabecera);
			if(jsonArray!=null){
				boolean valorPermitido=false;
				for(int i=0;i<jsonArray.size();i++){
					if(rec.getTipo(cabecera).equalsIgnoreCase(RecuperadorPropierdadesJson.TIPO_CADENA)){
						if(compararCampos((String)jsonArray.get(i),(String)entry.getValue(),entry.getKey()))
							valorPermitido=true;
					}
				}
				if(!valorPermitido){
					CSVHandler csv= new CSVHandler();
					csv.escribirCSVErrorValidacion(line,"El valor: "+entry.getValue()+" no esta permitido para el atributo: "+entry.getKey());
					return false;
				}
			}
		}
		return true;
	}

//	@Override
//	public JSONHandler createJson() throws Exception {
//		return new JSONHandler(getLine(),getJsonFormato());
//	}

	@Override
	public HashMap<String, Object> getMapCabeceraValor() {
		return mapCabeceraValor;
	}



}
