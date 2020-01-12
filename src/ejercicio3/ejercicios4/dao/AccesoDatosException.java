package ejercicio3.ejercicios4.dao;

import ejercicio3.ejercicios4.modelo.MatriculaException;

/**
 * Excepción lanzada cuando ocurre algun error en el acceso a la capa
 * persistente de datos(ficheros, base de datos...)
 * @see
 */
public class AccesoDatosException extends MatriculaException{

    /**
	 * Necesario por impmentar Serializable
	 */
	private static final long serialVersionUID = 381077868696070244L;

	public AccesoDatosException(String message) {
        super(message);
    }

}
