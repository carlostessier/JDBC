package ejercicio1.ejercicios123;

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
			System.out.println("\nContents of CAFES table:");
			miCafe.verTabla();
			
			//Observa como al llamar por segunda vez a verTabla los precios en BD se han modificado
			
			miCafe.buscar("Colombian");
			//El id de proveedor tiene que existir en la tabla proveedores
			miCafe.insertar("Nescafe", 49, new Float(4.99), 89, 94);
			//Comprobamos que se insert�
			miCafe.buscar("Nescafe");
			miCafe.borrar("Nescafe");
			miCafe.verTabla();
			miCafe.cerrar();
		} catch (MercadoException e) {
			/*
			 * En la GUI o en la clase con la que interacciona el usuario
			 * capturamos las excepciones de alto nivel de nuestra apliaci�n e
			 * informamos correctamente al usuario
			 */
			System.out.println("Lo sentimos ocurri�n un error en la apliaci�n"
					+ e.getMessage());

		}

	}
}
