package actividad03;

/**
 * Excepción lanzada cuando ocurre algun error en el acceso a la capa
 * persistente de datos(ficheros, base de datos...)
 * @see
 */
 class AccesoDatosException extends MercadoException{

  
	/**
	 * 
	 */
	private static final long serialVersionUID = 2280177977740484034L;

	public AccesoDatosException(String message) {
        super(message);
    }

}
