package ejercicio3.ejercicios123.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import ejercicio3.ejercicios123.modelo.Cafe;


/**
 * @descrition
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public interface CafeDAO {

	/**
	 * Solución del Ejercicio 3
	 * Metodo que devuelve los cafes existentes para un proveedor
	 * @param con
	 * @throws SQLException
	 */
	public List<Cafe> getCafesPorProv(int id) throws AccesoDatosException;

	
	/**
	 * Transacción para transferir ventas de café
	 * 
	 * @param ventas
	 * @throws AccesoDatosException
	 */
	public void transferencia(String cafe1, String cafe2)
			throws AccesoDatosException;

	/**
	 * Metodo que devuelve los cafes existentes
	 * 
	 * @param con
	 * @throws SQLException
	 */
	public List<Cafe> verTabla() throws AccesoDatosException;
	
		/**
	 * Método que busca un cafe por nombre y devuelve sus datos
	 *
	 * @param nombre
	 */
	public Cafe buscar(String nombre) throws AccesoDatosException;

	/**
	 * Método para insertar una fila
	 * 
	 * @param cafe
	 */
	public void insertar(Cafe cafe) throws AccesoDatosException;

	/**
	 * Método para borrar una fila dado un nombre de café
	 * 
	 * @param nombre
	 * @return
	 */
	public void borrar(String nombre) throws AccesoDatosException;

	/**
	 * Método que actualiza los precios por un porcentaje dado
	 * 
	 * @param percentage
	 * @throws AccesoDatosException
	 */
	public void modificarPrecio(float percentage) throws AccesoDatosException;

	/**
	 * Método que inserta una nueva fila usando ResultSet
	 * 
	 * @param cafe
	 */
	public void insertarFila(Cafe cafe) throws AccesoDatosException;

	/**
	 * Método que actualiza las ventas y el total de cada cafe con una
	 * transacción
	 * 
	 * @param ventas
	 * @throws AccesoDatosException
	 */
	public void actualizarVentas(HashMap<String, Integer> ventas)
			throws AccesoDatosException;

	/**
	 * Método para cerrar el DAO
	 * 
	 * @throws AccesoDatosException
	 */

	public void cerrar();

}
