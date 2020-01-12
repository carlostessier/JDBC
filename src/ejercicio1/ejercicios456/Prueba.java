package ejercicio1.ejercicios456;

import java.util.Arrays;

/**
 * @descrition Clase para probar la clase cafes
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class Prueba {

	public static void main(String[] args) {
		try {

			Libros miLibro = new Libros();
			System.out.println("\nCatálogo:");
			miLibro.verCatalogo();
			
			//Observa como al llamar por segunda vez a verTabla los precios en BD se han modificado
			//Esta actualización se hace utilizando un ResultSet "CONCUR_UPDATABLE" en modificarPrecio
			miLibro.actualizarCopias(1325, 15);
			miLibro.anadirLibro(15687, "Prueba", "yo","ies ciudad escolar", 178, 9);
			System.out.println("\nCatálogo:");
			miLibro.verCatalogo();
			miLibro.borrar(15687);
			System.out.println("\nCatálogo:");
			miLibro.verCatalogo();
			System.out.println(Arrays.toString(miLibro.getCamposLibro()));
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
