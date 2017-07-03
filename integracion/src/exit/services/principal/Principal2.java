package exit.services.principal;

import exit.services.principal.peticiones.EPeticiones;
import exit.services.principal.peticiones.vtex.borrado.GetDniContacts;
import exit.services.principal.peticiones.vtex.borrado.GetVtexIdProductos;
import exit.services.singletons.ApuntadorDeEntidad;
import exit.services.singletons.RecEntAct;

public class Principal2 {

	public static void main(String[] args) {
    	ApuntadorDeEntidad ap=ApuntadorDeEntidad.getInstance();
    	if(ap==null)
    		return;
    	Integer cont=-1;
    	ap.siguienteEntidad();
    	Integer offset=0;
    	Integer tam=20000;
    	while(cont!=0){
    		GetDniContacts v= new GetDniContacts();
    		cont=(Integer)v.realizarPeticion(EPeticiones.GET, "https://whirlpool.custhelp.com/services/rest/connect/v1.3/queryResults/?query=select%20distinct(customFields.Whirlpool.numero_documento)%20from%20contacts%20where%20customFields.Whirlpool.numero_documento%20is%20not%20null%20limit%20"+tam+"%20offset%20"+offset, null, null, RecEntAct.getInstance().getCep().getSubEntidad("contacto").getCabecera(), RecEntAct.getInstance().getCep().getSubEntidad("contacto"));
    		System.out.println("Fuera: "+cont);
    		offset+=tam;
    	}
   	}
}

