package ejercicio3.ejercicios4.dao;

import java.sql.SQLException;

import ejercicio3.ejercicios4.modelo.Alumno;


/**
 * @descrition Interfaz para DAO de alumnos
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public interface AlumnoDAO {

	/**
	 * 
	 * Metodo que busca un alumno por id
	 * 
	 * @param con
	 * @throws SQLException
	 */

	public Alumno buscar(int id) throws AccesoDatosException;

	/**
	 * Método para insertar una fila
	 * 
	 * @param cafe
	 */
	public void insertar(Alumno alumno) throws AccesoDatosException;

	/**
	 * Método para borrar una fila dado un alumno
	 * 
	 * @param nombre
	 * @return
	 */
	public void borrar(int id) throws AccesoDatosException;

	/**
	 * Método para matricular a un alumno en asignaturas
	 * 
	 * @throws AccesoDatosException
	 */

	public void matricular(int id_alumno, int id_asignatura) throws AccesoDatosException;

	/**
	 * Método para cerrar el DAO
	 * 
	 * @throws AccesoDatosException
	 */
	public void cerrar();

}
