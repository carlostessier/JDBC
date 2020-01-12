package ejercicio1.ejercicios123;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pstmt;

	/**
	 * Constructor: inicializa conexi�n
	 * 
	 * @throws AccesoDatosException
	 */
	public Cafes() throws AccesoDatosException {
		try {
			// Obtenemos la conexi�n
			this.con = new Utilidades().getConnection();
			this.stmt = null;
			this.rs = null;
			this.pstmt = null;
		} catch (IOException e) {
			// Error al leer propiedades
			// En una aplicaci�n real, escribo en el log y delego
			System.err.println(e.getMessage());
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		}

	}

	
	/**
	 * M�todo para cerrar la conexi�n
	 * 
	 * @throws AccesoDatosException
	 */
	public void cerrar() {
					
			if (con != null) {
				Utilidades.closeConnection(con);
			}
		
	}

	
	/**
	 * M�todo para liberar recursos
	 * 
	 * @throws AccesoDatosException
	 */
	private void liberar() {
		try {
			// Liberamos todos los recursos pase lo que pase
			//Al cerrar un statement se cierran los resultset asociados. Pod�amos omitir el primer if. Lo dejamos por claridad.
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
			// En una aplicaci�n real, escribo en el log, no delego porque
			// es error al liberar recursos
			Utilidades.printSQLException(sqle);
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
			// Creaci�n de la sentencia
			stmt = con.createStatement();
			// Ejecuci�n de la consulta y obtenci�n de resultados en un
			// ResultSet
			rs = stmt.executeQuery(SELECT_CAFES_QUERY);

			// Recuperaci�n de los datos del ResultSet
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
			// En una aplicaci�n real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		}finally{
			liberar();
		}

	}

	
	/**
	 * M�todo que busca un cafe por nombre y muestra sus datos
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
			// Ejecuci�n de la consulta y obtenci�n de resultados en un
			// ResultSet
			rs = pstmt.executeQuery();

			// Recuperaci�n de los datos del ResultSet
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
			// En una aplicaci�n real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		} finally{
			liberar();
		}
		
	}

	/**
	 * M�todo para insertar una fila
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
			// Ejecuci�n de la inserci�n
			pstmt.executeUpdate();

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");

		} finally{
			liberar();
		}

	}

	/**
	 * M�todo para borrar una fila dado un nombre de caf�
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
			// Ejecuci�n del borrado
			pstmt.executeUpdate();

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");

		} finally{
			liberar();
		}

	}

}
