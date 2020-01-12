package actividad03;


/**
 * @descrition Clase para probar la clase cafes
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class PruebaCafes {

	public static void main(String[] args) {

		System.out.println("actividad 4\n");
		try {
			Cafes miCafe = new Cafes();
			System.out.println("\nContents of CAFES table:");
			miCafe.verTabla();
			// miCafe.buscar("Colombian");
			// El id de proveedor tiene que existir en la tabla proveedores
			// miCafe.insertar("Nescafe", 49, new Float(4.99), 89, 94);
			// Comprobamos que se insertó
			// miCafe.buscar("Nescafe");
			// miCafe.borrar("Nescafe");

			// Comprobamos que se borró
			// miCafe.verTabla();
		} catch (Exception e) {
			/*
			 * En la GUI o en la clase con la que interacciona el usuario capturamos las
			 * excepciones de alto nivel de nuestra apliaciï¿½n e informamos correctamente
			 * al usuario
			 */
			System.out.println("Lo sentimos ocurriï¿½n un error en la apliaciï¿½n" + e.getMessage());

		}

	}
}
