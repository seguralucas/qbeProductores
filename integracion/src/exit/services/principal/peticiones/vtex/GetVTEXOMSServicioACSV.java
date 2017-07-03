package exit.services.principal.peticiones.vtex;

import exit.services.principal.peticiones.EPeticiones;
import exit.services.singletons.RecEntAct;


	
public class GetVTEXOMSServicioACSV extends GetVTEXAbstractServicioACSV{

	@Override
	Object realizarRequestAbstract(String url, String id) {
		return this.realizarPeticion(EPeticiones.GET,url, id,RecEntAct.getInstance().getCep().getCabecera());
	}


}
