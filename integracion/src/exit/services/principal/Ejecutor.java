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
	

	
	public Object updateRecuperandoIdPorQuery(String parametros, AbstractJsonRestEstructura jsonEst){
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
			return update.realizarPeticion(EPeticiones.UPDATE, jsonEst.getConfEntidadPart().getUrl() , id, jsonEst,jsonEst.getConfEntidadPart().getCabecera(),jsonEst.getConfEntidadPart());
		}
		/*System.out.println("Se va a insertar la entidad: "+  jsonEst.getConfEntidadPart().getEntidadNombre());
			PostGenerico insertar= new PostGenerico();
			return insertar.realizarPeticion(EPeticiones.POST, jsonEst.getConfEntidadPart().getUrl(), jsonEst,jsonEst.getConfEntidadPart().getCabecera());*/
		}
		catch(Exception e){
			CSVHandler csv= new CSVHandler();
			try {
				csv.escribirCSV("errorAlActualizar.csv", idOrder);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
/*			System.out.println("Se va a insertar la entidad: "+  jsonEst.getConfEntidadPart().getEntidadNombre());
			PostGenerico insertar= new PostGenerico();
			return insertar.realizarPeticion(EPeticiones.POST, jsonEst.getConfEntidadPart().getUrl(),jsonEst,jsonEst.getConfEntidadPart().getCabecera());			*/
		}
		return null;//Borrar al descomentar
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
	
	private String getParametroDateOMG(Integer cantDias){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String hasta = sdf.format(new Date());
		Date d= new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, -1*cantDias);
		d.setTime( c.getTime().getTime() );
		String desde=sdf.format(d);
		return "f_creationDate=creationDate:%5B"+desde+"T00:00:00.000Z+TO+"+hasta+"T23:59:59.999Z%5D";		    
	}

	private String getParametroDateMasterData(Integer cantDias){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String hasta = sdf.format(new Date());
		Date d= new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, -1*cantDias);
		d.setTime( c.getTime().getTime() );
		String desde=sdf.format(d);
		return "_where=createdIn%20between%20"+desde+"%20AND%20"+hasta;		    
	}

	
	public Object ejecutorServicioACsvVTEXOMS(String cantidadDias) throws UnsupportedEncodingException{
		ConfiguracionEntidadParticular r= RecEntAct.getInstance().getCep();
		AbstractHTTP getVTEXGenerico= new GetVTEXOMSServicioACSV();
		String identificadorAtr=r.getIdentificadorAtributo();
		String cabeceraUrl=r.getFiltros().replaceAll(identificadorAtr+"NRO_PAG"+identificadorAtr, String.valueOf(r.getPaginaActual()));
		Integer cantDias=Integer.parseInt(cantidadDias);
		String parametroDate=getParametroDateOMG(cantDias);
		String parametrosFianl;
		if(cantDias==-1)
			parametrosFianl=cabeceraUrl;
		else
			parametrosFianl=cabeceraUrl+"&"+parametroDate;
	
		System.out.println(r.getUrl()+"?"+parametrosFianl);		
		return getVTEXGenerico.realizarPeticion(EPeticiones.GET,r.getUrl()+"?"+parametrosFianl,RecEntAct.getInstance().getCep().getCabecera());
	}
	
	
	public Object ejecutorServicioACsvVTEXMasterData(String cantidadDias) throws UnsupportedEncodingException{
		ConfiguracionEntidadParticular r= RecEntAct.getInstance().getCep();
		AbstractHTTP getGenerico= new GetVTEXMasterDataServicioACSV();
		String identificadorAtr=r.getIdentificadorAtributo();
		String cabeceraUrl=r.getFiltros().replaceAll(identificadorAtr+"NRO_PAG"+identificadorAtr, String.valueOf(r.getPaginaActual()));
		Integer cantDias=Integer.parseInt(cantidadDias);
		String parametroDate=getParametroDateMasterData(cantDias);
		String parametrosFianl;
		if(cantDias==-1)
			parametrosFianl=cabeceraUrl;
		else
			parametrosFianl=cabeceraUrl+"&"+parametroDate;
	
		System.out.println(r.getUrl()+"/search?"+parametrosFianl);		
		return getGenerico.realizarPeticion(EPeticiones.GET,r.getUrl()+"/search?"+parametrosFianl,RecEntAct.getInstance().getCep().getCabecera());
	}
	
	public Object ejecutorServicioAServicioVTEXOMS(String cantidadDias){
		ConfiguracionEntidadParticular r= RecEntAct.getInstance().getCep();
		AbstractHTTP getVTEXGenerico= new GetVTEXOMSServicioAServicio();
		String identificadorAtr=r.getIdentificadorAtributo();
		String cabeceraUrl=r.getFiltros().replaceAll(identificadorAtr+"NRO_PAG"+identificadorAtr, String.valueOf(r.getPaginaActual()));
		Integer cantDias=Integer.parseInt(cantidadDias);
		String parametroDate=getParametroDateOMG(cantDias);
		String parametrosFianl;
		if(cantDias==-1)
			parametrosFianl=cabeceraUrl;
		else
			parametrosFianl=cabeceraUrl+"&"+parametroDate;
	
		System.out.println(r.getUrl()+"?"+parametrosFianl);		
		return getVTEXGenerico.realizarPeticion(EPeticiones.GET,r.getUrl()+"?"+parametrosFianl,RecEntAct.getInstance().getCep().getCabecera());		
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
