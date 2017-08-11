package exit.services.principal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONObject;

import com.csvreader.CsvWriter;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.convertidos.csvAJson.JsonGenerico;
import exit.services.excepciones.ExceptionBiactiva;
import exit.services.fileHandler.CSVHandler;
import exit.services.principal.peticiones.AbstractHTTP;
import exit.services.principal.peticiones.EPeticiones;
import exit.services.principal.peticiones.GetExistFieldURLQueryRightNow;
import exit.services.principal.peticiones.PostGenerico;
import exit.services.principal.peticiones.UpdateGenericoRightNow;
import exit.services.principal.peticiones.vtex.GetVTEXMasterDataServicioACSV;
import exit.services.principal.peticiones.vtex.GetVTEXOMSServicioACSV;
import exit.services.principal.peticiones.vtex.GetVTEXOMSServicioAServicio;
import exit.services.singletons.ApuntadorDeEntidad;
import exit.services.singletons.ConfiguracionEntidadParticular;
import exit.services.singletons.RecEntAct;
import exit.services.util.JsonUtils;

public class Ejecutor {
	

	
	public Object updateRecuperandoIdPorQuery(String parametros, AbstractJsonRestEstructura jsonEst, String urlInsertUpdate){
		String idOrder="No obtenido";
		try{
		String separador=jsonEst.getConfEntidadPart().getIdentificadorAtributo();
		int aux;
		int index = parametros.indexOf(separador);
		while(index >= 0) {
		   aux=index;
		   index = parametros.indexOf(separador, index+1);
		   if(index>=0){
			   String key=parametros.substring(aux+1, index);
			   if(jsonEst.getMapCabeceraValor().get(key)!=null){
				   parametros=parametros.replaceAll(JsonUtils.reemplazarCorcheteParaRegex(separador+key+separador), jsonEst.getMapCabeceraValor().get(key).toString());
				   idOrder=jsonEst.getMapCabeceraValor().get(key).toString();
				   System.out.println("Filtra por: "+key+" con valor: "+jsonEst.getMapCabeceraValor().get(key).toString());
			   }
			   index = parametros.indexOf(separador, index+1);
		   }
		}
		GetExistFieldURLQueryRightNow get= new GetExistFieldURLQueryRightNow();
		String id=(String)get.realizarPeticion(EPeticiones.GET, parametros,null,null,jsonEst.getConfEntidadPart().getCabecera(), jsonEst.getConfEntidadPart());
		if(id!=null){
			System.out.println("Se va a actualizar la entidad: "+  jsonEst.getConfEntidadPart().getEntidadNombre()+ "con id: "+id);
			UpdateGenericoRightNow update= new UpdateGenericoRightNow();
			return update.realizarPeticion(EPeticiones.UPDATE, urlInsertUpdate , id, jsonEst,jsonEst.getConfEntidadPart().getCabecera(),jsonEst.getConfEntidadPart());
		}
		System.out.println("Se va a insertar la entidad: "+  jsonEst.getConfEntidadPart().getEntidadNombre());
			PostGenerico insertar= new PostGenerico();
			return insertar.realizarPeticion(EPeticiones.POST, urlInsertUpdate, jsonEst,jsonEst.getConfEntidadPart().getCabecera());
		}
		catch(Exception e){
			CSVHandler csv= new CSVHandler();
			try {
				csv.escribirCSV("errorAlActualizar.csv", idOrder);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	public Object updateRecuperandoIdPorQuery(String parametros, AbstractJsonRestEstructura jsonEst){
		return this.updateRecuperandoIdPorQuery(parametros, jsonEst,jsonEst.getConfEntidadPart().getUrl() );
	}
	
	public Object updateRecuperandoIdPorQuery(String parametros, JSONObject jsonEst, String urlInsertUpdate, ConfiguracionEntidadParticular conf){
		String idOrder="No obtenido";
		try{
		GetExistFieldURLQueryRightNow get= new GetExistFieldURLQueryRightNow();
		String id=(String)get.realizarPeticion(EPeticiones.GET, parametros,null,null,conf.getCabecera(), conf);
		if(id!=null){
			System.out.println("Se va a actualizar la entidad: "+  conf.getEntidadNombre()+ "con id: "+id);
			UpdateGenericoRightNow update= new UpdateGenericoRightNow();
			return update.realizarPeticion(EPeticiones.UPDATE, urlInsertUpdate , id, new JsonGenerico(jsonEst),conf.getCabecera(),conf);
		}
		System.out.println("Se va a insertar la entidad: "+  conf.getEntidadNombre());
			PostGenerico insertar= new PostGenerico();
			return insertar.realizarPeticion(EPeticiones.POST, urlInsertUpdate, new JsonGenerico(jsonEst),conf.getCabecera());
		}
		catch(Exception e){
			CSVHandler csv= new CSVHandler();
			try {
				csv.escribirCSV("errorAlActualizar.csv", idOrder);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	
	public void ejecutorGenericoCsvAServicio(AbstractJsonRestEstructura jsonEst){
			try{
				AbstractHTTP insertar= new PostGenerico();
				insertar.realizarPeticion(EPeticiones.POST,jsonEst,RecEntAct.getInstance().getCep().getCabecera());
			}
			catch(Exception e){
				escribirExcepcion(e,jsonEst);
			}
	}
	
	private final String URL_QBE_SERVICIOS="https://qbe.custhelp.com/services/rest/connect/v1.3/";
	private final String URL_QBE_QUERYS=URL_QBE_SERVICIOS+"queryResults/?query=";
	private String getIdUnidadDeNegocio(AbstractJsonRestEstructura json, String entidad, String elemento){
		GetExistFieldURLQueryRightNow get= new GetExistFieldURLQueryRightNow();
		return (String)get.realizarPeticion(EPeticiones.GET, URL_QBE_QUERYS+"select%20id%20from%20Qbe."+entidad+"%20where%20Descripcion%20=%20%27"+json.getMapCabeceraValor().get(elemento).toString().replaceAll(" ","%20")+"%27",null,null,json.getConfEntidadPart().getCabecera(), json.getConfEntidadPart());
	}

	private JSONObject armarJsonProductor(AbstractJsonRestEstructura json){
		String idUnidadDeNegocio=getIdUnidadDeNegocio(json,"UnidadDeNegocio","UNIDAD_NEGOCIO");
		String idOrganizador=getIdUnidadDeNegocio(json,"Organizador","ORGANIZADOR");
		String idGrupoOrganizador=getIdUnidadDeNegocio(json,"GrupoOrganizador","GRUPO_ORGANIZADOR");
		System.out.println(idUnidadDeNegocio+" "+idOrganizador+" "+idGrupoOrganizador);
		JSONObject jsonProductor=(JSONObject)json.getJson().get("productor");
		JSONObject jsonIdUnidadDeNegocio = new JSONObject();
		JSONObject jsonIdOrganizador = new JSONObject();
		JSONObject jsonIdGrupoOrganizador = new JSONObject();

		jsonProductor.remove("UnidadDeNegocio");
		jsonIdUnidadDeNegocio.put("id", Long.parseLong(idUnidadDeNegocio));		
		jsonProductor.put("UnidadDeNegocio", jsonIdUnidadDeNegocio);
		
		jsonProductor.remove("Organizador");
		jsonIdOrganizador.put("id", Long.parseLong(idOrganizador));
		jsonProductor.put("Organizador", jsonIdOrganizador);
		
		jsonProductor.remove("GrupoOrganizador");
		jsonIdGrupoOrganizador.put("id", Long.parseLong(idGrupoOrganizador));
		jsonProductor.put("GrupoOrganizador", jsonIdGrupoOrganizador);
		return jsonProductor;

	}
	
	private String getIdContacto(AbstractJsonRestEstructura json){
		GetExistFieldURLQueryRightNow get= new GetExistFieldURLQueryRightNow();
		return (String)get.realizarPeticion(EPeticiones.GET, URL_QBE_QUERYS+"select%20id%20from%20contacts%20where%20Contacts.Emails.EmailList.Address=%27"+json.getMapCabeceraValor().get("MAIL").toString()+"%27",null,null,json.getConfEntidadPart().getCabecera(), json.getConfEntidadPart());
	}
	
	public Object ejecutorQBEProductor(AbstractJsonRestEstructura json){
		JSONObject aux= json.getJson();
		json.setJson(armarJsonProductor(json));
		String idContacto=getIdContacto(json);
		JsonGenerico jsonProductor=(JsonGenerico) this.updateRecuperandoIdPorQuery(URL_QBE_QUERYS+"select%20id%20from%20Qbe.Productor%20where%20Cliensec%20=%20"+json.getMapCabeceraValor().get("CLIENSEC").toString(),json , URL_QBE_SERVICIOS+"Qbe.Productor");
		Long idProductor=Long.parseLong(((JSONObject)jsonProductor.getJson().get("propiedadesExtras")).get("idproductorContacto").toString());
		JSONObject jsonIdProductor= new JSONObject();
		jsonIdProductor.put("id", idProductor);
		if(idContacto==null){
			JSONObject jsonContacto=(JSONObject)aux.get("contacto");
			JSONObject jsonCustomer=(JSONObject)((JSONObject)jsonContacto.get("customFields")).get("Qbe");
			jsonCustomer.put("Productor", jsonIdProductor);
			json.setJson(jsonContacto);
			PostGenerico insertar= new PostGenerico();
			System.out.println("Se va a insertar el contacto");
			return insertar.realizarPeticion(EPeticiones.POST, URL_QBE_SERVICIOS+"contacts", json ,json.getConfEntidadPart().getCabecera());
		}
		else{
			JSONObject jsonContactoUpdate= new JSONObject();
			JSONObject customFields = new JSONObject();
			JSONObject qbe= new JSONObject();
			qbe.put("Productor", jsonIdProductor);
			customFields.put("Qbe", qbe);
			jsonContactoUpdate.put("customFields", customFields);
			json.setJson(jsonContactoUpdate);
			System.out.println("Se va a actualizar el contacto con id: "+idContacto);
			UpdateGenericoRightNow update= new UpdateGenericoRightNow();
			return update.realizarPeticion(EPeticiones.UPDATE, URL_QBE_SERVICIOS+"contacts" , idContacto, json,json.getConfEntidadPart().getCabecera(),json.getConfEntidadPart());
		}
	}
	
	
	private void escribirExcepcion(Exception e, AbstractJsonRestEstructura jsonEst){
		e.printStackTrace();
		CSVHandler csv= new CSVHandler();
		try {
			csv.escribirErrorException(e.getStackTrace());
			csv.escribirCSV("error_no_espeficado.csv", jsonEst.getLine());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public Object ejecutar(String nombreMetodo,AbstractJsonRestEstructura jsonEst) throws ExceptionBiactiva{
		ApuntadorDeEntidad.getInstance().siguienteEntidad();
		return ejecutar(nombreMetodo,null,jsonEst);
	}
	
	public Object ejecutar(String nombreMetodo, String parametros) throws ExceptionBiactiva{
		return ejecutar(nombreMetodo,parametros,null);
	}
	
	public Object ejecutar(String nombreMetodo, String parametros, AbstractJsonRestEstructura jsonEst) throws ExceptionBiactiva{
		Class<Ejecutor> a= Ejecutor.class;
		try {
			Method m;
			Object o;
			if(parametros!=null){
				if(jsonEst!=null){
					m= a.getMethod(nombreMetodo, parametros.getClass(),AbstractJsonRestEstructura.class);
					o=m.invoke(this,parametros,jsonEst);
				}
				else{
					m= a.getMethod(nombreMetodo, parametros.getClass());
					o=m.invoke(this,parametros);				}
			}
			else{
				if(jsonEst!=null){
					m=a.getMethod(nombreMetodo,AbstractJsonRestEstructura.class);
					o=m.invoke(this,jsonEst);
				}
				else{
					m=a.getMethod(nombreMetodo);
					o=m.invoke(this);
				}
			}
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			CSVHandler csv= new CSVHandler();
			csv.escribirErrorException(e.getStackTrace());
			throw new ExceptionBiactiva("Error al ejecutar ejecutor");
		} 
	}
	
	
/*	public static void ejecutorGenerico2(AbstractJsonRestEstructura jsonEst){
		System.out.println("ttt");		
	}
	
	
	public static void main(String[] args) throws Exception {
		Method m;
		Object o;
		Class<Ejecutor> a= Ejecutor.class;
		ApuntadorDeEntidad.getInstance().siguienteEntidad();
		AbstractJsonRestEstructura json= new JsonGenerico();
		m=a.getMethod("ejecutorGenerico2",json.getClass().getSuperclass());
		o=m.invoke(null,json);
	}*/
}
