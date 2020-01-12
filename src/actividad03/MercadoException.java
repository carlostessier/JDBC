

package actividad03;
/**
 * 
 *  @descrition Clase Raiz para la jerarquia de Excepciones de mi aplicación
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */
 class MercadoException extends Exception{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3618070724207179230L;

	public MercadoException(String message) {
        super(message);
    }

}
