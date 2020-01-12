package ejercicio3.ejercicios4.modelo;

/**
 *  @descrition Clase para representar objetos Asignatura del modelo
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class Asignatura {
	private int identificador;
	private String nombre;
	private String tipo;	
	private float creditos;
	private boolean superada;
	public int getIdentificador() {
		return identificador;
	}
	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public float getCreditos() {
		return creditos;
	}
	public void setCreditos(float creditos) {
		this.creditos = creditos;
	}
	public boolean isSuperada() {
		return superada;
	}
	public void setSuperada(boolean superada) {
		this.superada = superada;
	}
	@Override
	public String toString() {
		String aux=(this.superada)?"cursada":"pendiente";
		return "Asignatura [identificador=" + identificador + ", nombre="
				+ nombre + ", tipo=" + tipo + ", creditos=" + creditos
				+ ", superada=" + aux + "]";
	}
	
	
	

}


