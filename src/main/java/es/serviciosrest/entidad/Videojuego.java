package es.serviciosrest.entidad;

import org.springframework.stereotype.Component;

/**
 * Clase Videojuego:
 * Los videojuegos tendrán un ID, un nombre, una compañía y una nota.
 * 
 * @author Jorge
 *
 */

@Component
public class Videojuego {
	
	private String id;
	private String nombre;
	private String compania;
	private String nota;
	public String getId() {
		return id;
	}
	
	
	@Override
	public String toString() {
		return "Videojuego [id=" + id + ", nombre=" + nombre + ", compania=" + compania + ", nota=" + nota + "]";
	}


	//Getters & Setters
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCompania() {
		return compania;
	}
	public void setCompania(String compania) {
		this.compania = compania;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	
	

}
