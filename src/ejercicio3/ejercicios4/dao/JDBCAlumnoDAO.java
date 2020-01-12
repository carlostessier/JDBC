package ejercicio3.ejercicios4.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ejercicio3.ejercicios4.modelo.Alumno;


/**
 * 
 * @descrition Clase que implementa los servicios de AlumnoDAO con tecnología JDBC
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class JDBCAlumnoDAO implements AlumnoDAO{

	// Consultas a realizar en BD

	private static final String SEARCH_ALUMNO_QUERY = "select * from alumnos WHERE id_alumno= ?";
	private static final String INSERT_MATRICULA_QUERY = "insert into alumnos_asignaturas values(?,?,0)";
	private static final String INSERT_ALUMNO_QUERY = "insert into alumnos values (?,?,?,?,?)";
	private static final String DELETE_ALUMNO_QUERY = "delete from alumnos WHERE id_alumno = ?";
	
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
	public JDBCAlumnoDAO() throws AccesoDatosException {
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
	 * Método que busca un alumno por id
	 *
	 * @param id
	 */
	public Alumno buscar(int id) throws AccesoDatosException {
		/* Sentencia sql */
		pstmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		Alumno al=null;
		try {

			pstmt = con.prepareStatement(SEARCH_ALUMNO_QUERY);
			pstmt.setInt(1, id);
			// Ejecución de la consulta y obtención de resultados en un
			// ResultSet
			rs = pstmt.executeQuery();

			// Recuperación de los datos del ResultSet
			if (rs.next()) {
				al=new Alumno();
				al.setId(rs.getInt("id_alumno"));
				al.setNombre(rs.getString("nombre"));
				al.setApellidos(rs.getString("apellidos"));
				al.setCurso(rs.getInt("curso"));
				al.setTitulacion(rs.getInt("titulacion"));
			}
			return al;

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
	 * @param alumno
	 */
	public void insertar(Alumno alumno) throws AccesoDatosException {

		/* Sentencia sql */
		pstmt = null;

		try {

			pstmt = con.prepareStatement(INSERT_ALUMNO_QUERY);
			pstmt.setInt(1, alumno.getId());
			pstmt.setString(2, alumno.getApellidos());
			pstmt.setString(3, alumno.getNombre());
			pstmt.setInt(4, alumno.getCurso());
			pstmt.setInt(5, alumno.getTitulacion());
			// Ejecución de la inserción
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

	/**
	 * Método para borrar una fila dado un id de alumno
	 * 
	 * @param nombre
	 * @return
	 */
	public void borrar(int id) throws AccesoDatosException {

		/* Sentencia sql */
		pstmt = null;

		try {

			pstmt = con.prepareStatement(DELETE_ALUMNO_QUERY);
			pstmt.setInt(1, id);
			// Ejecución del borrado
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

	/**
	 * Método para matricular a un alumno en asignatura
	 * 
	 * @param percentage
	 * @throws AccesoDatosException
	 */
	public void matricular(int al, int as) throws AccesoDatosException {
		pstmt = null;
		try {
			// Asociamos un RS que permite hacer scroll, es actualizable y
			// sesnsible a los cambios que se hagan simultaneamente en BD
			pstmt = con.prepareStatement(INSERT_MATRICULA_QUERY);
			pstmt.setInt(1, al);
			pstmt.setInt(2, as);
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
