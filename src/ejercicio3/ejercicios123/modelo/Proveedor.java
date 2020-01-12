package ejercicio3.ejercicios123.modelo;

/**
 *  @descrition
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class Proveedor {
	private int identificador;
	private String nombre;
	private String calle;
	private String ciudad;
	private String pais;
	private int cp;
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
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public int getCp() {
		return cp;
	}
	public void setCp(int cp) {
		this.cp = cp;
	}
	@Override
	public String toString() {
		return "Proveedor [nombre="
				+ nombre + ", calle=" + calle + ", ciudad=" + ciudad
				+ ", pais=" + pais + ", cp=" + cp + "]";
	}
	
	

}


