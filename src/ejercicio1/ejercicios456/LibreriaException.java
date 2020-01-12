

package ejercicio1.ejercicios456;
/**
 * 
 *  @descrition Clase Raiz para la jerarquia de Excepciones de mi aplicación
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */
public class LibreriaException extends Exception{

    /**
	 * Necesario por impmentar Serializable
	 */
	private static final long serialVersionUID = 6308847858962342271L;

	public LibreriaException(String message) {
        super(message);
    }

}
