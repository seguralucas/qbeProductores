package exit.services.singletons.entidadesARecuperar;

public class Peticion {
	private String peticion;
	private int codigoResponseEsperado;
	private String cabecera;
		
	public Peticion(String peticion, int codigoResponseEsperado, String cabecera) {
		super();
		this.peticion = peticion;
		this.codigoResponseEsperado = codigoResponseEsperado;
		this.cabecera = cabecera;
	}
	
	public String getPeticion() {
		return peticion;
	}
	public void setPeticion(String peticion) {
		this.peticion = peticion;
	}
	public int getCodigoResponseEsperado() {
		return codigoResponseEsperado;
	}
	public void setCodigoResponseEsperado(int codigoResponseEsperado) {
		this.codigoResponseEsperado = codigoResponseEsperado;
	}
	public String getCabecera() {
		return cabecera;
	}
	public void setCabecera(String cabecera) {
		this.cabecera = cabecera;
	}
	
	
}
