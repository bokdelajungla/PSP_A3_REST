package com.example.demo.controlador;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entidad.Videojuego;
import com.example.demo.persistencia.DaoVideojuego;

@RestController
public class ControladorVideojuego {
	
		@Autowired
		private DaoVideojuego daoVideojuego;

		@GetMapping(path="videojuegos/{id}",produces = MediaType.APPLICATION_JSON_VALUE)	
		public ResponseEntity<Videojuego> getVideojuego(@PathVariable("id") int id) {
			System.out.println("Buscando persona con id: " + id);
			Videojuego p = daoVideojuego.get(id);
			if(p != null) {
				return new ResponseEntity<Videojuego>(p,HttpStatus.OK);//200 OK
			}else {
				return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);//404 NOT FOUND
			}
		}
		
		@PostMapping(path="videojuegos",consumes=MediaType.APPLICATION_JSON_VALUE,
				produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Videojuego> altaVideojuego(@RequestBody Videojuego p) {
			System.out.println("altaPersona: objeto persona: " + p);
			daoVideojuego.add(p);
			return new ResponseEntity<Videojuego>(p,HttpStatus.CREATED);//201 CREATED
		}
		
		@GetMapping(path="videojuegos",produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Videojuego>> listarVideojuego() {
			List<Videojuego> listaVideojuegos = null;
			//Si no me viene el nombre, devolvemos toda la lista
				System.out.println("Listando las personas");
				listaVideojuegos = daoVideojuego.list();			
			System.out.println(listaVideojuegos);
			return new ResponseEntity<List<Videojuego>>(listaVideojuegos,HttpStatus.OK);
		}
		
		@PutMapping(path="videojuegos/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Videojuego> modificarVidejuego(
				@PathVariable("id") int id, 
				@RequestBody Videojuego v) {
			System.out.println("ID a modificar: " + id);
			System.out.println("Datos a modificar: " + v);
			v.setId(id);
			Videojuego pUpdate = daoVideojuego.update(v);
			if(pUpdate != null) {
				return new ResponseEntity<Videojuego>(HttpStatus.OK);//200 OK
			}else {
				return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);//404 NOT FOUND
			}
		}
		
		@DeleteMapping(path="videjuegos/{id}")
		public ResponseEntity<Videojuego> borrarVideojuego(@PathVariable("id") int id) {
			System.out.println("ID a borrar: " + id);
			Videojuego p = daoVideojuego.delete(id);
			if(p != null) {
				return new ResponseEntity<Videojuego>(p,HttpStatus.OK);//200 OK
			}else {
				return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);//404 NOT FOUND
			}
		}

}
