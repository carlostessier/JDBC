package ejercicio3.ejercicios4.dao;

import java.util.List;

import ejercicio3.ejercicios4.modelo.Asignatura;


/**
 * @descrition Interfaz para DAOs de Asignaturas
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public interface AsignaturaDAO {

	/**
	 * Método que busca las asignaturas en las que está matriculado un alumno
	 *
	 * @param nombre
	 */
	public List<Asignatura> getAsignaturasPorAlumno(int identificador) throws AccesoDatosException;

	/**
	 * Método para cerrar el DAO
	 * 
	 * @throws AccesoDatosException
	 */

	public void cerrar();

}
