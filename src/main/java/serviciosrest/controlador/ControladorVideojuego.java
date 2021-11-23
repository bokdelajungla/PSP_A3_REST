package serviciosrest.controlador;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import serviciosrest.modelo.entidad.Videojuego;
import serviciosrest.modelo.persistencia.DaoVideojuego;

//Vamos a realizar un CRUD completo contra la entidad
//Videojuego. La bbdd esta simulado en memoria.
@RestController
public class ControladorVideojuego {
	
	//Hay que tener en cuenta que normalmente tenemos objetos que dependen
	//de otros para hacer su trabajo. En este caso, el objeto de tipo
	//ControladorVideojuego que hemos dado de alta en el contexto de Spring
	//mediante la anotacion @RestController NECESITA un objeto de tipo
	//DaoVideojuego para realizar su trabajo (y que dimos de alta previamente
	//con la anotacion @Component)
		
	//La anotacion @Autowired se usa para hacer inyecciones de dependencias
	//de objetos dados de alta dentro del contexto de Spring. 
	//Cuando se cree este objeto (ControladorVideojuego) dentro del contexto
	//de Spring, mediante esta anotacion le diremos que inyecte un objeto
	//de tipo DaoVideojuego a la referencia "daoVideojuego", por lo que el objeto
	//ControladorVideojuego quedará perfectamente formado.
	@Autowired
	private DaoVideojuego daoVideojuego;
	
	/***********
	 * METODOS *
	 ***********/
	
	//GET VIDEOJUEGO POR ID
	//En este primer ejemplo vamos a configurar endpoint(punto de acceso) para
	//devolver un videojuego por ID. Como nos marca REST, al ser una busqueda
	//por clave primaria, el ID debe ir como parte del PATH de la URL.
	//Esto lo hacemos por medio del atributo "path="videojuegos/{id}"
	//Podemos obtener el ID usando la anotacion @PathVariable("id") dentro
	//de un parametro de entrada. El "id" se corresponde con el "{id}",
	//es decir, deben de llamarse igual.
	
	//Ademas, queremos que el resultado sea JSON. Spring Boot serializara
	//automaticamente el resultado a json a traves de las librerías Jackson
	//Esto lo hacemos mediante el atributo "produces". Recordemos que todas
	//las respuestas van dentro del BODY del mensaje HTTP
	
	//Por ultimo, no nos olvidemos que tenemos que responder adecuadamente
	//con el codigo de respuesta apropieado, segun nos marca el protocolo HTTP.
	//Para ello usaremos la clase "ResponseEntity" que nos permite encapsular 
	//tanto el resultado en json como el codigo del mensaje. En este caso 
	//el codigo 200 "OK" si existe o 404 NOT FOUND si no existe
	
