package es.serviciorest.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import es.serviciorest.model.*;
import es.serviciorest.model.entity.Videojuego;
import es.serviciorest.model.persistencia.DaoVideojuego;
/**
 * Controlador del CRUD
 * @author Grupo 16
 * @version 1.0
 */
@RestController
public class ControladorVideojuego {

	// Inyectamos el daoVideojuego
	@Autowired
	private DaoVideojuego daoVideojuego;
	
	// CRUD Completo -> Create, Read, Update, Delete
	
	// CREATE. Url de acceso: http://localhost:8081/videojuegos y el metodo POST
	// Pasar el videojuego sin el id dentro del body
	@PostMapping(path="videojuegos", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> altaVideojuego(@RequestBody Videojuego v) {
		System.out.println("altaVideojuego: objeto videojuego" + v);
		// incluimos el videojuego
		daoVideojuego.add(v);
		// se da de alta un videojuego: retornamos 201 - CREATED
		return new ResponseEntity<Videojuego>(v, HttpStatus.CREATED); 
	}
	
	// READ. Url de acceso: http://localhost:8081/videojuegos/id y el metodo GET
	@GetMapping(path="videojuegos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> getVideojuego(@PathVariable("id") int id) {
		System.out.println("Buscando videojuego con id: " + id);
		Videojuego v = daoVideojuego.get(id);
		// Si se encuentra el videojuego con dicho id: retornamos 200 - OK
		if(v != null)
			return new ResponseEntity<Videojuego>(v, HttpStatus.OK); 
		// Si no se encuentra el videojuego con dicho id: retornamos 404 - NOT FOUND
		else
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND); 
	}

	// READ (lista de videojuegos). Url de acceso: http://localhost:8081/videojuegos y el metodo GET)
	@GetMapping(path="videojuegos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Videojuego>> listarVideojuegos() {
		System.out.println("Devolviendo lista videojuegos");
		List<Videojuego> listaVideojuegos = daoVideojuego.list();
		// retornamos 200 - OK
		return new ResponseEntity<List<Videojuego>>(listaVideojuegos, HttpStatus.OK); 
	}
	
	// UPDATE. Url de acceso: http://localhost:8081/videojuegos/id y el metodo PUT
	// Pasar el videojuego sin el id dentro del body
	@PutMapping(path="videojuegos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> modificarVideojuego(@PathVariable("id") int id,
			@RequestBody Videojuego v) {
		System.out.println("ID a modificar: " + id);
		System.out.println("Datos a modificar: " + v);
		
		// referencia al id del videojuego que queremos modificar
		v.setId(id);
		
		// modificamos los datos de dicho videojuego
		Videojuego vUpdate = daoVideojuego.update(v);
		
		// Si se encuentra el videojuego a modificar con dicho id: retornamos 200 - OK
		if(vUpdate != null)
			return new ResponseEntity<Videojuego>(HttpStatus.OK); 
		// Si no se encuentra el videojuego a modificar con dicho id: retornamos 404 - NOT FOUND
		else
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND); 
	} 
	
	// DELETE. Url de acceso: http://localhost:8081/videojuegos/id y el metodo DELETE
	@DeleteMapping(path="videojuegos/{id}")
	public ResponseEntity<Videojuego> borrarVideojuego(@PathVariable("id") int id) {
		System.out.println("ID a borrar: " + id);
		Videojuego v = daoVideojuego.delete(id);
		// Si se encuentra el videojuego a borrar con dicho id: retornamos 200 - OK
		if(v != null)
			return new ResponseEntity<Videojuego>(v, HttpStatus.OK); 
		// Si no se encuentra el videojuego a borrar con dicho id: retornamos 404 - NOT FOUND
		else
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND); 
	}
	
	
}
