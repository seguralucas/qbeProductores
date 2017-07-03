package exit.services.principal;


import java.io.File;
import java.io.FileWriter;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;

import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.DirectorioManager;
import exit.services.principal.ejecutores.ParalelizadorDistintosFicheros;
import exit.services.singletons.ApuntadorDeEntidad;
import exit.services.singletons.ConfiguracionEntidadParticular;
import exit.services.singletons.EOutputs;
import exit.services.singletons.RecuperadorMapeoCsv;
import exit.services.singletons.RecEntAct;

public class Principal {
	public static final String UPDATE_CONTACTOS="UPDATE_CONTACTOS";
	public static final String INSERTAR_CONTACTOS="INSERTAR_CONTACTOS";
	public static final String INSERTAR_INCIDENTES="INSERTAR_INCIDENTES";
	public static final String BORRAR_INCIDENTES="BORRAR_INCIDENTES";
	

	
	public static void main(String[] args) throws Exception {
		long time_start, time_end;
    	time_start = System.currentTimeMillis();
    	ApuntadorDeEntidad ap=ApuntadorDeEntidad.getInstance();
    	if(ap==null)
    		return;
    	while(ap.siguienteEntidad()){
    		ConfiguracionEntidadParticular r= RecEntAct.getInstance().getCep();
	    	r.mostrarConfiguracion();
	    	switch(r.getAction().toUpperCase()){
	    		case ConfiguracionEntidadParticular.ACCION_CSVASERVICIO:csvAServicio();break;
	    		case ConfiguracionEntidadParticular.ACCION_SERVICIOAACSV:servicioACsv(); break;
	    		case ConfiguracionEntidadParticular.ACCION_SERVICIOASERVICIO:servicioAServicio(); break;
	    	}
	    }
	    	time_end = System.currentTimeMillis();
	    	System.out.println(ManagementFactory.getThreadMXBean().getThreadCount() );
	    	double tiempoDemorado=(time_end - time_start)/1000/60 ;
    		if(tiempoDemorado>1){
        		FileWriter fw = new FileWriter(DirectorioManager.getDirectorioFechaYHoraInicio("duracion.txt"));
    			fw.write("El proceso de updateo demoró un total de: "+tiempoDemorado+" minutos");
        		fw.close();
    		}    	
/***********************************************************/
		//***Borrar ficheros de ejecucion***/
/***********************************************************/
	//	FilesAProcesarManager.getInstance().deleteCSVAProcesar();
	}

	
	private static void csvAServicio(){
		ConfiguracionEntidadParticular r= RecEntAct.getInstance().getCep();
		ParalelizadorDistintosFicheros hiloApartre = new ParalelizadorDistintosFicheros();
      	try {
      		if(r.getMetodoPreEjecutor()!=null)
      			PreEjecutor.ejecutar(r.getMetodoPreEjecutor(), r.getParametroPreEjecutor());
      		hiloApartre.insertar();
      		if(r.isBorrarDataSetAlFinalizar()){
      			File file = new File(r.getPathCSVRegistros());
      			file.delete();
      		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private static void servicioACsv(){
		Ejecutor e= new Ejecutor();
		try{

			Integer cantRegistros=-1;
			ConfiguracionEntidadParticular r= RecEntAct.getInstance().getCep();
			if(r.isCreateEmptyFile())
				CSVHandler.crearCabecer(DirectorioManager.getDirectorioFechaYHoraInicio(r.getOutputFile()),r.getRecuperadorMapeoCSV().getCabecera() );
			if(r.isPaginado()){
				while(cantRegistros!=0){
					try{
						System.out.println("Pagina actual: "+r.getPaginaActual());					
						cantRegistros=(Integer)e.ejecutar(r.getMetodoEjecutor(),r.getParametroEjecutor());
						r.incresePaginaActual();
						}catch(Exception ex){CSVHandler csv= new CSVHandler(); csv.escribirErrorException("Error en entidad: "+ApuntadorDeEntidad.getInstance().getEntidadActual(), ex.getStackTrace(),true);}
				}
			}
			else{
				cantRegistros=(Integer)e.ejecutar(r.getMetodoEjecutor(),r.getParametroEjecutor());
			}
			if(r.getOutput()==EOutputs.SFTP){
				SFTP sftp= new SFTP(r.getSftpPropiedades());
				sftp.transferirFichero(DirectorioManager.getDirectorioFechaYHoraInicio(r.getOutputFile()).getPath(), r.getOutPutPath()+"/"+r.getOutputFile());
			}
			System.out.println("Fin");
		}
		catch (Exception d) {
			d.printStackTrace();
		}
	}
	
	private static void servicioAServicio(){
		Ejecutor e= new Ejecutor();
		try{
			Integer cantRegistros=-1;
			ConfiguracionEntidadParticular r= RecEntAct.getInstance().getCep();
//			System.out.println(RecEntAct.getInstance().getCep().getSubEntidad("contacto").getRecuperadorMapeoCSV().getCabecera());
			if(r.isPaginado()){
				while(cantRegistros!=0){
					try{
						System.out.println("Pagina actual: "+r.getPaginaActual());					
						cantRegistros=(Integer)e.ejecutar(r.getMetodoEjecutor(),r.getParametroEjecutor());
						r.incresePaginaActual();
						}catch(Exception ex){CSVHandler csv= new CSVHandler(); csv.escribirErrorException("Error en entidad: "+ApuntadorDeEntidad.getInstance().getEntidadActual(), ex.getStackTrace(),true);}
				}
			}
			else
				cantRegistros=(Integer)e.ejecutar(r.getMetodoEjecutor(),r.getParametroEjecutor());
			System.out.println("Fin");
		}
		catch (Exception d) {
			d.printStackTrace();
		}
	}
	
	
}
