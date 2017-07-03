package exit.services.excepciones;

public class ExceptionServidorNoAlcanzado extends ExceptionBiactiva {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public ExceptionServidorNoAlcanzado(String fichero,String mensaje){
			super(fichero, mensaje);
		}
		public ExceptionServidorNoAlcanzado(String fichero){
			super(fichero);
		}

}
