package ejercicio3.ejercicios123.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ejercicio3.ejercicios123.modelo.Proveedor;


/**
 * 
 * @descrition Clase que implementa los servicios de CafeDAO con tecnología JDBC
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class JDBCProveedorDAO implements ProveedorDAO{

	// Consultas a realizar en BD
	
	private static final String SEARCH_PROV_QUERY = "select * from PROVEEDORES WHERE PROV_ID= ?";
	private static final String INSERT_PROV_QUERY = "insert into PROVEEDORES values (?,?,?,?,?,?)";
	

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
	public JDBCProveedorDAO() throws AccesoDatosException {
		try {
			// Obtenemos la conexión
			this.con = new Utilidades().getConnection();
			this.stmt = null;
			this.rs = null;
			this.pstmt = null;
			this.pstmt2 = null;
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
			// Al cerrar un statement se cierran los resultset asociados.
			// Podíamos omitir el primer if. Lo dejamos por claridad.
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (pstmt2 != null) {
				pstmt2.close();
			}
		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log, no delego porque
			// es error al liberar recursos
			Utilidades.printSQLException(sqle);
		}
	}

	
	
	/**
	 * Método que busca un proveedor por id y devuelve sus datos
	 *
	 * @param id
	 */
	public Proveedor buscar(int id) throws AccesoDatosException {
		/* Sentencia sql */
		pstmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		Proveedor proveedor=null;
		try {

			pstmt = con.prepareStatement(SEARCH_PROV_QUERY);
			pstmt.setInt(1, id);
			// Ejecución de la consulta y obtención de resultados en un
			// ResultSet
			rs = pstmt.executeQuery();

			// Recuperación de los datos del ResultSet
			if (rs.next()) {
				proveedor=new Proveedor();
				proveedor.setIdentificador(rs.getInt("PROV_ID"));
				proveedor.setNombre( rs.getString("PROV_NOMBRE"));
				proveedor.setCalle(rs.getString("CALLE"));
				proveedor.setCiudad(rs.getString("CIUDAD"));
				proveedor.setPais(rs.getString("PAIS"));
				proveedor.setCp(rs.getInt("CP"));
			}
			return  proveedor;

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
	 * Método para insertar una fila
	 * 
	 * @param cafe
	 */
	public void insertar(Proveedor proveedor) throws AccesoDatosException {

		/* Sentencia sql */
		pstmt = null;

		try {

			pstmt = con.prepareStatement(INSERT_PROV_QUERY);
			pstmt.setInt(1, proveedor.getIdentificador());
			pstmt.setString(2, proveedor.getNombre());
			pstmt.setString(3, proveedor.getCalle());
			pstmt.setString(4, proveedor.getCiudad());
			pstmt.setString(5, proveedor.getPais());			
			pstmt.setInt(6, proveedor.getCp());
		
			pstmt.executeUpdate();

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
