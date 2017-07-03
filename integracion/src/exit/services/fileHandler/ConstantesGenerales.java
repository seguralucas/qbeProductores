package exit.services.fileHandler;

import java.io.File;
import java.net.URISyntaxException;

import exit.services.principal.Principal;

public class ConstantesGenerales {
	 public static String SEPARADOR_ERROR_PETICION = "##############################################################################################";
	 public static String SEPARADOR_ERROR_JSON = "**********************************************************************************************";
	 public static String SEPARADOR_ERROR_TRYCATCH = "---------------------------------------------------------------------";
	 public static String PATH_EJECUCION;
	 public static String PATH_CONFIGURACION_ENTIDADES=PATH_EJECUCION+"/Configuracion/entidades";
	 public static String PATH_CONFIGURACION=PATH_EJECUCION+"/Configuracion";
	 public static String PATH_PETICIONES_GET=PATH_EJECUCION+"/Configuracion/peticiones/GET.properties";
	 public static String PATH_PETICIONES_UPDATE=PATH_EJECUCION+"/Configuracion/peticiones/UPDATE.properties";
	 public static String PATH_PETICIONES_DELETE=PATH_EJECUCION+"/Configuracion/peticiones/DELETE.properties";
	 public static String PATH_PETICIONES_POST=PATH_EJECUCION+"/Configuracion/peticiones/POST.properties";
	 public static String PATH_PETICIONES_ENTIDAD_GET=PATH_EJECUCION+"/Configuracion/@0/peticiones/GET.properties";
	 public static String PATH_PETICIONES_ENTIDAd_UPDATE=PATH_EJECUCION+"/Configuracion/@0/peticiones/UPDATE.properties";
	 public static String PATH_PETICIONES_ENTIDAD_DELETE=PATH_EJECUCION+"/Configuracion/@0/peticiones/DELETE.properties";
	 public static String PATH_PETICIONES_ENTIDAD_POST=PATH_EJECUCION+"/Configuracion/@0/peticiones/POST.properties";
	 
	 
	 
	 public static String reemplazarArrobas(String constante, String... valores){
		 for(int i=0;i<valores.length;i++){
			 constante=constante.replaceAll("@"+i, valores[i]);
		 }
		 return constante;
	 }
	 
	 static{
		 try {
			String aux=new File(ConstantesGenerales.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getAbsolutePath(); //Dos puntos porque te lo deja en /bin
			String auxs[]=aux.split("\\\\");
			if(auxs.length==1)
				auxs=aux.split("\\/");
			System.out.println(aux);
			PATH_EJECUCION="";
			for(int i=0;i<auxs.length-1;i++)
				PATH_EJECUCION+=auxs[i]+"/";
			PATH_EJECUCION=PATH_EJECUCION.substring(0, PATH_EJECUCION.length());
			System.out.println(PATH_EJECUCION);
			PATH_CONFIGURACION_ENTIDADES=PATH_EJECUCION+"/Configuracion/entidades";
			PATH_CONFIGURACION_ENTIDADES=PATH_EJECUCION+"/Configuracion/entidades";
			PATH_CONFIGURACION=PATH_EJECUCION+"/Configuracion";
			PATH_PETICIONES_GET=PATH_EJECUCION+"/Configuracion/peticiones/GET.properties";
			PATH_PETICIONES_UPDATE=PATH_EJECUCION+"/Configuracion/peticiones/UPDATE.properties";
			PATH_PETICIONES_DELETE=PATH_EJECUCION+"/Configuracion/peticiones/DELETE.properties";
			PATH_PETICIONES_POST=PATH_EJECUCION+"/Configuracion/peticiones/POST.properties";
			PATH_PETICIONES_ENTIDAD_GET=PATH_EJECUCION+"Configuracion/entidades/@0/peticiones/GET.properties";
			PATH_PETICIONES_ENTIDAd_UPDATE=PATH_EJECUCION+"Configuracion/entidades/@0/peticiones/UPDATE.properties";
			PATH_PETICIONES_ENTIDAD_DELETE=PATH_EJECUCION+"Configuracion/entidades/@0/peticiones/DELETE.properties";
			PATH_PETICIONES_ENTIDAD_POST=PATH_EJECUCION+"Configuracion/entidades/@0/peticiones/POST.properties";
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 
}
