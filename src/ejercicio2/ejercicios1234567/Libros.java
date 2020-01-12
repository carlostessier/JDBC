package ejercicio2.ejercicios1234567;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @descrition
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class Libros {

	// Consultas a realizar en BD
	private static final String SELECT_LIBROS_QUERY = "select * from LIBROS ORDER BY TITULO ASC";
	private static final String UPDATE_COPIAS_QUERY = "update LIBROS SET COPIAS=? WHERE ISBN= ?";
	private static final String INSERT_LIBRO_QUERY = "insert into LIBROS values (?,?,?,?,?,?)";
	private static final String DELETE_LIBRO_QUERY = "delete from LIBROS WHERE ISBN = ?";
	private static final String SELECT_CAMPOS_QUERY = "SELECT * FROM LIBROS LIMIT 1";
	private static final String SELECT_LIBRO_QUERY = "SELECT * FROM LIBROS WHERE ISBN= ?";
	private static final String UPDATE_PRECIO_QUERY = "update LIBROS SET PRECIO=? WHERE ISBN= ?";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pstmt;

	/**
	 * Constructor: inicializa conexi�n
	 * 
	 * @throws AccesoDatosException
	 */
	public Libros() throws AccesoDatosException {
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
			// Al cerrar un stmt se cierran los resultset asociados. Pod�amos
			// omitir el primer if. Lo dejamos por claridad.
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
	 * Soluci�n Ejercicio 1:M�todo que muestra por pantalla muestre el cat�logo
	 * en orden inverso al alfab�tico
	 * 
	 * @param con
	 * @throws SQLException
	 */
	public void verCatalogoInverso() throws AccesoDatosException {
		/* Sentencia sql */
		stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		try {
			// Creaci�n de la sentencia, ResulSet scroll para recorrerlo hacia
			// atr�s, read only porque no lo vamos a actualizar
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// Ejecuci�n de la consulta y obtenci�n de resultados en un
			// ResultSet
			rs = stmt.executeQuery(SELECT_LIBROS_QUERY);

			// Posicionamos el cursor despu�s de la �ltima fila porque vamos a
			// recorrerlo en orden inverso
			rs.afterLast();
			// Recuperaci�n de los datos del ResultSet recorriendolo en orden
			// inverso con previous
			while (rs.previous()) {
				int isbn = rs.getInt("ISBN");
				String titulo = rs.getString("TITULO");
				String autor = rs.getString("AUTOR");
				String editorial = rs.getString("EDITORIAL");
				int paginas = rs.getInt("PAGINAS");
				int copias = rs.getInt("COPIAS");
				System.out.println(titulo + ", " + isbn + ", " + autor + ", "
						+ editorial + ", " + paginas + "," + copias);
			}

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		} finally {
			liberar();
		}

	}

	/**
	 * Soluci�n Ejercicio 2: recibe un Hashmap que contiene el nuevo n�mero de
	 * copias para cada isbn. Este nuevo n�mero de copias se lo sumamos al
	 * actual
	 * 
	 * @param isbn
	 * @param copias
	 * @throws AccesoDatosException
	 */
	public void actualizarCopias(HashMap<Integer, Integer> copias)
			throws AccesoDatosException {
		/* Sentencia sql */
		stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		try {
			// Creamos un RS actualizable y sensible a cambios que se hagan en
			// la BD
			pstmt = con
					.prepareStatement(SELECT_LIBROS_QUERY,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int isbn = rs.getInt("ISBN");
				int paginas = rs.getInt("PAGINAS");
				// Actualizamos la columna de p�ginas en el resultset
				rs.updateInt("PAGINAS", paginas + copias.get(isbn));
				// Actualizamos la fila en el RS. Los cambios de hacen efectivos
				// en BD
				rs.updateRow();
			}

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		} finally {
			liberar();
		}
	}

	/**
	 * Soluci�n Ejercicio 3:M�todo que muestra por pantalla filas concretas
	 * 
	 * @param con
	 * @throws SQLException
	 */
	public void verCatalogo(int[] filas) throws AccesoDatosException {
		/* Sentencia sql */
		pstmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		try {
			// Creaci�n de la sentencia
			pstmt = con
					.prepareStatement(SELECT_LIBROS_QUERY,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			// Ejecuci�n de la consulta y obtenci�n de resultados en un
			// ResultSet
			rs = pstmt.executeQuery();

			// Recuperaci�n de los datos del ResultSet
			for (int i = 0; i < filas.length; i++) {
				rs.absolute(filas[i]);
				int isbn = rs.getInt("ISBN");
				String titulo = rs.getString("TITULO");
				String autor = rs.getString("AUTOR");
				String editorial = rs.getString("EDITORIAL");
				int paginas = rs.getInt("PAGINAS");
				int copias = rs.getInt("COPIAS");
				System.out.println(titulo + ", " + isbn + ", " + autor + ", "
						+ editorial + ", " + paginas + "," + copias);
			}

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		} finally {
			liberar();
		}

	}

	/**
	 * Soluci�n Ejercicio 4: actualizamos precios de cada libro EN PHPMYADMIN
	 * EJECUTAR "ALTER TABLE LIBROS ADD COLUMN PRECIO FLOAT;"
	 * 
	 * @param precio
	 * @throws AccesoDatosException
	 */
	public void rellenaPrecio(float precio) throws AccesoDatosException {
		/* Sentencia sql */
		stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		try {
			// Creamos un RS actualizable y sensible a cambios que se hagan en
			// la BD
			pstmt = con
					.prepareStatement(SELECT_LIBROS_QUERY,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				int paginas = rs.getInt("PAGINAS");
				// Actualizamos la columna de p�ginas en el resultset
				rs.updateFloat("PRECIO", paginas * precio);
				// Actualizamos la fila en el RS. Los cambios de hacen efectivos
				// en BD
				rs.updateRow();
			}

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		} finally {
			liberar();
		}
	}

	/**
	 * Soluci�n Ejercicio 5: actualizaci�n de precio por transacci�n sin
	 * 
	 * @param isbn
	 * @param paginas
	 * @param precio
	 * @throws AccesoDatosException
	 */
	public void actualizaPrecio5(int isbn1, int isbn2, float precio)
			throws AccesoDatosException {
		/* Sentencia sql */
		stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		int pags1 = 0, pags2 = 0;
		float max;
		try {
			// Creamos un RS actualizable y sensible a cambios que se hagan en
			// la BD
			pstmt = con.prepareStatement(SELECT_LIBRO_QUERY);
			pstmt.setInt(1, isbn1);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pags1 = rs.getInt("PAGINAS");
			}
			pstmt.setInt(1, isbn2);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pags2 = rs.getInt("PAGINAS");

			}
			// Calculamos el precio m�ximo
			max = (pags1 > pags2 ? pags1 * precio : pags2 * precio);
			// Desactivamos el autocommit
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_PRECIO_QUERY);
			pstmt.setFloat(1, max);
			pstmt.setInt(2, isbn1);
			pstmt.executeUpdate();

			pstmt = con.prepareStatement(UPDATE_PRECIO_QUERY);
			pstmt.setFloat(1, max);
			pstmt.setInt(2, isbn2);
			pstmt.executeUpdate();
			// Hacemos un commit de forma explicita cuando hemos ejecutado
			// todas las operaciones de la transacci�n
			con.commit();
		} catch (SQLException sqle) {
			Utilidades.printSQLException(sqle);
			if (con != null) {
				try {
					System.err.println("Roll back de la transacci�n");
					con.rollback();
				} catch (SQLException excep) {
					Utilidades.printSQLException(excep);
				}

				throw new AccesoDatosException(
						"Ocurri� un error al acceder a los datos");

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
	 * Soluci�n Ejercicio 6: actualizaci�n de precio por transacci�n con
	 * CONCUR_UPDATABLE
	 * 
	 * @param isbn
	 * @param paginas
	 * @param precio
	 * @throws AccesoDatosException
	 */
	public void actualizaPrecio6(int isbn, int paginas, float precio)
			throws AccesoDatosException {
		/* Sentencia sql */
		stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		try {
			// Creamos un RS actualizable y sensible a cambios que se hagan en
			// la BD
			pstmt = con
					.prepareStatement(SELECT_LIBRO_QUERY,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			pstmt.setInt(1, isbn);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				int pags = rs.getInt("PAGINAS");
				// Actualizamos la columna de p�ginas en el resultset
				rs.updateInt("PAGINAS", paginas + pags);
				rs.updateFloat("PRECIO", (paginas + pags) * precio);
				// Actualizamos la fila en el RS. Los cambios de hacen efectivos
				// en BD
				// Este m�todo nos garantiza reflejar todos los cambios en BD
				// como una transacci�n
				rs.updateRow();
			}

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		} finally {
			liberar();
		}
	}

	/**
	 * Soluci�n Ejercicio 7: insertar fila a partir de otra
	 * 
	 * @param isbn1
	 * @param isbn2
	 * @throws AccesoDatosException
	 */
	public void copiaLibro(int isbn1, int isbn2) throws AccesoDatosException {
		/* Sentencia sql */
		stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		try {
			// Creamos un RS actualizable y sensible a cambios que se hagan en
			// la BD
			pstmt = con
					.prepareStatement(SELECT_LIBRO_QUERY,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			pstmt.setInt(1, isbn1);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String titulo = rs.getString("TITULO");
				String autor = rs.getString("AUTOR");
				String editorial = rs.getString("EDITORIAL");
				int paginas = rs.getInt("PAGINAS");
				int copias = rs.getInt("COPIAS");
				float precio = rs.getFloat("PRECIO");
				/*
				 * Para insertar una fila a trav�s del RS, hay que posicionar el
				 * cursor en una fila especial llamada "insert Row" en la que
				 * puedo dar valores a los campos
				 */

				rs.moveToInsertRow();
				rs.updateInt("ISBN", isbn2);
				rs.updateString("TITULO", titulo);
				rs.updateString("AUTOR", autor);
				rs.updateString("EDITORIAL", editorial);
				rs.updateInt("PAGINAS", paginas);
				rs.updateInt("COPIAS", copias);
				rs.updateFloat("PRECIO", precio);

				/*
				 * Inserto la fila, se inserta en BD sin tener que hacer otra
				 * consulta
				 */
				rs.insertRow();
			}
		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		} finally {
			liberar();
		}
	}

	/**
	 * Metodo que muestra por pantalla los datos de la tabla libros
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
			// Creaci�n de la sentencia
			stmt = con.createStatement();
			// Ejecuci�n de la consulta y obtenci�n de resultados en un
			// ResultSet
			rs = stmt.executeQuery(SELECT_LIBROS_QUERY);

			// Recuperaci�n de los datos del ResultSet
			while (rs.next()) {
				int isbn = rs.getInt("ISBN");
				String titulo = rs.getString("TITULO");
				String autor = rs.getString("AUTOR");
				String editorial = rs.getString("EDITORIAL");
				int paginas = rs.getInt("PAGINAS");
				int copias = rs.getInt("COPIAS");

				// * TODO: Descomentar Despu�s del Ejercicio 4 y comentar el
				// otro println
				float precio = rs.getFloat("PRECIO");
				System.out.println(titulo + ", " + isbn + ", " + autor + ", "
						+ editorial + ", " + paginas + "," + copias + ","
						+ precio);

				/*
				 * System.out.println(titulo + ", " + isbn + ", " + autor + ", "
				 * + editorial + ", " + paginas + "," + copias);
				 */

			}

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		} finally {
			liberar();
		}

	}

	/**
	 * Actualiza el numero de copias para un libro
	 * 
	 * @param isbn
	 * @param copias
	 * @throws AccesoDatosException
	 */
	public void actualizarCopias(int isbn, int copias)
			throws AccesoDatosException {
		/* Sentencia sql */
		stmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		try {

			pstmt = con.prepareStatement(UPDATE_COPIAS_QUERY);
			pstmt.setInt(1, copias);
			pstmt.setInt(2, isbn);
			pstmt.executeUpdate();

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");
		} finally {
			liberar();
		}
	}

	/**
	 * A�ade un nuevo libro a la BD
	 * 
	 * @param isbn
	 * @param titulo
	 * @param autor
	 * @param editorial
	 * @param paginas
	 * @param copias
	 * @throws AccesoDatosException
	 */
	public void anadirLibro(int isbn, String titulo, String autor,
			String editorial, int paginas, int copias)
			throws AccesoDatosException {

		/* Sentencia sql */
		pstmt = null;

		try {

			pstmt = con.prepareStatement(INSERT_LIBRO_QUERY);
			pstmt.setInt(1, isbn);
			pstmt.setString(2, titulo);
			pstmt.setString(3, autor);
			pstmt.setString(4, editorial);
			pstmt.setInt(5, paginas);
			pstmt.setInt(6, copias);
			pstmt.executeUpdate();

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");

		} finally {
			liberar();
		}

	}

	/**
	 * Borra un libro por ISBN
	 * 
	 * @param isbn
	 * @throws AccesoDatosException
	 */
	public void borrar(int isbn) throws AccesoDatosException {

		/* Sentencia sql */
		pstmt = null;

		try {

			pstmt = con.prepareStatement(DELETE_LIBRO_QUERY);
			pstmt.setInt(1, isbn);
			// Ejecuci�n del borrado
			pstmt.executeUpdate();

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");

		} finally {
			liberar();
		}

	}

	/**
	 * Devulve los nombres de los campos de BD
	 * 
	 * @return
	 * @throws AccesoDatosException
	 */
	public String[] getCamposLibro() throws AccesoDatosException {

		/* Sentencia sql con par�metros de entrada */
		pstmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		ResultSetMetaData rsmd = null;

		String[] campos = null;

		try {

			// Solicitamos a la conexion un objeto stmt para nuestra consulta
			pstmt = con.prepareStatement(SELECT_CAMPOS_QUERY);

			// Le solicitamos al objeto stmt que ejecute nuestra consulta
			// y nos devuelve los resultados en un objeto ResultSet
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			campos = new String[columns];
			for (int i = 0; i < columns; i++) {
				// Los indices de las columnas comienzan en 1
				campos[i] = rsmd.getColumnLabel(i + 1);
			}
			return campos;

		} catch (SQLException sqle) {
			// En una aplicaci�n real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurri� un error al acceder a los datos");

		} finally {
			liberar();
		}
	}

}
