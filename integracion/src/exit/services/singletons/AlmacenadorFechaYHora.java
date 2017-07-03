package exit.services.singletons;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
	
	
public class AlmacenadorFechaYHora {
	private static String fechaYHoraInicio=null;
	
	private static String setFechaYHora(){
	   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
	   Calendar cal = Calendar.getInstance();
	   return dateFormat.format(cal.getTime()).toString();
	}
	public static String getFechaYHoraInicio(){
		if(fechaYHoraInicio==null)
			fechaYHoraInicio=setFechaYHora();
		return fechaYHoraInicio;
	}
	
	static void reiniciar(){
		fechaYHoraInicio=null;
	}
}
