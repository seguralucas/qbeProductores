package exit.services.excepciones;

public class ExceptionBiactiva extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String ficheroErrorExcepcion;
	
	public ExceptionBiactiva(String fichero) {
		setFicheroErrorExcepcion(fichero);
	}
	public ExceptionBiactiva() {
		super();
	}
	public ExceptionBiactiva(String fichero, String mensaje) {
		super(mensaje);
		setFicheroErrorExcepcion(fichero);
	}
	
	public String getFicheroErrorExcepcion() {
		return ficheroErrorExcepcion;
	}

	public void setFicheroErrorExcepcion(String ficheroErrorExcepcion) {
		this.ficheroErrorExcepcion = ficheroErrorExcepcion;
	}

	
}
