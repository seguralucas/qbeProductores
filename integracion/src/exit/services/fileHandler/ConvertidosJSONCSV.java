package exit.services.fileHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.convertidos.csvAJson.JsonGenerico;
import exit.services.singletons.ConfiguracionEntidadParticular;
import exit.services.singletons.RecEntAct;


public class ConvertidosJSONCSV{
    	private String line = "";
    	private CSVHandler csv;
		private BufferedReader br=null;
    	private boolean esPrimeraVez=true;
    	private boolean fin=false;
    	
    	
		public ConvertidosJSONCSV(){
            csv = new CSVHandler();
		}
		
	   
	   public AbstractJsonRestEstructura convertirCSVaJSONLineaALinea(File fileCSV) {
			ConfiguracionEntidadParticular r= RecEntAct.getInstance().getCep();
		   try{
			   if(br==null)
			   br = new BufferedReader(
		  		         new InputStreamReader(
		  		                 new FileInputStream(fileCSV)));
  		while ((line = br.readLine()) != null) {
  			if(this.esPrimeraVez){
  				String firstChar=String.valueOf(line.charAt(0));
  				if(!firstChar.matches("[a-zA-Z]"))
  					line=line.substring(1);//Ocasionalmente el primer caracter erra un signo raro y hay que eliminarlo.
  				this.esPrimeraVez=false;
  				CSVHandler.cabeceraFichero=line;//Esto es sólo en caso de que estemos haciendo update
  			}
  			else{
  	    		String[] valoresCsv= line.replace("\"", "'").split(r.getSeparadorCSVREGEX());
				try{
  					if(ColumnasMayorCabecera(valoresCsv))
  						throw new Exception();
  					AbstractJsonRestEstructura jsonEstructura=crearJson(valoresCsv,CSVHandler.cabeceraFichero.split(r.getSeparadorCSVREGEX()));  	
  					jsonEstructura.setLine(line);
    			return jsonEstructura;
  				}
  				catch(Exception e){
  					e.printStackTrace();
  					csv.escribirCSV("error_parser.csv", line,true);
  					return null;
  				}
  			}
  		}
  		br.close();
  		this.fin=true;
      }
      catch(IOException e){
      	e.printStackTrace();
  		this.fin=true;
			return null;
      }	   
 			return null;
	   }
			   
	   public AbstractJsonRestEstructura crearJson(String[] valoresCsv, String[] cabeceras) throws Exception{
		   AbstractJsonRestEstructura restEstructura= new JsonGenerico(RecEntAct.getInstance().getCep());
		   for(int i=0;i<valoresCsv.length;i++){
			   restEstructura.agregarCampo(cabeceras[i], valoresCsv[i]);
		   }
		   return restEstructura;
	   }

	   
	   private boolean ColumnasMayorCabecera(String[] valoresCsv){
		   return CSVHandler.cabeceraFichero.split(RecEntAct.getInstance().getCep().getSeparadorCSVREGEX()).length<valoresCsv.length;
	   }
	   
	   	   
	   public boolean isFin() {
		return fin;
	}

}
