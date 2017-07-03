package exit.services.excepciones;

public class ExceptionLongitud extends ExceptionBiactiva{

	private static final long serialVersionUID = 1L;
	
	public ExceptionLongitud(String fichero,String mensaje){
		super(fichero, mensaje);
	}
	public ExceptionLongitud(String fichero){
		super(fichero);
	}

}
