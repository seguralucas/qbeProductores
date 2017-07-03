package exit.services.singletons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.ConstantesGenerales;
import exit.services.util.ConvertidorJson;

public class RecuperadorMapeoCsv {


	private String cabecera;
	private String cuerpo;
	
	
	public String getCabecera() {
		return cabecera;
	}


	public void setCabecera(String cabecera) {
		this.cabecera = cabecera;
	}


	public String getCuerpo() {
		return cuerpo;
	}


	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}


	public RecuperadorMapeoCsv(String name) throws IOException{
		File f= new File(ConstantesGenerales.PATH_CONFIGURACION_ENTIDADES+"/"+name+"/mapeoCSV.csv");
		CSVHandler csv= new CSVHandler();
		BufferedReader br= new BufferedReader(new FileReader(f));
			String line;
			int nroLine=0;
			while((line=br.readLine()) != null){
				if(nroLine==0)
					this.cabecera=line;
				else if(nroLine==1)
					this.cuerpo=line;
				else
					csv.escribirErrorException("El fichero mapeo.csv posee mas de 2 lineas. Se ignorara lineas siguientes");
				nroLine++;
			}
			br.close();
	}


}
