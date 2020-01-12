package actividad04;

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
	private static final String SELECT_CAFES_QUERY = "select CAF_NOMBRE, PROV_ID, PRECIO, VENTAS, TOTAL from DUMMY";
	private static final String SEARCH_CAFE_QUERY = "select * from CAFES WHERE CAF_NOMBRE=";
	private static final String INSERT_CAFE_QUERY = "insert into CAFES values( ?, ?, ?, ? , ?)";
	private static final String DELETE_CAFE_QUERY = "delete from CAFES WHERE CAF_NOMBRE=";

	/**
	 * Metodo que muestra por pantalla los datos de la tabla cafes
	 * 
	 * @param con
	 * @throws SQLException
	 */
	public void verTabla() {
		/* Conexión a la Base de Datos */
		Connection con = null;
		/* Sentencia sql */
		// Statement stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		// ResultSet rs = null;
		try {
			con = new Utilidades().getConnection();
			// Creación de la sentencia

			try (Statement stmt = con.createStatement()) {
				// Ejecución de la consulta y obtención de resultados en un ResultSet
				try (ResultSet rs = stmt.executeQuery(SELECT_CAFES_QUERY)) {

					// Recuperación de los datos del ResultSet
					while (rs.next()) {
						String coffeeName = rs.getString("CAF_NOMBRE");
						int supplierID = rs.getInt("PROV_ID");
						float PRECIO = rs.getFloat("PRECIO");
						int VENTAS = rs.getInt("VENTAS");
						int total = rs.getInt("TOTAL");
						System.out.println(coffeeName + ", " + supplierID + ", " + PRECIO + ", " + VENTAS + ", " + total);
					}
				} catch (SQLException sqle) {
					// En una aplicación real, escribo en el log y delego
					//System.err.println(sqle.getMessage());
					Utilidades.printSQLException(sqle);
				}
			}

		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			System.err.println(sqle.getMessage());

		} catch (IOException e) {
			// Error al leer propiedades
			// En una aplicación real, escribo en el log y delego
			System.err.println(e.getMessage());

		}

		finally {

			// Liberamos todos los recursos pase lo que pase
			/*
			 * if (rs != null) { rs.close(); } if (stmt != null) { stmt.close(); }
			 */
			if (con != null) {
				Utilidades.closeConnection(con);
			}

		}

	}

	/**
	 * Método que busca un cafe por nombre y muestra sus datos
	 *
	 * @param nombre
	 */
	public void buscar(String nombre) {
		/* Conexión a la Base de Datos */
		Connection con = null;
		/* Sentencia sql */
		Statement stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		ResultSet rs = null;
		try {
			con = new Utilidades().getConnection();
			// Creación de la sentencia
			stmt = con.createStatement();
			// Ejecución de la consulta y obtención de resultados en un
			// ResultSet
			rs = stmt.executeQuery(SEARCH_CAFE_QUERY + "\"" + nombre + "\"");

			// Recuperación de los datos del ResultSet
			if (rs.next()) {
				String coffeeName = rs.getString("CAF_NOMBRE");
				int supplierID = rs.getInt("PROV_ID");
				float PRECIO = rs.getFloat("PRECIO");
				int VENTAS = rs.getInt("VENTAS");
				int total = rs.getInt("TOTAL");
				System.out.println(coffeeName + ", " + supplierID + ", " + PRECIO + ", " + VENTAS + ", " + total);
			}

		} catch (IOException e) {
			// Error al leer propiedades
			// En una aplicación real, escribo en el log y delego
			System.err.println(e.getMessage());
		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			System.err.println(sqle.getMessage());
		} finally {
			try {
				// Liberamos todos los recursos pase lo que pase
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					Utilidades.closeConnection(con);
				}
			} catch (SQLException sqle) {
				// En una aplicación real, escribo en el log, no delego porque es error al
				// liberar recursos
			}
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
	public void insertar(String nombre, int provid, float precio, int ventas, int total) {
		/* Conexión a la Base de Datos */
		Connection con = null;
		/* Sentencia sql */
		//PreparedStatement pstmt = null;

		try {
			con = new Utilidades().getConnection();
			// Creación de la sentencia
			try( PreparedStatement pstmt = con.prepareStatement(INSERT_CAFE_QUERY)){
			pstmt.setString(1, nombre);
			pstmt.setInt(2, provid);
			pstmt.setFloat(3, precio);
			pstmt.setInt(4, ventas);
			pstmt.setInt(5, total);

			pstmt.executeUpdate();
			}

		} catch (IOException e) {
			// Error al leer propiedades
			// En una aplicación real, escribo en el log y delego
			System.err.println(e.getMessage());

		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			System.err.println(sqle.getMessage());

		} finally {
			
				if (con != null) {
					Utilidades.closeConnection(con);
				}
			
		}

	}

	/**
	 * Método para borrar una fila dado un nombre de café
	 * 
	 * @param nombre
	 * @return
	 */
	public void borrar(String nombre) {
		/* Conexión a la Base de Datos */
		Connection con = null;
		/* Sentencia sql */
		Statement stmt = null;

		try {
			con = new Utilidades().getConnection();
			// Creación de la sentencia
			stmt = con.createStatement();
			// Ejecución de la inserción
			stmt.executeUpdate(DELETE_CAFE_QUERY + "\"" + nombre + "\"");

		} catch (IOException e) {
			// Error al leer propiedades
			// En una aplicación real, escribo en el log y delego
			System.err.println(e.getMessage());

		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			System.err.println(sqle.getMessage());

		} finally {
			try {
				// Liberamos todos los recursos pase lo que pase
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					Utilidades.closeConnection(con);
				}
			} catch (SQLException sqle) {
				// En una aplicación real, escribo en el log, no delego porque es error al
				// liberar recursos
			}
		}

	}

}
