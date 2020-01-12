package ejercicio3.ejercicios123.dao;

/**
 * @descrition
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

 public class FactoriaDAO {
	private static FactoriaDAO instance;
	private static final String cafeDAO = "JDBCCafeDAO";
	private static final String proveedorDAO = "JDBCProveedorDAO";

	// Patr�n Singleton
	// Garantiza que una clase s�lo tenga una instancia y proporciona un punto
	// de acceso global a ella.
	// Si un objeto no tiene estado (atributos), no necesitamos m�ltiples
	// instancias
	// del mismo cargadas en memoria
	// No utilizamos m�todos est�ticos, pues perderiamos todas las ventajas de
	// la OO
	// como la herencia
	// Si tuvi�semos atributos de objetos-->!!No usar singleton
	public static FactoriaDAO getInstance() {
		if (instance == null) {
			instance = new FactoriaDAO();
		}
		return instance;
	}

	private FactoriaDAO() {

	}

	/**
	 * Devuelve un objeto DAO adecuado dependiendo de como est� implementada la
	 * persistencia a datos
	 * 
	 * @return
	 * @throws AccesoDatosException
	 */
	public CafeDAO getCafeDAO() throws AccesoDatosException {
		/*
		 * Como esta clase es est�tica no podemos hacerlo usando Class.forName, pues no se puede determinar el tipo en t.compilaci�n
		 * try { return (LibroDAO) Class.forName(libroDAO).newInstance(); }
		 * catch (Exception e) {
		 * System.err.println(e.getMessage()+"/n"+e.toString()); throw new
		 * AccesoDatosException( "Ocurri� un error al acceder a los datos"); }
		 */
		
		/*TODO: en un caso real el tipo del DAO se lee de un fichero de propiedades y/� se utiliza Class.forName().newInstance() si la
		  clase no es est�tica. Al leerlo de un fichero de propiedades conseguimos que si el tipo del DAO cambia, no hay que tocar el c�digo,y 
		  por tanto no hay que regenerar el bytecode*/
		
		//Podemos inicializar el DAO a null o a uno por defecto
		CafeDAO cdao = null;
		
		//Un if por cada tipo de DAO posible a instanciar
		if (cafeDAO.equals("JDBCCafeDAO")) {
			cdao = new JDBCCafeDAO();
		}
		return cdao;

	}
	
	/**
	 * Devuelve un objeto DAO adecuado dependiendo de como est� implementada la
	 * persistencia a datos
	 * 
	 * @return
	 * @throws AccesoDatosException
	 */
	public ProveedorDAO getProveedorDAO() throws AccesoDatosException {
		
		//Podemos inicializar el DAO a null o a uno por defecto
		ProveedorDAO dao = null;
		
		//Un if por cada tipo de DAO posible a instanciar
		if (proveedorDAO.equals("JDBCProveedorDAO")) {
			dao = new JDBCProveedorDAO();
		}
		return dao;

	}


}
