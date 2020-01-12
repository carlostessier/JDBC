package ejercicio3.ejercicios4.modelo;

/**
 * @descrition Clase para representar objetos Alumno del modelo
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class Alumno {
	
	private String nombre;
	private int id;
	private String apellidos;	
	private int curso;
	private int titulacion;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public int getCurso() {
		return curso;
	}
	public void setCurso(int curso) {
		this.curso = curso;
	}
	public int getTitulacion() {
		return titulacion;
	}
	public void setTitulacion(int titulacion) {
		this.titulacion = titulacion;
	}
	@Override
	public String toString() {
		return "Alumno [nombre=" + nombre + ", id=" + id + ", apellidos="
				+ apellidos + ", curso=" + curso + ", titulacion=" + titulacion
				+ "]";
	}
	
	

}	
