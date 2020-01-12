package actividad05;

import java.util.HashMap;

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
			//Comprobamos que se insertó
			miCafe.buscar("Nescafe");
			miCafe.borrar("Nescafe");
			System.out.println("\nContenido de la tabla CAFE:");
			miCafe.modificarPrecio(new Float(1.5));
			miCafe.insertarFila("La Estrella", 150, new Float(6.99), 56, 97);
			miCafe.mostrarBorrar(3);

			HashMap<String, Integer> ventas = new HashMap<String, Integer>();
			ventas.put("Colombian", 175);
			ventas.put("French_Roast", 150);
			ventas.put("Espresso", 60);
			ventas.put("Colombian_Decaf", 155);
			ventas.put("French_Roast_Decaf", 90);
			miCafe.actualizarVentas(ventas);
			System.out.println("\nContenido de la tabla CAFE:");
			miCafe.verTabla();
			miCafe.mostrarBorrar(3);
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
