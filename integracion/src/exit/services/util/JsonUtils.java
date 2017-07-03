package exit.services.util;

public class JsonUtils {

	public static String reemplazarCorcheteParaRegex(String cadena){
		return cadena.replaceAll("\\[", "\\\\[");
	}
}
