package exit.services.fileHandler;

import java.io.File;
import java.util.ArrayList;

public class FilesAProcesarManager {

	
	private ArrayList<File> csvAProcesar;
	private static FilesAProcesarManager instance=null;
	
	public static synchronized FilesAProcesarManager getInstance(){
		if(instance==null)
			instance= new FilesAProcesarManager();
		return instance;
	}
	
	public ArrayList<File> getAllCSV(String path){
		ArrayList<File> paths = new ArrayList<File>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	String extension = listOfFiles[i].getName().substring(listOfFiles[i].getName().lastIndexOf(".") + 1, listOfFiles[i].getName().length());

		        if(extension.equalsIgnoreCase("csv"))
		        	paths.add(listOfFiles[i]);
		      } 
		    }
		    return paths;
		}
	
	
	public ArrayList<File> getCSVEjecutarAProcesar(String path){
		ArrayList<File> paths = new ArrayList<File>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
			       if(listOfFiles[i].getName().equalsIgnoreCase("ejecutar.csv")){
			        	paths.add(listOfFiles[i]);
			      } 
		      } 
		    }
		    this.csvAProcesar=paths;
		    return paths;
		}
	
	public ArrayList<File> getCSVAProcesar(String path){
		ArrayList<File> paths = new ArrayList<File>();
		File f = new File(path);
		if(f.exists())
			paths.add(f);
		return paths;
	}
	
	
	
	public void deleteCSVAProcesar(){
		for(File f: this.csvAProcesar){
			f.delete();
		}
	}	

}
