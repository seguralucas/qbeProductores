package exit.services.principal.peticiones.vtex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;
import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.DirectorioManager;
import exit.services.principal.peticiones.AbstractHTTP;
import exit.services.util.ConvertidorJson;
import exit.services.util.RecorrerJson;
import exit.services.singletons.ConfiguracionEntidadParticular;
import exit.services.singletons.RecEntAct;

public abstract class GetVTEXAbstractServicioACSV  extends AbstractHTTP{
	
	@Override
	protected Object procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json, String id, int responseCode)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json, String id, int responseCode)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, String id, int responseCode) throws Exception {
		ConfiguracionEntidadParticular r=RecEntAct.getInstance().getCep();
		FuncionesVTEX fvtex= new FuncionesVTEX();
		JSONObject jsonObject = ConvertidorJson.convertir(in);
		String[] buscar=r.getRecuperadorMapeoCSV().getCuerpo().split(r.getSeparadorCSVREGEX());
		ArrayList<Object> listaObjetos=new ArrayList<Object>();
		for(int i=0;i<buscar.length;i++){
			listaObjetos.add(RecorrerJson.getValue(buscar[i],jsonObject));
		}
		String[] cabeceras=r.getRecuperadorMapeoCSV().getCabecera().split(RecEntAct.getInstance().getCep().getSeparadorCSVREGEX());		
		StringBuilder sb = new StringBuilder();
		int i=0;
		for(Object o:listaObjetos){
			String value = "";
			if(o!=null){
				if(cabeceras.length>i)
					value=fvtex.procesarPorFunciones(o,cabeceras[i]);
				else
					value=o.toString();
				sb.append(value+RecEntAct.getInstance().getCep().getSeparadorCSV());
			}
			else
				sb.append(RecEntAct.getInstance().getCep().getSeparadorCSV());
			i++;
		}
		String lineaAGuardar=sb.toString();
		if(lineaAGuardar.length()!=0)
			lineaAGuardar=lineaAGuardar.substring(0, lineaAGuardar.length()-1);
		CSVHandler csv= new CSVHandler();
		csv.escribirCSV(RecEntAct.getInstance().getCep().getOutputFile(), lineaAGuardar,r.getRecuperadorMapeoCSV().getCabecera(),false);		
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, String id, int responseCode) throws Exception {
		String path=("error_get_lista_"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        out.close();
        return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, int responseCode) throws Exception {
		JSONArray jsonArrayItems;
		if(RecEntAct.getInstance().getCep().getIterarSobre()!=null){
			JSONObject jsonObject = ConvertidorJson.convertir(in);
			jsonArrayItems= (JSONArray) jsonObject.get(RecEntAct.getInstance().getCep().getIterarSobre());
		}
		else
			jsonArrayItems=ConvertidorJson.convertirArray(in);
		ExecutorService workers = Executors.newFixedThreadPool(RecEntAct.getInstance().getCep().getNivelParalelismo());
	    List<Callable<Void>> tasks = new ArrayList<>();
		System.out.println("Subiendo el paralelismo a "+RecEntAct.getInstance().getCep().getNivelParalelismo());
		for(int i=0;i<jsonArrayItems.size();i++){
			final Integer j=i;
			final GetVTEXAbstractServicioACSV getVTEXAbstract= this;
			tasks.add(new Callable<Void>() {
		        public Void call() {
    		JSONObject jsonItem=(JSONObject)jsonArrayItems.get(j);
			String id=(String)jsonItem.get(RecEntAct.getInstance().getCep().getIdIteracion());
			getVTEXAbstract.realizarRequestAbstract(RecEntAct.getInstance().getCep().getUrl(), id);
			return null;
		        }
			});
		}
		System.out.println(jsonArrayItems.size());
	    workers.invokeAll(tasks);
	    workers.shutdown();
		return jsonArrayItems.size(); //Devuelve la cantidad de registros encontradas
	}
	
	abstract Object realizarRequestAbstract(String url, String id);
	
	@Override
	protected Object procesarPeticionError(BufferedReader in, int responseCode) throws Exception {
		String path=("error_get_lista_"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        out.close();
		return 0;
	}
}
