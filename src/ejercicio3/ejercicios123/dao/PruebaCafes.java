package ejercicio3.ejercicios123.dao;


/**
 * @description Clase para probar la clase cafes
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class PruebaCafes {

	public static void main(String[] args) {
		try {

			Cafes miCafe = new Cafes();
			System.out.println("\nContenido de la tabla CAFE:");
			miCafe.verTabla();			
			miCafe.transferencia("Colombian", "Espresso");
			System.out.println("\nContenido de la tabla CAFE:");
			miCafe.verTabla();
			miCafe.cerrar();
		} catch (MercadoException e) {
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
