package ejercicio3.ejercicios123.dao;

import ejercicio3.ejercicios123.modelo.Proveedor;

/**
 * @descrition Interfaz para DAOs de proveedores
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public interface ProveedorDAO {

	/**
	 * Método que busca un proveedor por identificador y devuelve sus datos
	 *
	 * @param nombre
	 */
	public Proveedor buscar(int identificador) throws AccesoDatosException;

	/**
	 * Método para insertar una fila
	 * 
	 * @param proveedor
	 */
	public void insertar(Proveedor proveedor) throws AccesoDatosException;

	/**
	 * Método para cerrar el DAO
	 * 
	 * @throws AccesoDatosException
	 */

	public void cerrar();

}
