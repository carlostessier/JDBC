package ejercicio2.ejercicios8;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


/**
 * @descrition
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */
public class Cafes {

	// Consultas a realizar en BD
	private static final String SELECT_CAFES_QUERY = "select CAF_NOMBRE, PROV_ID, PRECIO, VENTAS, TOTAL from CAFES";
	private static final String SEARCH_CAFE_QUERY = "select * from CAFES WHERE CAF_NOMBRE= ?";
	private static final String INSERT_CAFE_QUERY = "insert into CAFES values (?,?,?,?,?)";
	private static final String DELETE_CAFE_QUERY = "delete from CAFES WHERE CAF_NOMBRE = ?";
	private static final String UPDATE_VENTAS_CAFE = "update CAFES set VENTAS = ? where CAF_NOMBRE = ?";
	private static final String UPDATE_INC_VENTAS_CAFE = "update CAFES set VENTAS = VENTAS + ? where CAF_NOMBRE = ?";
	private static final String UPDATE_TOTAL_CAFE = "update CAFES "
			+ "set TOTAL = TOTAL + ? where CAF_NOMBRE = ?";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;


	/**
	 * Constructor: inicializa conexión
	 * 
	 * @throws AccesoDatosException
	 */
	public Cafes() throws AccesoDatosException {
		try {
			// Obtenemos la conexión
			this.con = new Utilidades().getConnection();
			this.stmt = null;
			this.rs = null;
			this.pstmt = null;
		} catch (IOException e) {
			// Error al leer propiedades
			// En una aplicación real, escribo en el log y delego
			System.err.println(e.getMessage());
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");
		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");
		}

	}

	
	/**
	 * Método para cerrar la conexión
	 * 
	 * @throws AccesoDatosException
	 */
	public void cerrar() {
					
			if (con != null) {
				Utilidades.closeConnection(con);
			}
		
	}

	
	/**
	 * Método para liberar recursos
	 * 
	 * @throws AccesoDatosException
	 */
	private void liberar() {
		try {
			// Liberamos todos los recursos pase lo que pase
			//Al cerrar un statement se cierran los resultset asociados. Podíamos omitir el primer if. Lo dejamos por claridad.
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}			
		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log, no delego porque
			// es error al liberar recursos
			Utilidades.printSQLException(sqle);
		}
	}
	
	
	/**
	 * Solución Ejercicio 8: Transacción para transferir ventas de café
	 * 
	 * @param ventas
	 * @throws AccesoDatosException
	 */
	public void transferencia(String cafe1, String cafe2)
			throws AccesoDatosException {
		pstmt = null;
		pstmt2 = null;
		rs = null;

		try {

			pstmt = con.prepareStatement(SEARCH_CAFE_QUERY);
			pstmt.setString(1, cafe1);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// Desactivamos el autocommit
				con.setAutoCommit(false);
				int ventas = rs.getInt("VENTAS");
				pstmt2 = con.prepareStatement(UPDATE_INC_VENTAS_CAFE);
				pstmt2.setInt(1, ventas);
				pstmt2.setString(2, cafe2);
				pstmt2.executeUpdate();
				pstmt = con.prepareStatement(UPDATE_VENTAS_CAFE);
				pstmt.setInt(1, 0);
				pstmt.setString(2, cafe1);
				pstmt.executeUpdate();
				con.commit();

			}

		} catch (SQLException e) {
			Utilidades.printSQLException(e);
			if (con != null) {
				try {
					System.err.println("Roll back de la transacción");
					con.rollback();
				} catch (SQLException excep) {
					Utilidades.printSQLException(excep);
				}

				throw new AccesoDatosException(
						"Ocurrió un error al acceder a los datos");

			}
		} finally {
			liberar();
			try {
				// Volvemos a habilitar el autocommit
				con.setAutoCommit(true);
			} catch (SQLException excep) {
				Utilidades.printSQLException(excep);
			}
		}
	}

	/**
	 * Metodo que muestra por pantalla los datos de la tabla cafes
	 * 
	 * @param con
	 * @throws SQLException
	 */
	public void verTabla() throws AccesoDatosException {
		/* Sentencia sql */
		stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		try {
			// Creación de la sentencia
			stmt = con.createStatement();
			// Ejecución de la consulta y obtención de resultados en un
			// ResultSet
			rs = stmt.executeQuery(SELECT_CAFES_QUERY);

			// Recuperación de los datos del ResultSet
			while (rs.next()) {
				String coffeeName = rs.getString("CAF_NOMBRE");
				int supplierID = rs.getInt("PROV_ID");
				float PRECIO = rs.getFloat("PRECIO");
				int VENTAS = rs.getInt("VENTAS");
				int total = rs.getInt("TOTAL");
				System.out.println(coffeeName + ", " + supplierID + ", "
						+ PRECIO + ", " + VENTAS + ", " + total);
			}

		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");
		}finally{
			liberar();
		}

	}

	
	/**
	 * Método que busca un cafe por nombre y muestra sus datos
	 *
	 * @param nombre
	 */
	public void buscar(String nombre) throws AccesoDatosException {
		/* Sentencia sql */
		pstmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		try {
			
			pstmt = con.prepareStatement(SEARCH_CAFE_QUERY);
			pstmt.setString(1, nombre);
			// Ejecución de la consulta y obtención de resultados en un
			// ResultSet
			rs = pstmt.executeQuery();

			// Recuperación de los datos del ResultSet
			if (rs.next()) {
				String coffeeName = rs.getString("CAF_NOMBRE");
				int supplierID = rs.getInt("PROV_ID");
				float PRECIO = rs.getFloat("PRECIO");
				int VENTAS = rs.getInt("VENTAS");
				int total = rs.getInt("TOTAL");
				System.out.println(coffeeName + ", " + supplierID + ", "
						+ PRECIO + ", " + VENTAS + ", " + total);
			}

		}  catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");
		} finally{
			liberar();
		}
		
	}

	/**
	 * Método para insertar una fila
	 * 
	 * @param nombre
	 * @param provid
	 * @param precio
	 * @param ventas
	 * @param total
	 * @return
	 */
	public void insertar(String nombre, int provid, float precio, int ventas,
			int total) throws AccesoDatosException {
		
		/* Sentencia sql */
		pstmt = null;

		try {
			
			pstmt = con.prepareStatement(INSERT_CAFE_QUERY);
			pstmt.setString(1, nombre);
			pstmt.setInt(2, provid);
			pstmt.setFloat(3, precio);
			pstmt.setInt(4, ventas);
			pstmt.setInt(5, total);
			// Ejecución de la inserción
			pstmt.executeUpdate();

		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");

		} finally{
			liberar();
		}

	}

	/**
	 * Método para borrar una fila dado un nombre de café
	 * 
	 * @param nombre
	 * @return
	 */
	public void borrar(String nombre) throws AccesoDatosException {
		
		/* Sentencia sql */
		pstmt = null;

		try {
			
			pstmt = con.prepareStatement(DELETE_CAFE_QUERY);
			pstmt.setString(1, nombre);
			// Ejecución del borrado
			pstmt.executeUpdate();

		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");

		} finally{
			liberar();
		}

	}
	
	/**
	 * Método que actualiza los precios por un porcentaje dado
	 * 
	 * @param percentage
	 * @throws AccesoDatosException
	 */
	public void modificarPrecio(float percentage) throws AccesoDatosException {
		Statement stmt = null;
		try {
			// Asociamos un RS que permite hacer scroll, es actualizable y
			// sesnsible a los cambios que se hagan simultaneamente en BD
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(SELECT_CAFES_QUERY);

			while (rs.next()) {
				float f = rs.getFloat("PRECIO");
				// Actualizamos la columna precio en el RS
				rs.updateFloat("PRECIO", f * percentage);
				// Actualizamos la fila en el RS. Los cambios de hacen efectivos
				// en BD
				rs.updateRow();
			}
			// Me coloco delante de la primera fila
			rs.beforeFirst();
			// Ahora puedo volver a recorrer el RS y mostrar los datos
			// actualizados sin necesidad de una nueva consulta
			System.out.println("\nEn modificar precio:\n");
			while (rs.next()) {
				String coffeeName = rs.getString("CAF_NOMBRE");
				int supplierID = rs.getInt("PROV_ID");
				float PRECIO = rs.getFloat("PRECIO");
				int VENTAS = rs.getInt("VENTAS");
				int total = rs.getInt("TOTAL");
				System.out.println(coffeeName + ", " + supplierID + ", "
						+ PRECIO + ", " + VENTAS + ", " + total);
			}

		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");

		} finally {
			liberar();
		}

	}
	
	/**
	 * Método que inserta una nueva fila usando ResultSet
	 * 
	 * @param coffeeName
	 * @param supplierID
	 * @param PRECIO
	 * @param VENTAS
	 * @param total
	 * @throws SQLException
	 */
	public void insertarFila(String coffeeName, int supplierID, float PRECIO,
			int VENTAS, int total) throws AccesoDatosException {
		Statement stmt = null;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(SELECT_CAFES_QUERY);

			/*
			 * Para insertar una fila a través del RS, hay que posicionar el
			 * cursor en una fila especial llamada "insert Row" en la que puedo
			 * dar valores a los campos
			 */
			rs.moveToInsertRow();

			rs.updateString("CAF_NOMBRE", coffeeName);
			rs.updateInt("PROV_ID", supplierID);
			rs.updateFloat("PRECIO", PRECIO);
			rs.updateInt("VENTAS", VENTAS);
			rs.updateInt("TOTAL", total);

			/*
			 * Inserto la fila, se inserta en BD sin tener que hacer otra
			 * consulta
			 */
			rs.insertRow();
			rs.beforeFirst();
			// Ahora puedo volver a recorrer el RS y mostrar los datos
			// actualizados sin necesidad de una nueva consulta
			System.out.println("\nEn insertar fila:\n");
			while (rs.next()) {
				String coffeeName1 = rs.getString("CAF_NOMBRE");
				int supplierID1 = rs.getInt("PROV_ID");
				float PRECIO1 = rs.getFloat("PRECIO");
				int VENTAS1 = rs.getInt("VENTAS");
				int total1 = rs.getInt("TOTAL");
				System.out.println(coffeeName1 + ", " + supplierID1 + ", "
						+ PRECIO1 + ", " + VENTAS1 + ", " + total1);
			}

		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");

		} finally {
			liberar();
		}
	}
	
	/**
	 * Método que actualiza las ventas y el total de cada cafe con una
	 * transacción
	 * 
	 * @param ventas
	 * @throws AccesoDatosException
	 */
	public void actualizarVentas(HashMap<String, Integer> ventas)
			throws AccesoDatosException {
		pstmt = null;
		pstmt2 = null;

		try {
			// Desactivamos el autocommit
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_VENTAS_CAFE);
			pstmt2 = con.prepareStatement(UPDATE_TOTAL_CAFE);

			for (Map.Entry<String, Integer> e : ventas.entrySet()) {
				pstmt.setInt(1, e.getValue().intValue());
				pstmt.setString(2, e.getKey());
				pstmt.executeUpdate();
				pstmt2.setInt(1, e.getValue().intValue());
				pstmt2.setString(2, e.getKey());
				pstmt2.executeUpdate();
				// Hacemos un commit de forma explicita cuando hemos ejecutado
				// todas las operaciones de la transacción
				con.commit();
			}
		} catch (SQLException e) {
			Utilidades.printSQLException(e);
			if (con != null) {
				try {
					System.err.println("Roll back de la transacción");
					con.rollback();
				} catch (SQLException excep) {
					Utilidades.printSQLException(excep);
				}

				throw new AccesoDatosException(
						"Ocurrió un error al acceder a los datos");

			}
		} finally {
			liberar();
			try {
				// Volvemos a habilitar el autocommit
				con.setAutoCommit(true);
			} catch (SQLException excep) {
				Utilidades.printSQLException(excep);
			}
		}
	}
	
	/**
	 * Método que se posiciona en una fila, la muestra y luego la borra
	 * 
	 * @param coffeeName
	 * @param supplierID
	 * @param PRECIO
	 * @param VENTAS
	 * @param total
	 * @throws SQLException
	 */
	public void mostrarBorrar(int pos) throws AccesoDatosException {
		Statement stmt = null;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(SELECT_CAFES_QUERY);

			/*
			 * Para insertar una fila a través del RS, hay que posicionar el
			 * cursor en una fila especial llamada "insert Row" en la que puedo
			 * dar valores a los campos
			 */
			rs.absolute(pos);
			System.out.println("\nEn mostrar y borrar:\n");

			String coffeeName1 = rs.getString("CAF_NOMBRE");
			int supplierID1 = rs.getInt("PROV_ID");
			float PRECIO1 = rs.getFloat("PRECIO");
			int VENTAS1 = rs.getInt("VENTAS");
			int total1 = rs.getInt("TOTAL");
			System.out.println(coffeeName1 + ", " + supplierID1 + ", "
					+ PRECIO1 + ", " + VENTAS1 + ", " + total1);
			rs.deleteRow();

		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");

		} finally {
			liberar();
		}
	}



}
