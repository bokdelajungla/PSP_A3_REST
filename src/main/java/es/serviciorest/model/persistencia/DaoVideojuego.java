package es.serviciorest.model.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.serviciorest.model.entity.Videojuego;

/**
 * Data Access Object de videojuego
 * 
 * @author Grupo 16
 * @version 1.0
 *
 */
@Component
public class DaoVideojuego {

	// Atributos del DAO
	public List<Videojuego> listaVideojuegos;
	public int contador;
	
	// Constructor del DAO
	public DaoVideojuego() {
		System.out.println("DaoVideojuego -> Creando lista de Videojuegos");
		
		// Inicializamos el ArrayList
		listaVideojuegos = new ArrayList<Videojuego>();
		
		// Creamos los objetos de tipo videojuego
		Videojuego v1 = new Videojuego(contador++, "Killzone", "Sony", 8);
		Videojuego v2 = new Videojuego(contador++, "Horizon", "Sony", 9);
		Videojuego v3 = new Videojuego(contador++, "Super Mario", "Nintendo", 6);
		Videojuego v4 = new Videojuego(contador++, "Metroid", "Nintendo", 7);
		Videojuego v5 = new Videojuego(contador++, "Pokemon", "Nintendo", 8);
		
		// Incluimos los videojuegos en la lista
		listaVideojuegos.add(v1);
		listaVideojuegos.add(v2);
		listaVideojuegos.add(v3);
		listaVideojuegos.add(v4);
		listaVideojuegos.add(v5);
	}
	
	/**
	 * Devuelve un videojuego a partir de su posicion en el array
	 * @param pos posicion del videojuego en el array de videojuegos
	 * @return el videojuego en la posicion especificada, null si no existe o esta fuera de rango
	 */
	public Videojuego get(int pos) {
		try {
			return listaVideojuegos.get(pos);
		} catch(IndexOutOfBoundsException iobe) {
			System.out.println("get -> Videojuego fuera de rango");
			return null;
		}
	}
	
	/**
	 * Devuelve todos los videojuegos del array
	 * @return lista con todos los videojuegos disponibles
	 */
	public List<Videojuego> list() {
		return listaVideojuegos;
	}
	
	/**
	 * Introduce un videojuego en el array
	 * @param v videojuego a introducir en el array
	 */
	public void add(Videojuego v) {
		v.setId(contador++);
		listaVideojuegos.add(v);
	}
	
	/**
	 * Borra un videojuego de una posición del array
	 * @param pos posicion del videojuego a borrar
	 * @return videojuego eliminado o null en caso de no existir
	 */
	public Videojuego delete(int pos) {
		try {
			return listaVideojuegos.remove(pos);
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("delete -> Videojuego fuera de rango");
			return null;
		}
	}
	
	/**
	 * Modifia un videojuego en una posicion determinada del array
	 * @param v contiene los datos a modificar (exceptuando el id, que no se modifica)
	 * @return videojuego modificado en caso de que exista, null en caso contrario
	 */
	public Videojuego update(Videojuego v) {
		try {
			// hacemos un uso de una objeto auxiliar a modo de referencia (puntero)
			Videojuego aux = listaVideojuegos.get(v.getId());
			// fijamos los atributos que vamos a modificar (Ojo! el id no hay que doificarlo!)
			aux.setNombre(v.getNombre());
			aux.setCompanyia(v.getCompanyia());
			aux.setNota(v.getNota());
			return aux;
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("update -> Videojuego fuera de rango");
			return null;
		}
	}
	
}

