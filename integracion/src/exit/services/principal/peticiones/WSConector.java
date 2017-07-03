package exit.services.principal.peticiones;


import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.json.simple.JSONObject;

import exit.services.singletons.RecEntAct;
import exit.services.singletons.entidadesARecuperar.IPeticiones;
import exit.services.singletons.entidadesARecuperar.Peticion;
import exit.services.singletons.entidadesARecuperar.RecuperadorPeticiones;
import exit.services.util.ConvertidorJson;



public class WSConector {
	 private HttpURLConnection conexion;
	 
	 private URL url;
	
	 
	 public WSConector(EPeticiones httpMethod,String url,JSONObject cabecera, Peticion peticion) throws Exception{
		 this.url = new URL(url);
		 if(peticion==null)
			 peticion=RecuperadorPeticiones.getInstance().getPeticion(httpMethod);
		 initConecction(httpMethod,peticion,cabecera);
	 }	 	
	 
	 public WSConector(EPeticiones httpMethod,String url,JSONObject cabecera) throws Exception{
		 this.url = new URL(url);
		 initConecction(httpMethod,RecuperadorPeticiones.getInstance().getPeticion(httpMethod),cabecera);
	 }	 	
	 public WSConector(EPeticiones httpMethod,String url) throws Exception{
		 	this(httpMethod,url,null);
	 }
	
	 
	 public WSConector(EPeticiones httpMethod) throws Exception{
		 	this(httpMethod,RecEntAct.getInstance().getCep().getUrl());
	 }
	
	private void initConecction(EPeticiones httpMethod, Peticion peticion, JSONObject cabecera) throws Exception{
		HttpURLConnection conn=null;
		if(RecEntAct.getInstance().getCep().getUsaProxy().equalsIgnoreCase("SI")){
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(RecEntAct.getInstance().getCep().getIpProxy(), Integer.parseInt(RecEntAct.getInstance().getCep().getPuertoProxy())));
			conn = (HttpURLConnection) url.openConnection(proxy);
		}
		else
			conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(peticion.getPeticion());
		if(peticion.getCabecera()!=null && peticion.getCabecera().length()>0)
			try{completarCabecera(conn,ConvertidorJson.convertir(peticion.getCabecera()));}	catch (Exception e) {e.printStackTrace();}

		conn.setRequestProperty("charset", "UTF-8");
		conn.setDoOutput(true);
		if(cabecera!=null)
			completarCabecera(conn,cabecera);
		this.conexion= conn;
		
	}
	
	private void completarCabecera(HttpURLConnection conn,JSONObject json) throws Exception{
		for (Object key : json.keySet()) {
			if(key instanceof String && json.get(key) instanceof String)
				conn.setRequestProperty((String) key, (String)json.get(key));
			
		}
	}

	public HttpURLConnection getConexion() {
		return conexion;
	}
	 
	

	 
	 
	 
	 
	
}
