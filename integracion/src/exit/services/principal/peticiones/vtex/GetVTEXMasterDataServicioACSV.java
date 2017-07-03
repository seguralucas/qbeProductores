package exit.services.principal.peticiones.vtex;

import exit.services.principal.peticiones.EPeticiones;
import exit.services.singletons.RecEntAct;

public class GetVTEXMasterDataServicioACSV extends GetVTEXAbstractServicioACSV{

	@Override
	Object realizarRequestAbstract(String url, String id) {
		return this.realizarPeticion(EPeticiones.GET, url+"/documents", id+"?_fields=_all",RecEntAct.getInstance().getCep().getCabecera());
	}

	
}
