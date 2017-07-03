package exit.services.singletons;


public class RecEntAct {
	private static RecEntAct instance;
	private ConfiguracionEntidadParticular cep;

    private RecEntAct(){
    	cep= new ConfiguracionEntidadParticular(ApuntadorDeEntidad.getInstance().getEntidadActual());
    }
    
    void reiniciar(){
    	instance=null;
    }
    
	
    public static synchronized RecEntAct getInstance() {
    	if(instance==null)
    		instance=new RecEntAct();
    	return instance;
    }

	public ConfiguracionEntidadParticular getCep() {
		return cep;
	}

  
	

}
