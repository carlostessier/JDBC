package actividad02;



/**
 * @descrition Clase para probar la clase cafes
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class PruebaCafes {

	public static void main(String[] args) {
	
			
			Cafes miCafe = new Cafes();
			System.out.println("\nContents of CAFES table:");
			//miCafe.verTabla();
			miCafe.buscar("Colombian");
			//El id de proveedor tiene que existir en la tabla proveedores
			miCafe.insertar("Nescafe", 49, new Float(4.99), 89, 94);
			//Comprobamos que se insertó
			miCafe.buscar("Nescafe");
			miCafe.borrar("Nescafe");
			
			//Comprobamos que se borró
			miCafe.verTabla();

	}
}