	//La URL para acceder a este metodo sería: 
	//"http://localhost:8080/videojuegos/ID" y el metodo a usar seria GET
	//ID sería el identificador que queremos buscar
	@GetMapping(path="videojuegos/{id}",produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<Videojuego> getVideojuego(@PathVariable("id") int id) {
		System.out.println("Buscando Videojuego con id: " + id);
		Videojuego v = daoVideojuego.getById(id);
		if(v != null) {
			return new ResponseEntity<Videojuego>(v,HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
	
	//DAR DE ALTA UN VIDEOJUEGO (POST)
	//En este caso vamos a dar de alta un videojuego, para ello usaremos
	//el metodo POST, vamos a producir tambien JSON (produces) y el 
	//formato que nos tiene que enviar el cliente tambien tiene que ser
	//JSON (consumes). El videojuego nos tiene que llegar sin ID, ya que
	//será asignado internamente.
	
	//Para obtener el videojuego que nos envie el cliente podemos usar
	//la anotacion @RequestBody en un parametro de entrada de tipo
	//Videojuego. Spring se encargará de deserializar automaticamente
	//el json.
	
	//Si el videojuego se ha podido añadir al set devolveremos el videojuego creado
	//y el codigo de respuesta 201 CREATED
	//Sin embargo, si el nombre del videojuego ya se encuentra dentro del set, 
	//no se añadirá y se devolvera null y el codigo de respuesta 409 CONFLICT
	
	//La URL para acceder a este metodo sería: 
	//"http://localhost:8080/videojuegos" y el metodo a usar seria POST
	//Pasandole el videojuego sin el ID dentro del body del HTTP request
	@PostMapping(path="videojuegos",consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> altaVideojuego(@RequestBody Videojuego v) {
		System.out.println("altaVideojuego: objeto Videojuego: " + v);
		Videojuego vRes = daoVideojuego.add(v); //El método para añadir un videojuego
		if (vRes != null) {
			return new ResponseEntity<Videojuego>(vRes,HttpStatus.CREATED);//201 CREATED
		}
		else {
			return new ResponseEntity<Videojuego>(vRes,HttpStatus.CONFLICT);//409 CONFLICT
		}
		
	}
	
	//DAR DE BAJA UN VIDEOJUEGO POR ID (DELETE)
	//Aqui vamos a borar un videojuego a traves de un ID que le pasemos en el
	//PATH.
	
	//Si todo ha ido bien devolvemos el codigo de respuesta de 200 OK y
	//Si no devolvemos 404 NOT FOUND
	
	//La URL para acceder a este metodo sería: 
	//"http://localhost:8080/videojuegos/ID" y el metodo a usar seria DELETE
	@DeleteMapping(path="videojuegos/{id}")
	public ResponseEntity<Videojuego> borrarVideojuego(@PathVariable("id") int id) {
		System.out.println("ID a borrar: " + id);
		boolean estado = daoVideojuego.delete(id);
		if(estado) {
			return new ResponseEntity<Videojuego>(HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
	
	//MODIFICAR UN VIDEOJUEGO POR ID (PUT)
	//En este caso vamos a hacer una modificación de un videojuego por ID
	//Para seguir lo que nos marca REST, el ID lo recibiremos en el PATH
	//y los datos por JSON dentro del body del mensaje HTTP.
	
	//Si todo ha ido bien devolvemos el codigo de respuesta de 200 OK,
	//si el id del videojuego no existe devolvemos 404 NOT FOUND
	//si el nombre ya existe en el set, devolvemos 409 CONFLICT
	
	//La URL para acceder a este metodo sería: 
	//"http://localhost:8080/videojuegos/ID" y el metodo a usar seria PUT
	//Pasandole el videojuego sin el ID dentro del body del HTTP request
	@PutMapping(path="videojuegos/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> modificarVideojuego(
			@PathVariable("id") int id, 
			@RequestBody Videojuego v) {
		System.out.println("ID a modificar: " + id);
		System.out.println("Datos a modificar: " + v);
		v.setId(id);
		int estado = daoVideojuego.update(v);
		if(estado==0) {
			return new ResponseEntity<Videojuego>(HttpStatus.OK);//200 OK
		}
		if(estado==1){
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
		else {
			return new ResponseEntity<Videojuego>(HttpStatus.CONFLICT);//409 CONFLICT
		}
	}
	
	//LISTAR TODOS LOS VIDEOJUEGOS
	//En este caso vamos a pedir todas las videojuegos que tenemos almacenadas
	//Tambien nos da la opcion de filtrar por nombre si nos pasa un parametro
	//que se llame "nombre". Mediante la anotacion @RequestParam que pondremos
	//en un atributo de entrada de tipo String. Con el atributo name="nombre"
	//establecemos el nombre del parametro y con el atributo required=false
	//le decimos que no es obligatorio que nos lo envie.
	//De esta manera si NO me viene el parametro "nombre" devolveremos
	//toda la lista de videojuegos, en caso de que venga, haremos el filtrado
	//por dicho nombre.
	
	//La URL para acceder a este metodo en caso de querer todas las videojuegos
	//sería: 
	//"http://localhost:8080/videojuegos" y el metodo a usar seria GET
	//Si queremos filtrar por nombre entonces deberemos usar:
	//"http://localhost:8080/videojuegos?nombre=NOMBRE_A_FILTRAR"
	@GetMapping(path="videojuegos",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HashSet<Videojuego>> listarVideojuegos(
			@RequestParam(name="nombre",required=false) String nombre) {
		HashSet<Videojuego> listaVideojuegos = null;
		//Si no llega un nombre, devolvemos toda la lista
		if(nombre == null) {
			System.out.println("Listando los Videojuegos");
			listaVideojuegos = daoVideojuego.list();			
		}else {
			System.out.println("Listando los videojuegos por nombre: " + nombre);
			listaVideojuegos = daoVideojuego.listByNombre(nombre);
		}
		System.out.println(listaVideojuegos);
		return new ResponseEntity<HashSet<Videojuego>>(listaVideojuegos,HttpStatus.OK);
	}	

}
