package ejercicio3.ejercicios123.logica;

import java.util.List;

import ejercicio3.ejercicios123.dao.CafeDAO;
import ejercicio3.ejercicios123.dao.FactoriaDAO;
import ejercicio3.ejercicios123.dao.MercadoException;
import ejercicio3.ejercicios123.dao.ProveedorDAO;
import ejercicio3.ejercicios123.modelo.Cafe;
import ejercicio3.ejercicios123.modelo.Proveedor;

/**
 * @description Clase para probar patrón DAO de Cafes
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class PruebaCafes {

	/**
	 * Método auxiliar para recorrer una lista de cafes y mostrarla por pantalla
	 * @param libros
	 */
	public static void imprimeLista(List<Cafe> cafes){
		System.out.println("\nContenido de la tabla CAFE:");
		for(int i=0;i<cafes.size();i++){
			System.out.println(cafes.get(i).toString());
		}
	}
	public static void main(String[] args) {
		try {

			//Ejercicio 1
			List<Cafe> cafes;
			Cafe cafe=new Cafe();
			cafe.setNombre("CafeIESCE");
			cafe.setProveedorId(101);
			cafe.setTotal(57);
			cafe.setPrecio(new Float(5.3));
			cafe.setVentas(43);
			CafeDAO miCafe = FactoriaDAO.getInstance().getCafeDAO();
			cafes=miCafe.verTabla();
			imprimeLista(cafes);
			miCafe.insertar(cafe);		
			cafes=miCafe.verTabla();
			imprimeLista(cafes);
			//Borro por si ejecuto varias veces que no me de error de clave primaria duplicada
			miCafe.borrar("CafeIESCE");
			//miCafe.cerrar(); Cerramos después del ejercicio 3
			
			//Ejercicio 2
			ProveedorDAO miProv=FactoriaDAO.getInstance().getProveedorDAO();
			Proveedor prov=new Proveedor();
			//Si se ejecuta este código varias veces cambiar identificador para que no de error de clave primaria duplicada
			prov.setIdentificador(42);
			prov.setCalle("mi calle");
			prov.setCiudad("madrid");
			prov.setCp(28050);
			prov.setPais("ES");
			prov.setNombre("Prov IES");
			miProv.insertar(prov);
			prov=null;
			prov=miProv.buscar(42);
			System.out.println(prov.toString());
			
			
			cafes=miCafe.getCafesPorProv(150);
			prov=miProv.buscar(150);
			System.out.println(prov.toString());
			imprimeLista(cafes);
			
			//Liberamos recursos
			miProv.cerrar();
			miCafe.cerrar();
			
			//Ejercicio 3
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
