package exit.services.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Timer {

	GregorianCalendar c1;
    GregorianCalendar c2;
    DateFormat dateFormat;
	public Timer(){
		c1 = new GregorianCalendar();
	    c2 = new GregorianCalendar();
	    dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	}
	
	public void tomarTiempoUno(){
	    Calendar cal = Calendar.getInstance();
	    System.out.println(dateFormat.format(cal.getTime()));
	    String[] dateCadena=dateFormat.format(cal.getTime()).split(" ");
	    String[] amd=dateCadena[0].split("/");
	    String[] hms=dateCadena[1].split(":");
	    System.out.println(amd[0]+" "+amd[1]+" "+amd[2]+" "+hms[0]+" "+hms[1]+" "+hms[2]);
	    c1.set(Integer.parseInt(amd[0]),Integer.parseInt(amd[1]),Integer.parseInt(amd[2]),Integer.parseInt(hms[0]),Integer.parseInt(hms[1]),Integer.parseInt(hms[2]));
	}
	
	public void tomarTiempoDos(){
	    Calendar cal = Calendar.getInstance();
	    System.out.println(dateFormat.format(cal.getTime()));
	    String[] dateCadena=dateFormat.format(cal.getTime()).split(" ");
	    String[] amd=dateCadena[0].split("/");
	    String[] hms=dateCadena[1].split(":");
	    System.out.println(amd[0]+" "+amd[1]+" "+amd[2]+" "+hms[0]+" "+hms[1]+" "+hms[2]);
	    c2.set(Integer.parseInt(amd[0]),Integer.parseInt(amd[1]),Integer.parseInt(amd[2]),Integer.parseInt(hms[0]),Integer.parseInt(hms[1]),Integer.parseInt(hms[2]));
	}
	
	public long diferencia(){
		return c2.getTimeInMillis() - c1.getTimeInMillis();
	}
}
