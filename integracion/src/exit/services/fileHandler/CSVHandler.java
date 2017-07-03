package exit.services.fileHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import com.csvreader.CsvWriter;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.singletons.ConfiguracionEntidadParticular;
import exit.services.singletons.RecEntAct;

public class CSVHandler {
	
	public static String cabeceraFichero;
	public static final String PATH_ERROR_SERVER_NO_ALCANZADO="servidor_no_alcanzado.csv";
	public static final String PATH_ERROR_EXCEPTION="exception_ejecucion.csv";
	public static final String PATH_ERROR_VALIADACION_DATOS_CSV="error_validacion_datos.csv";
	public static final String PATH_ERROR_VALIADACION_DATOS_TXT="error_validacion_datos.txt";
	public static final String PATH_ID_NO_ENCONTRADO="id_no_encontrado.csv";
	public static final String PATH_ERROR_EXCEPTION_LOG="exception_ejecucion_log.txt";
	public static final String NRO_SAC_REPETIDO_EN_EL_CSV_EJECUTADO="nro_sac_repetido_en_el_csv_ejecutado.csv";
	public static final String PATH_INSERTADOS_OK="insertadosOK.csv";
	public static final String PATH_UPDATES_OK="actualizadosOK.csv";
	public static final String PATH_BORRADOS_OK="borradosOK.csv";
	public static final String PATH_LOG_GENERICO="log.txt";
	

		public static synchronized void crearCabecer(File file,String cabecera)  throws IOException{
            if(!file.exists() || file.length() == 0){
            		CsvWriter csvOutput = new CsvWriter(new FileWriter(file, true), RecEntAct.getInstance().getCep().getSeparadorCSVREGEX().charAt(0));
    				csvOutput.write(cabecera);
    	        	csvOutput.endRecord();
    	            csvOutput.close();
    		}
		}
		
		private void escribirCampos(File file, String line,boolean onlyNotNull) throws IOException{
		 	CsvWriter csvOutput = new CsvWriter(new FileWriter(file, true), RecEntAct.getInstance().getCep().getSeparadorCSV().charAt(0));
        	if(onlyNotNull){
			 	String[] campos= line.split(RecEntAct.getInstance().getCep().getSeparadorCSVREGEX());
	            for(String c:campos)
	            		csvOutput.write(insertarNoNull(c));             
        	}
            else{
			 	String[] campos= line.split(RecEntAct.getInstance().getCep().getSeparadorCSVREGEX(),-1);
	            for(String c:campos)
	            	csvOutput.write(insertarNoNull(c==null?"":c));   
            }
        	
            csvOutput.endRecord();		 
            csvOutput.close();
		}
		
/*		private synchronized void crearCabecer(File file)  throws IOException{
			crearCabecer(file,cabeceraFichero);
		}	*/
		
		 public void escribirCSV(File file,String line, String cabecera,boolean onlyNotNull) throws IOException{
	            crearCabecer(file,cabecera);
	            escribirCampos(file,line,onlyNotNull);
		 }
		 
		 public void escribirCSV(File file,String line, boolean tieneCabeceraDefault) throws IOException{
			 if(tieneCabeceraDefault)
				 escribirCSV(file,line,cabeceraFichero,true);
			 else
				 escribirCampos(file,line,true);
		 }
		 
		 public void escribirCSV(File file,String line) throws IOException{
			 escribirCSV(file,line,true);
		 }
		 
		 public void escribirCSV(String path,String line) throws IOException{
			 	escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicio(path),line);
		 }
		 
		 public void escribirCSV(String path,String line, String cabecera, boolean onlyNotNull) throws IOException{
			 	escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicio(path),line,cabecera, onlyNotNull);
		 }
		 
		 public void escribirCSV(String path,ConfiguracionEntidadParticular conf, String line, String cabecera, boolean onlyNotNull) throws IOException{
			 	escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicio(conf,path),line,cabecera, onlyNotNull);
		 }
		 
		 
		 public void escribirCSV(String path,String line,boolean cabeceraDefecto) throws IOException{
			 	escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicio(path),line,cabeceraDefecto);
		 }
		 public void escribirCSV(String path,AbstractJsonRestEstructura json) throws IOException{
			 escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicio(path),json.getLine(),true);
		 }
		 
/*		 private void insertarCampoVacio(CsvWriter csvOutput) throws IOException{
        	 csvOutput.write(insertarNoNull(""));
		 }*/
		 
		 public void escribirCSVErrorValidacion(String line, String error){
				try {
					this.escribirCSV(PATH_ERROR_VALIADACION_DATOS_CSV,line);
					this.escribirCSV(PATH_ERROR_VALIADACION_DATOS_TXT, line);
					this.escribirCSV(PATH_ERROR_VALIADACION_DATOS_TXT, error);
					this.escribirCSV(PATH_ERROR_VALIADACION_DATOS_TXT, ConstantesGenerales.SEPARADOR_ERROR_PETICION);					
				} catch (IOException e) {
					e.printStackTrace();
				}

		 }
		 
		 private String insertarNoNull(String cadena){
			 if(cadena!=null)
				 return cadena;
			 return "";
		 }
		 
		 public synchronized void escribirErrorException(StackTraceElement[] stackArray) {
			 escribirErrorException((String)null,stackArray,false);
		 }
		 
		 public synchronized void escribirErrorException(AbstractJsonRestEstructura json,StackTraceElement[] stackArray) {
			 escribirErrorException(json.getLine(),stackArray,true);
		 }
		 public synchronized void escribirErrorException(String linea) {
			 escribirErrorException(linea,null,false);
		 }
		 public synchronized void escribirErrorException(String line,StackTraceElement[] stackArray,boolean logueaEnCsv) {
				try {
					if(line!=null){
						if(logueaEnCsv)
							this.escribirCSV(PATH_ERROR_EXCEPTION,line);
						this.escribirCSV(PATH_ERROR_EXCEPTION_LOG,line);
					}
					if(stackArray!=null)
						for(StackTraceElement ste: stackArray){
						     this.escribirCSV(PATH_ERROR_EXCEPTION_LOG,"FileName: "+ste.getFileName()+" Metodo: "+ste.getMethodName()+"Clase "+ste.getClassName()+" Linea "+ste.getLineNumber(),false);
						}		
					this.escribirCSV(PATH_ERROR_EXCEPTION_LOG,ConstantesGenerales.SEPARADOR_ERROR_TRYCATCH);
					} catch (IOException e) {
					e.printStackTrace();
				}
	 }
}
