package com.curso.springboot.reactor.app;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.curso.springboot.reactor.app.dao.Producto;
import com.curso.springboot.reactor.app.repository.ProductoRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class ProgramacionReactivaSpringWebFluxApplication implements CommandLineRunner  {

	@Autowired
	private ProductoRepository repo;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(ProgramacionReactivaSpringWebFluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// Borramos antes de la BBDD
		mongoTemplate.dropCollection("productos").subscribe();
		
		Flux.just(
				new Producto("TV", 45.00),
				new Producto("PC", 150.00),
				new Producto("Monitor", 50.00),
				new Producto("Raton", 25.00)
				// Esto con MAP seguiria siendo un Flux, con flatMap nos ahorramos
				).flatMap(producto -> {
					producto.setCreateAt(new Date());
					return repo.save(producto);
				}).subscribe();
		// por defecto la bbdd es test
	}
		
}
