package ejercicio1.ejercicios456;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * @descrition
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class Libros{

	// Consultas a realizar en BD
	private static final String SELECT_LIBROS_QUERY = "select * from libros ORDER BY TITULO ASC";
	private static final String UPDATE_COPIAS_QUERY = "update LIBROS SET COPIAS=? WHERE ISBN= ?";
	private static final String INSERT_LIBRO_QUERY = "insert into LIBROS values (?,?,?,?,?,?)";
	private static final String DELETE_LIBRO_QUERY = "delete from LIBROS WHERE ISBN = ?";
	private static final String SELECT_CAMPOS_QUERY = "SELECT * FROM LIBROS LIMIT 1";


	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pstmt;

	/**
	 * Constructor: inicializa conexión
	 * 
	 * @throws AccesoDatosException
	 */
	public Libros() throws AccesoDatosException {
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
			//Al cerrar un stmt se cierran los resultset asociados. Podíamos omitir el primer if. Lo dejamos por claridad.
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
	 * Metodo que muestra por pantalla los datos de la tabla cafes
	 * 
	 * @param con
	 * @throws SQLException
	 */
	public void verCatalogo() throws AccesoDatosException {
		/* Sentencia sql */
		stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		try {
			// Creación de la sentencia
			stmt = con.createStatement();
			// Ejecución de la consulta y obtención de resultados en un
			// ResultSet
			rs = stmt.executeQuery(SELECT_LIBROS_QUERY);

			// Recuperación de los datos del ResultSet
			while (rs.next()) {
				int isbn = rs.getInt("ISBN");
				String titulo = rs.getString("TITULO");				
				String autor = rs.getString("AUTOR");
				String editorial = rs.getString("EDITORIAL");
				int paginas = rs.getInt("PAGINAS");
				int copias = rs.getInt("COPIAS");
				System.out.println(titulo + ", " + isbn + ", "
						+ autor + ", " + editorial + ", " +paginas+","+ copias);
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
     * Actualiza el numero de copias para un libro
     * @param isbn
     * @param copias
     * @throws AccesoDatosException
     */
	public void actualizarCopias(int isbn, int copias) throws AccesoDatosException {
		/* Sentencia sql */
		stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		try {
			
			pstmt = con.prepareStatement(UPDATE_COPIAS_QUERY);
			pstmt.setInt(1,copias);
			pstmt.setInt(2,isbn);			
			pstmt.executeUpdate();		

		}catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");
		} finally{
			liberar();
		}
	}

	
    /**
     * Añade un nuevo libro a la BD
     * @param isbn
     * @param titulo
     * @param autor
     * @param editorial
     * @param paginas
     * @param copias
     * @throws AccesoDatosException
     */
	public void anadirLibro(int isbn, String titulo, String autor, String editorial, int paginas, int copias) throws AccesoDatosException {
		
		/* Sentencia sql */
		pstmt = null;

		try {
			
			pstmt = con.prepareStatement(INSERT_LIBRO_QUERY);
			pstmt.setInt(1,isbn);
			pstmt.setString(2,titulo);
			pstmt.setString(3,autor);
			pstmt.setString(4,editorial);
			pstmt.setInt(5,paginas);
			pstmt.setInt(6,copias);
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
	 * Borra un libro por ISBN
	 * @param isbn
	 * @throws AccesoDatosException
	 */
	public void borrar(int isbn) throws AccesoDatosException {
		
		/* Sentencia sql */
		pstmt = null;

		try {
			
			pstmt = con.prepareStatement(DELETE_LIBRO_QUERY);
			pstmt.setInt(1, isbn);
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
	 * Devulve los nombres de los campos de BD
	 * @return
	 * @throws AccesoDatosException
	 */
	public String[] getCamposLibro() throws AccesoDatosException {
       
        /*Sentencia sql con parámetros de entrada*/
        pstmt = null;
        /*Conjunto de Resultados a obtener de la sentencia sql*/
        rs= null;
        ResultSetMetaData rsmd = null;
              
        String[] campos = null;


        try {

            //Solicitamos a la conexion un objeto stmt para nuestra consulta
            pstmt = con.prepareStatement(SELECT_CAMPOS_QUERY);

            //Le solicitamos al objeto stmt que ejecute nuestra consulta
            //y nos devuelve los resultados en un objeto ResultSet
            rs = pstmt.executeQuery();
            rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            campos = new String[columns];
            for (int i = 0; i < columns; i++) {
                //Los indices de las columnas comienzan en 1
                campos[i] = rsmd.getColumnLabel(i + 1);
            }
            return campos;


        } catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");

		} finally{
			liberar();
		}
	}
	
}