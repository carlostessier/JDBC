package ejercicio2.ejercicios1234567;

import java.util.HashMap;


/**
 * 
 *  @descrition Clase para probar la clase libros
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class Prueba {

	public static void main(String[] args) {
		try {

			Libros miLibro = new Libros();
			//Prueba para el Ejercicio 1
			System.out.println("\nCatálogo en orden inverso:");
			miLibro.verCatalogoInverso();
			//Prueba para el Ejercicio 2
			HashMap<Integer, Integer> copias = new HashMap<Integer, Integer>();
			copias.put(1325, 20);
			copias.put(1725, 30);
			copias.put(12345, 40);
			copias.put(12453, 10);
			miLibro.actualizarCopias(copias);
			System.out.println("\nCatálogo en orden inverso:");
			miLibro.verCatalogoInverso();
			//Prueba para el Ejercicio 3
			int[] filas={4,2};
			System.out.println("\nVer libros concretos:");
			miLibro.verCatalogo(filas);
			//Prueba para el Ejercicio 4
			System.out.println("\nCatálogo:");
			miLibro.verCatalogo();
			miLibro.rellenaPrecio(new Float(2.5));
			//Prueba para el Ejercicio 5
			System.out.println("\nCatálogo Antes:");
			miLibro.verCatalogo();
			miLibro.actualizaPrecio5(1325,1725,new Float(1.5));
			System.out.println("\nCatálogo Después:");
			miLibro.verCatalogo();
			//Prueba para el Ejercicio 6
			miLibro.actualizaPrecio6(12345,10,new Float(1.5));
			System.out.println("\nCatálogo:");
			miLibro.verCatalogo();
			//Prueba para el Ejercicio 7
			//Si reejecuto el isbn 7777 no estará en copias y saltará nullPinterException: borrar esta tupla antes de reejecutar
			miLibro.copiaLibro(12345, 7777);
			miLibro.verCatalogo();
			miLibro.cerrar();
			
		} catch (LibreriaException e) {
			/*
			 * En la GUI o en la clase con la que interacciona el usuario
			 * capturamos las excepciones de alto nivel de nuestra apliación e
			 * informamos correctamente al usuario
			 */
			System.out.println("Lo sentimos ocurrión un error en la apliación"
					+ e.getMessage());

		}

	}
}
