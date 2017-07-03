package exit.services.singletons.entidadesARecuperar;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import exit.services.fileHandler.ConstantesGenerales;
import exit.services.fileHandler.DirectorioManager;
import exit.services.principal.peticiones.EPeticiones;
import exit.services.singletons.RecuperadorPropiedadesConfiguracionGenerales;


public class RecuperadorPeticiones implements IPeticiones{
	HashMap<String, Peticion> mapPeticiones;
	private static RecuperadorPeticiones instance;
	HashMap<EPeticiones, Peticion> peticion;

    private RecuperadorPeticiones(){
        Properties props = new Properties();
        try{
        	peticion= new HashMap<EPeticiones, Peticion>();
    		props.load(new FileReader(ConstantesGenerales.PATH_PETICIONES_GET));
    		peticion.put(EPeticiones.GET, new Peticion(props.getProperty("metodo"), Integer.parseInt(props.getProperty("codigoResponseEsperado")), props.getProperty("cabecera")));
    		props.load(new FileReader(ConstantesGenerales.PATH_PETICIONES_POST));
    		peticion.put(EPeticiones.POST, new Peticion(props.getProperty("metodo"), Integer.parseInt(props.getProperty("codigoResponseEsperado")), props.getProperty("cabecera")));
    		props.load(new FileReader(ConstantesGenerales.PATH_PETICIONES_UPDATE));
    		peticion.put(EPeticiones.UPDATE, new Peticion(props.getProperty("metodo"), Integer.parseInt(props.getProperty("codigoResponseEsperado")), props.getProperty("cabecera")));
    		props.load(new FileReader(ConstantesGenerales.PATH_PETICIONES_DELETE));
    		peticion.put(EPeticiones.DELETE, new Peticion(props.getProperty("metodo"), Integer.parseInt(props.getProperty("codigoResponseEsperado")), props.getProperty("cabecera")));
        }
        catch(Exception e){
        	e.printStackTrace();
        	try(FileWriter fw= new FileWriter(DirectorioManager.getDirectorioFechaYHoraInicio("error.txt"))){
				fw.write("Error al recuperar las propierdades del fichero: ConfiguracionGeneral.properties");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
    }
    
    void reiniciar(){
    	instance=null;
    }

	public synchronized static IPeticiones getInstance() {
    	if(instance==null)
    		instance=new RecuperadorPeticiones();
    	return instance;	}


	public Peticion getPeticion(EPeticiones ePeticion){
		return peticion.get(ePeticion);
	}
    
    
    
}
