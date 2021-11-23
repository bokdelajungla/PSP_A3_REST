package com.example.demo.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entidad.Videojuego;


@Component
public class DaoVideojuego {
	public List<Videojuego> listaVideojuegos;
	public int contador;
	
	public DaoVideojuego() {
		System.out.println("DaoPersona -> Creando la lista de videojuegos!");
		listaVideojuegos = new ArrayList<Videojuego>();
		Videojuego p1 = new Videojuego(contador++,"League of Legends", "Riot Games", "5;");
		Videojuego p2 = new Videojuego(contador++,"Hearthstone", "Blizzard", "6");//ID: 1
		Videojuego p3 = new Videojuego(contador++,"Mario Kart", "Nintendo", "8");//ID: 2
		Videojuego p4 = new Videojuego(contador++,"Ori and the blind forest", "Microsoft", "10");//ID:3
		Videojuego p5 = new Videojuego(contador++,"Leguends of Runeterra", "Riot Games", "10");//ID:4
		listaVideojuegos.add(p1);
		listaVideojuegos.add(p2);
		listaVideojuegos.add(p3);
		listaVideojuegos.add(p4);
		listaVideojuegos.add(p5);
	}
	
	/**
	 * Devuelve una persona a partir de su posicion del array
	 * @param posicion la posicion del arrya que buscamos
	 * @return la persona que ocupe en la posicion del array, null en caso de
	 * que no exista o se haya ido fuera de rango del array
	 */
	public Videojuego get(int posicion) {
		try {
			return listaVideojuegos.get(posicion);
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("Persona fuera de rango");
			return null;
		}
	}
	
	/**
	 * Metodo que devuelve toda las personas del array
	 * @return una lista con todas las personas del array
	 */
	public List<Videojuego> list() {
		return listaVideojuegos;
	}
	
	/**
	 * Metodo que introduce una persona al final de la lista
	 * @param p la persona que queremos introducir
	 */
	public void add(Videojuego p) {
		p.setId(contador++);
		listaVideojuegos.add(p);
	}
	
	/**
	 * Borramos una persona de una posicion del array
	 * @param posicion la posicion a borrar
	 * @return devolvemos la persona que hemos quitado del array, 
	 * o null en caso de que no exista.
	 */
	public Videojuego delete(int posicion) {
		try {
			return listaVideojuegos.remove(posicion);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("delete -> Persona fuera de rango");
			return null;
		}
	}
	
	/**
	 * Metodo que modifica una persona de una posicion del array
	 * @param p contiene todos los datos que queremos modificar, pero 
	 * p.getId() contiene la posicion del array que queremos eliminar
	 * @return la persona modificada en caso de que exista, null en caso
	 * contrario
	 */
	public Videojuego update(Videojuego v) {
		try {
			Videojuego pAux = listaVideojuegos.get(v.getId());
			pAux.setNombre(v.getNombre());
			pAux.setCompany(v.getCompany());
			pAux.setNota(v.getNota());

			return pAux;
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("update -> Persona fuera de rango");
			return null;
		}
	}
}

