package exit.services.excepciones;

public class ExceptionHashRepetido extends ExceptionBiactiva{
	
	private static final long serialVersionUID = 1L;
	
	
	public ExceptionHashRepetido(String fichero,String mensaje){
		super(fichero, mensaje);
	}
	public ExceptionHashRepetido(String fichero){
		super(fichero);
	}
	
	public ExceptionHashRepetido(){
		super();
	}
}
