package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;

import org.json.simple.JSONObject;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.fileHandler.CSVHandler;
import exit.services.singletons.ApuntadorDeEntidad;
import exit.services.singletons.ConfiguracionEntidadParticular;
import exit.services.singletons.RecEntAct;
import exit.services.singletons.entidadesARecuperar.IPeticiones;
import exit.services.singletons.entidadesARecuperar.Peticion;
import exit.services.singletons.entidadesARecuperar.RecuperadorPeticiones;

public abstract class AbstractHTTP {
	public static final String PROPIEDADES_EXTRA="propiedadesExtras";

	@Deprecated
	public Object realizarPeticion(EPeticiones httpMetodo,JSONObject cabecera){
		return realizarPeticion(httpMetodo,RecEntAct.getInstance().getCep().getUrl(),null,null,cabecera,null);
	}
	
	public Object realizarPeticion(EPeticiones httpMetodo, AbstractJsonRestEstructura json,JSONObject cabecera,ConfiguracionEntidadParticular conf){
		return realizarPeticion(httpMetodo,RecEntAct.getInstance().getCep().getUrl(),null,json,cabecera,conf);
	}
	public Object realizarPeticion(EPeticiones httpMetodo, AbstractJsonRestEstructura json,JSONObject cabecera){
		return realizarPeticion(httpMetodo,RecEntAct.getInstance().getCep().getUrl(),null,json,cabecera,null);
	}

	public Object realizarPeticion(EPeticiones httpMetodo,String url,JSONObject cabecera, ConfiguracionEntidadParticular conf){
		return realizarPeticion(httpMetodo,url,null,null,cabecera,conf);
	}
	
	public Object realizarPeticion(EPeticiones httpMetodo,String url,JSONObject cabecera){
		return realizarPeticion(httpMetodo,url,null,null,cabecera,null);
	}
		
	public Object realizarPeticion(EPeticiones httpMetodo, String url, AbstractJsonRestEstructura json,JSONObject cabecera, ConfiguracionEntidadParticular conf){
		 return realizarPeticion(httpMetodo,url,null,json,cabecera,conf);
	 }
	public Object realizarPeticion(EPeticiones httpMetodo, String url, AbstractJsonRestEstructura json,JSONObject cabecera){
		 return realizarPeticion(httpMetodo,url,null,json,cabecera,null);
	 }
	 
	public Object realizarPeticion(EPeticiones httpMetodo, String url,String id,JSONObject cabecera, ConfiguracionEntidadParticular conf){
		 return realizarPeticion(httpMetodo,url,id,null,cabecera,conf);
	}
	public Object realizarPeticion(EPeticiones httpMetodo, String url,String id,JSONObject cabecera){
		 return realizarPeticion(httpMetodo,url,id,null,cabecera,null);
	}
	
	public Object realizarPeticion(EPeticiones httpMetodo, String url,String id,AbstractJsonRestEstructura json, JSONObject cabecera, ConfiguracionEntidadParticular conf){
	        try{
	            Peticion pet= conf==null?RecuperadorPeticiones.getInstance().getPeticion(httpMetodo):conf.getPeticiones().getPeticion(httpMetodo);
	        	WSConector ws;
	        	if(id!=null)
	        		 ws = new WSConector(httpMetodo,url+"/"+id,cabecera,pet);
	        	else
	        		 ws = new WSConector(httpMetodo,url,cabecera,pet);
	        	HttpURLConnection conn=ws.getConexion();
	        	if(json!=null){
		        	DataOutputStream wr = new DataOutputStream(
		        			conn.getOutputStream());
		        	wr.write(json.toStringNormal().getBytes("UTF-8"));
		        	wr.flush();
		        	wr.close();
	        	}
	            int responseCode = conn.getResponseCode();
	            BufferedReader in;
	            Object o;
	            if(responseCode == pet.getCodigoResponseEsperado()){
	            	in = new BufferedReader(
		                    new InputStreamReader(conn.getInputStream()));
	            	if(id!=null){
	            		if(json!=null)
	            			o=procesarPeticionOK(in, json, id,responseCode);
	            		else
	            			o=procesarPeticionOK(in, id,responseCode);
	            	}
	            	else{
	            		if(json!=null)
	            			o=procesarPeticionOK(in, json, responseCode);
	            		else
	            			o=procesarPeticionOK(in, responseCode);
	            	}
	            }
	            else{
	            	in = new BufferedReader(
		                    new InputStreamReader(conn.getErrorStream()));
	            	if(id!=null)
	            		o=procesarPeticionError(in,id,responseCode);
	            	else if( json!=null)
	            		o=procesarPeticionError(in,json,responseCode);
	            	else 
	            		o=procesarPeticionError(in,responseCode);
	            }
	            return o;	 
	            }	                
          catch (ConnectException e) {
				CSVHandler csv= new CSVHandler();
				try {
					csv.escribirCSV(CSVHandler.PATH_ERROR_SERVER_NO_ALCANZADO, url+"/"+id==null?"":id);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return null;
			}
          catch (Exception e) {
        	  e.printStackTrace();
				CSVHandler csv= new CSVHandler();
				if(id!=null)
					csv.escribirErrorException("Error al realizar request "+ url+"/"+id==null?"":id+" de la entidad "+ApuntadorDeEntidad.getInstance().getEntidadActual(),e.getStackTrace(),false);
				return null;
			}
      }
	
	abstract protected Object procesarPeticionOK(BufferedReader in, String id,int responseCode) throws Exception;
	abstract protected Object procesarPeticionError(BufferedReader in, String id, int responseCode) throws Exception;
	abstract protected  Object procesarPeticionOK(BufferedReader in, int responseCode) throws Exception;
	abstract protected Object procesarPeticionError(BufferedReader in, int responseCode) throws Exception;
	abstract protected  Object procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json, int responseCode) throws Exception;
	abstract protected Object procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json,int responseCode) throws Exception;
	abstract protected Object procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json, String id,int responseCode) throws Exception;
	abstract protected Object procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json, String id, int responseCode) throws Exception;

}
