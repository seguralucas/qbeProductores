package exit.services.principal.ejecutores;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.fileHandler.ConvertidosJSONCSV;
import exit.services.fileHandler.DirectorioManager;
import exit.services.fileHandler.FilesAProcesarManager;
import exit.services.principal.Ejecutor;
import exit.services.singletons.RecEntAct;
import exit.services.util.Contador;




public class ParalelizadorDistintosFicheros {
	public static int y=0;
	public static int z=0;
	public void insertar() throws InterruptedException, IOException{
	 	ArrayList<File> pathsCSVEjecutar= FilesAProcesarManager.getInstance().getCSVAProcesar(RecEntAct.getInstance().getCep().getPathCSVRegistros());
	 	for(File path:pathsCSVEjecutar){
		 	try {
				DirectorioManager.SepararFicheros(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ArrayList<File> filesCSVDivididos=FilesAProcesarManager.getInstance().getAllCSV(DirectorioManager.getPathFechaYHoraInicioDivision());
	    	ExecutorService workers = Executors.newFixedThreadPool(RecEntAct.getInstance().getCep().getNivelParalelismo());      	
		    List<Callable<Void>> tasks = new ArrayList<>();
			for(File file: filesCSVDivididos){
				tasks.add(new Callable<Void>() {
			        public Void call() {
			        	try {
				        	ConvertidosJSONCSV csvThread= new ConvertidosJSONCSV();
			        		AbstractJsonRestEstructura jsonEst=null;
			        		while(!csvThread.isFin()){
			        			jsonEst = csvThread.convertirCSVaJSONLineaALinea(file);
			        			if(jsonEst != null && jsonEst.validarCampos()){
				        			Ejecutor e= new Ejecutor();
				        			e.ejecutar(RecEntAct.getInstance().getCep().getMetodoEjecutor(),RecEntAct.getInstance().getCep().getParametroEjecutor(),jsonEst);
			        			}
			    				Contador.x++;
			    				System.out.println(Contador.x);
			    				if(Contador.x%1000==0){
			    			  		FileWriter fw = new FileWriter(DirectorioManager.getDirectorioFechaYHoraInicio("cantidadProcesada.txt"));
			    		    		fw.write("el proceso lleva procesado un total de: "+Contador.x+" Registros");
			    		    		fw.close();
			    		    		System.out.println("el proceso lleva procesado un total de: "+Contador.x+" Registros");
			    				}
			        		}
			        	}
			        	catch (Exception e) {
							e.printStackTrace();
						}
						return null;
			        }
			});
		}
		    workers.invokeAll(tasks);
		    workers.shutdown();
	}
	}
}
