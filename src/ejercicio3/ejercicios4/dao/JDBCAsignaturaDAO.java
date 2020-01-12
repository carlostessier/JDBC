package ejercicio3.ejercicios4.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ejercicio3.ejercicios4.modelo.Asignatura;

/**
 * 
 * @descrition Clase que implementa los servicios de AsignaturaDAO con tecnolog�a JDBC
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class JDBCAsignaturaDAO implements AsignaturaDAO{

	// Consultas a realizar en BD
	
	private static final String SEARCH_ASIGNATURAS_QUERY = "select * from asignaturas,alumnos_asignaturas WHERE asignaturas.id_asignatura=alumnos_asignaturas.id_asignatura AND id_alumno= ?";
	
	

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;

	/**
	 * Constructor: inicializa conexi�n
	 * 
	 * @throws AccesoDatosException
	 */
	public JDBCAsignaturaDAO() throws AccesoDatosException {
		try {
			// Obtenemos la conexi�n
			this.con = new Utilidades().getConnection();
			this.stmt = null;
			this.rs = null;
			this.pstmt = null;
			this.pstmt2 = null;
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
			// Al cerrar un statement se cierran los resultset asociados.
			// Pod�amos omitir el primer if. Lo dejamos por claridad.
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
			// En una aplicaci�n real, escribo en el log, no delego porque
			// es error al liberar recursos
			Utilidades.printSQLException(sqle);
		}
	}

	
	
	/**
	 * M�todo que busca las asignaturas en las que est� matriculado un alumno
	 *
	 * @param nombre
	 */
	public List<Asignatura> getAsignaturasPorAlumno(int identificador) throws AccesoDatosException{
		/* Sentencia sql */
		pstmt = null;
		/* Conjunto de Resultados a obtener de la sentencia sql */
		rs = null;
		List <Asignatura> asigs=new ArrayList<Asignatura>();
		Asignatura asig=null;
		try {
			// Creaci�n de la sentencia
			pstmt = con.prepareStatement(SEARCH_ASIGNATURAS_QUERY);
			pstmt.setInt(1,identificador);
			// Ejecuci�n de la consulta y obtenci�n de resultados en un
			// ResultSet
			rs = pstmt.executeQuery();

			// Recuperaci�n de los datos del ResultSet
			while (rs.next()) {
				asig=new Asignatura();
				asig.setIdentificador(rs.getInt("id_asignatura"));
				asig.setNombre(rs.getString("nombre"));
				asig.setTipo(rs.getString("tipo"));
				asig.setCreditos(rs.getFloat("creditos"));;
				asig.setSuperada(rs.getBoolean("cursada"));;
				asigs.add(asig);
			}
			return asigs;
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


}
