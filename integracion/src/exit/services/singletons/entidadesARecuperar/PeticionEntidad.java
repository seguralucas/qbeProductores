package exit.services.singletons.entidadesARecuperar;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import exit.services.fileHandler.ConstantesGenerales;
import exit.services.principal.peticiones.EPeticiones;

public class PeticionEntidad implements IPeticiones{
	private static PeticionEntidad instance;

	HashMap<EPeticiones, Peticion> peticion;
    private PeticionEntidad(String nombreEntidad) throws FileNotFoundException, IOException{
    	peticion= new HashMap<EPeticiones, Peticion>();
        Properties props = new Properties();
    		props.load(new FileReader(ConstantesGenerales.reemplazarArrobas(ConstantesGenerales.PATH_PETICIONES_ENTIDAD_GET, nombreEntidad)));
    		peticion.put(EPeticiones.GET, new Peticion(props.getProperty("metodo"), Integer.parseInt(props.getProperty("codigoResponseEsperado")), props.getProperty("cabecera")));
    		props.load(new FileReader(ConstantesGenerales.reemplazarArrobas(ConstantesGenerales.PATH_PETICIONES_ENTIDAD_POST, nombreEntidad)));
    		peticion.put(EPeticiones.POST,new Peticion(props.getProperty("metodo"), Integer.parseInt(props.getProperty("codigoResponseEsperado")), props.getProperty("cabecera")));
    		props.load(new FileReader(ConstantesGenerales.reemplazarArrobas(ConstantesGenerales.PATH_PETICIONES_ENTIDAd_UPDATE, nombreEntidad)));
    		peticion.put(EPeticiones.UPDATE,new Peticion(props.getProperty("metodo"), Integer.parseInt(props.getProperty("codigoResponseEsperado")), props.getProperty("cabecera")));
    		props.load(new FileReader(ConstantesGenerales.reemplazarArrobas(ConstantesGenerales.PATH_PETICIONES_ENTIDAD_DELETE, nombreEntidad)));
    		peticion.put(EPeticiones.DELETE,new Peticion(props.getProperty("metodo"), Integer.parseInt(props.getProperty("codigoResponseEsperado")), props.getProperty("cabecera")));
    }
    
    void reiniciar(){
    	instance=null;
    }

	public synchronized static IPeticiones getInstance() {
		return RecuperadorPeticiones.getInstance();
    }

	public synchronized static IPeticiones getInstance(String nombre) throws FileNotFoundException, IOException {
    	return instance=new PeticionEntidad(nombre);	
    }


	public Peticion getPeticion(EPeticiones ePeticion){
		return peticion.get(ePeticion);
	}
}
