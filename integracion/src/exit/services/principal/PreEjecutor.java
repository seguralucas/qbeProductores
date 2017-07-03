package exit.services.principal;

import java.lang.reflect.Method;

import exit.services.excepciones.ExceptionBiactiva;
import exit.services.fileHandler.CSVHandler;
import exit.services.principal.peticiones.EPeticiones;
import exit.services.principal.peticiones.GetIdsAEliminar;
import exit.services.singletons.ApuntadorDeEntidad;
import exit.services.singletons.RecEntAct;

public class PreEjecutor {

	public static void borrarEntidadRightNow() {
		Integer cantidad=-1;
		System.out.println("Borrando "+ApuntadorDeEntidad.getInstance().getEntidadActual()+".");
		while(cantidad>0 || cantidad==-1){
			GetIdsAEliminar g= new GetIdsAEliminar();
			Object o=g.realizarPeticion(EPeticiones.GET,RecEntAct.getInstance().getCep().getCabecera());
			if(o instanceof Integer)
				cantidad=(Integer)o;
			else
				cantidad=-777;
		}
		System.out.println("Borrado finalizado.");
	}
	

	public static Object ejecutar(String nombreMetodo) throws ExceptionBiactiva{
		ApuntadorDeEntidad.getInstance().siguienteEntidad();
		return ejecutar(nombreMetodo,null);
	}
	

	
	public static Object ejecutar(String nombreMetodo, String parametros) throws ExceptionBiactiva{
		Class<PreEjecutor> a= PreEjecutor.class;
		try {
			Method m;
			Object o;
			if(parametros!=null){
				m= a.getMethod(nombreMetodo, parametros.getClass());
				o=m.invoke(null,parametros);
			}
			else{
				m=a.getMethod(nombreMetodo);
				o=m.invoke(null);
			}
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			CSVHandler csv= new CSVHandler();
			csv.escribirErrorException(e.getStackTrace());
			throw new ExceptionBiactiva("Error al ejecutar pre-ejecutor");
		} 
	}
}
