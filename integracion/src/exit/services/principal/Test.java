package exit.services.principal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Test {

	public static void main(String[] args) throws ParseException {
		
		/*Busca transformar la fecha del formato 2017-04-28T10:25:22.0000000+00:00
		al formato 2017-04-28T10 10:25:22*/
		String dateOMS="2017-04-28T10:25:22.0000000+00:00";
		String[] aux=dateOMS.split("T");
		String fecha=aux[0];
		String[] time=aux[1].split(":");
		String seconds=time[2].split("\\.")[0];
		System.out.println(fecha+" "+time[0]+":"+time[1]+":"+seconds);
	}

}
