package exit.services.procesadoresRespuesta;

import java.io.BufferedReader;

import exit.services.convertidos.csvAJson.AbstractJsonRestEstructura;

public interface IProcesarRespuestaREST {
	public void procesarPeticionOK(BufferedReader in, AbstractJsonRestEstructura json,int responseCode) throws Exception;
    public void procesarPeticionError(BufferedReader in, AbstractJsonRestEstructura json, int responseCode) throws Exception;
}
