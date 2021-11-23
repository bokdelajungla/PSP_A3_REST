package serviciosrest.modelo.persistencia;

import org.springframework.stereotype.Component;

import serviciosrest.modelo.entidad.Videojuego;

import java.util.HashSet;

/**
 * <p>Patron DAO (Data Access Object), objeto que se encarga de hacer las consultas
 * a algun motor de persistencia (BBDD, Ficheros, etc).<br>
 * En este caso, {@link listaVideojuegos} contiene los objetos {@link serviciosrest.modelo.entidad#Videojuego}
 * y simula los datos guardados en una BBDD.<br> 
 * Mediante la anotacion @Component, damos de alta un unico objeto de esta clase
 * dentro del contexto de Spring, su ID sera el nombre de la case en notacion
 * lowerCamelCase (en nuestro caso {@code daoVideojuego}).
 * 
 */

@Component
public class DaoVideojuego {
	//Atributos
	private HashSet<Videojuego> listaVideojuegos;
	private int contador;
	
	/**
	 * Cuando se cree el objeto dentro del contexto de Spring, se ejecutara
	 * su constructor, que creara la lista de videojuegos y la poblara de objetos 
	 * {@link serviciosrest.modelo.entidad#Videojuego} para que puedan ser 
	 * consumidos por nuestros clientes
	 */
	
	public DaoVideojuego () {
		System.out.println("DaoVideojuego -> Creando la lista de videojuegos!");
		listaVideojuegos = new HashSet<Videojuego>();
		Videojuego v1 = new Videojuego(contador++,"The Legend of Zelda: Twilight Princess", "Nintendo", 2006, 95); //ID 0
		Videojuego v2 = new Videojuego(contador++,"Hearthstone: Heroes of Warcraft", "Blizzard Entertainment", 2014, 88);//ID: 1
		Videojuego v3 = new Videojuego(contador++,"Final Fantasy VIII", "SquareSoft", 2009, 90);//ID: 2
		Videojuego v4 = new Videojuego(contador++,"World of Warcraft", "Blizzard Entertainment", 2004, 93);//ID:3
		Videojuego v5 = new Videojuego(contador++,"Command & Conquer", "Westwood Studios", 1995, 94);//ID:4
		Videojuego v6 = new Videojuego(contador++,"Pokemon FireRed Version", "Game Freak", 2004, 81);//ID:5
		
		
		listaVideojuegos.add(v1);
		listaVideojuegos.add(v2);
		listaVideojuegos.add(v3);
		listaVideojuegos.add(v4);
		listaVideojuegos.add(v5);
		listaVideojuegos.add(v6);
	}
	
	/***********
	 * METODOS *
	 ***********/
	
	//DAR DE ALTA UN VIDEOJUEGO
	/**
	 * Metodo añade un videojuego a la lista
	 * @param v El videojuego que quermos introducir (sin el ID)
	 * @return v El videojuego que se ha añadido a la lista (con el ID)
	 * o null en caso de que no se haya podido añadir.
	 */
	public Videojuego add(Videojuego v) {
		v.setId(contador++); //Le asignamos el ID
		if(listaVideojuegos.add(v)) {
			return v;
		}
		else return null;
	}
	
	//DAR DE BAJA UN VIDEOJUEGO POR ID
	/**
	 * Borramos el videojuego con el ID solicitado
	 * @param id el identificador del videojuego a borrar
	 * @return devolvemos true si se ha podiod eliminar del set, 
	 * o false en caso contrario.
	 */
	public boolean delete(int id) {
		for(Videojuego v: listaVideojuegos) {
			if(v.getId() == id) {
			return listaVideojuegos.remove(v);
			}
		}
		return false;
	}
	
	//MODIFICAR UN VIDEOJUEGO POR ID
	/**
	 * Metodo que modifica un videojuego con el id pasado
	 * @param v contiene todos los datos que queremos modificar incluido el id
	 * @return 0 en caso de que la modificación se haya podido realizar, 1 en caso
	 * de que el elemento no exista, 2 en caso de que el nombre ya exista en el set
	 */
	public int update(Videojuego vModificado) {
	
		int id = vModificado.getId();
		for(Videojuego v: listaVideojuegos) {
			if(v.getId() == id) {
				//Eliminamos el elemento a modificar
				listaVideojuegos.remove(v);
				//Añadimos a la lista la modificación
				if(listaVideojuegos.add(vModificado)) {
					return 0;//En caso de que se añada a la coleccion
				}
				else {//Si el objeto tiene nombre repetido
					listaVideojuegos.add(v);//Restauramos el videojuego original al set
					return 2;//El elemento ya existía, es decir ya hay un videojuego con el mismo nombre
				}
			}
		}
		//No se ha encontrado el id
		return 1;
	}
	
	//LISTAR TODOS LOS VIDEOJUEGOS
	/**
	 * Metodo que devuelve todos los videojuegos del set
	 * @return una lista con todos las videojuegos del set
	 */
	public HashSet<Videojuego> list() {
		return listaVideojuegos;
	}
	
	//OBTENER VIDEOJUEGO POR ID
	/**
	 * Devuelve un videjuego a partir de su id
	 * @param id el id del videojuego buscado
	 * @return el videojuego con el id solicitado, null en caso de
	 * que no exista.
	 */
	public Videojuego getById(int id) {
		
		for(Videojuego v: listaVideojuegos) {
			if(v.getId() == id) {
				return v;
			}
		}
		return null;
	}
	
	//METODO ADICIONAL -- LISTAR POR NOMBRE
	/**
	 * Metodo que devuelve todos los videojuegos que tengan un nombre concreto.
	 * Como puede haber varios videojuegos con el mismo nombre hay que
	 * devolver una lista con todas los encontrados
	 * @param nombre representa el nombre por el que vamos a hacer la
	 * busqueda
	 * @return una lista con los videojuegos que coincidan en el nombre.
	 * La lista estará vacia en caso de que no hay coincidencias
	 */
	public HashSet<Videojuego> listByNombre(String nombre){
		HashSet<Videojuego> listaAux = new HashSet<Videojuego>();
		for(Videojuego v : listaVideojuegos) {
			if(v.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
				listaAux.add(v);
			}
		}
		return listaAux;
	}
	
}
