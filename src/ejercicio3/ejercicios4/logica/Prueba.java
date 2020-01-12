package ejercicio3.ejercicios4.logica;

import java.util.List;

import ejercicio3.ejercicios4.dao.FactoriaDAO;
import ejercicio3.ejercicios4.dao.AlumnoDAO;
import ejercicio3.ejercicios4.dao.AsignaturaDAO;
import ejercicio3.ejercicios4.modelo.Alumno;
import ejercicio3.ejercicios4.modelo.Asignatura;
import ejercicio3.ejercicios4.modelo.MatriculaException;

/**
 * @description Clase para probar patr�n DAO de Asignaturas
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class Prueba {

	/*
	 * Nos centramos en el acceso a datos y por eso definimos estos valores como
	 * constantes En un caso real estos valores se obtendr�n de la interacci�n
	 * del usuario con la GUI de la aplicaci�n
	 */
	private static final int id_nuevo = 1234567;
	private static final int id_borrar = 9119705;
	private static final int[] asignaturas = { 20598, 32330, 78200 };

	/**
	 * M�todo auxiliar para recorrer una lista de asigs y mostrarla por pantalla
	 * 
	 * @param libros
	 */
	public static void imprimeLista(List<Asignatura> asigs) {
		System.out.println("\nAsignaturas:");
		for (int i = 0; i < asigs.size(); i++) {
			System.out.println(asigs.get(i).toString());
		}
	}

	public static void main(String[] args) {
		try {

			
			
			// Creaci�n de un alumno nuevo
			AlumnoDAO miAlum = FactoriaDAO.getInstance().getAlumnoDAO();
			Alumno alum = new Alumno();
			alum.setId(id_nuevo);
			alum.setApellidos("Garcia Lopez");
			alum.setNombre("Ram�n");
			alum.setCurso(3);
			alum.setTitulacion(2);
			miAlum.insertar(alum);
			alum = null;
			alum = miAlum.buscar(id_nuevo);
			System.out.println("Se cre� el alumno:" + alum.toString());

			// Borrado de un alumno existente
			miAlum.borrar(id_borrar);

			// Matricular a un alumno en asignaturas
			for (int i = 0; i < asignaturas.length; i++) {
				miAlum.matricular(id_nuevo, asignaturas[i]);
			}

			AsignaturaDAO miAsignatura = FactoriaDAO.getInstance()
					.getAsignaturaDAO();
			List<Asignatura> asigs = miAsignatura
					.getAsignaturasPorAlumno(id_nuevo);

			System.out.println("El alumno:" + alum.toString()
					+ " est� matriculado en:\n");
			imprimeLista(asigs);
			
			//Si se va a ejecutar varias veces este c�digo descomentar esta l�nea para evitar error de clave primaria duplicada
			//miAlum.borrar(id_nuevo);

			// Liberamos recursos
			miAlum.cerrar();
			miAsignatura.cerrar();

		
		} catch (MatriculaException e) {
			/*
			 * En la GUI o en la clase con la que interacciona el usuario
			 * capturamos las excepciones de alto nivel de nuestra apliaci�n e
			 * informamos correctamente al usuario
			 */
			System.out.println("Lo sentimos ocurri� un error en la apliaci�n"
					+ e.getMessage());

		}

	}
}
