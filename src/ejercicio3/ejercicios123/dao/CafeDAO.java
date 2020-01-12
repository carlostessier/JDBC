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
	 * Soluci�n del Ejercicio 3
	 * Metodo que devuelve los cafes existentes para un proveedor
	 * @param con
	 * @throws SQLException
	 */
	public List<Cafe> getCafesPorProv(int id) throws AccesoDatosException;

	
	/**
	 * Transacci�n para transferir ventas de caf�
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
	 * M�todo que busca un cafe por nombre y devuelve sus datos
	 *
	 * @param nombre
	 */
	public Cafe buscar(String nombre) throws AccesoDatosException;

	/**
	 * M�todo para insertar una fila
	 * 
	 * @param cafe
	 */
	public void insertar(Cafe cafe) throws AccesoDatosException;

	/**
	 * M�todo para borrar una fila dado un nombre de caf�
	 * 
	 * @param nombre
	 * @return
	 */
	public void borrar(String nombre) throws AccesoDatosException;

	/**
	 * M�todo que actualiza los precios por un porcentaje dado
	 * 
	 * @param percentage
	 * @throws AccesoDatosException
	 */
	public void modificarPrecio(float percentage) throws AccesoDatosException;

	/**
	 * M�todo que inserta una nueva fila usando ResultSet
	 * 
	 * @param cafe
	 */
	public void insertarFila(Cafe cafe) throws AccesoDatosException;

	/**
	 * M�todo que actualiza las ventas y el total de cada cafe con una
	 * transacci�n
	 * 
	 * @param ventas
	 * @throws AccesoDatosException
	 */
	public void actualizarVentas(HashMap<String, Integer> ventas)
			throws AccesoDatosException;

	/**
	 * M�todo para cerrar el DAO
	 * 
	 * @throws AccesoDatosException
	 */

	public void cerrar();

}
