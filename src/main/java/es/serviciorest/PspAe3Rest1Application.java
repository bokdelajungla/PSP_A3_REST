package es.serviciorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PspAe3Rest1Application {
	
	public static void main(String[] args) {
		System.out.println("Servicio rest -> Cargando el contexto de Spring...");
		SpringApplication.run(PspAe3Rest1Application.class, args);
		System.out.println("Servicio rest -> Contexto de Spring cargado");
	}

}
