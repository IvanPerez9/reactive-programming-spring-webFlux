package com.curso.springboot.reactor.app.programacionReactivaBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.curso.springboot.reactor.app.programacionReactivaBase.models.Usuario;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Operadores {

	private static final Logger log = LoggerFactory.getLogger(SubscribeAndErrors.class);
	
	public static void main(String[] args) {
		ejemploFlatMap();
	}

	public static void ejemploMap() {
		// De String a Usuario
		Flux<Usuario> nombres = Flux
				.just("Ivan Perez", "Pepe Perez", "Daniel Pa", "Daniel Peña", "Daniel elTravieso", "Dan Pa")
				.doOnNext(e -> {
					if (e.isEmpty()) {
						throw new RuntimeException("Nombre en vacio");
					}
				}).map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1]))
				.filter(usuario -> usuario.getNombre().toLowerCase().equals("daniel"));

		nombres.subscribe((Usuario n) -> System.out.println(n.getNombre().concat(" ").concat(n.getApellido())));
	}

	public static void ejemploFlatMap() {
		// De String a Usuario
		Flux.just("Ivan Perez", "Pepe Perez", "Daniel Pa", "Daniel Peña", "Daniel elTravieso", "Dan Pa")
				.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1]))
				.flatMap(usuario -> {
					if (usuario.getNombre().equalsIgnoreCase("Daniel")) {
						return Mono.just(usuario);
					}
					return Mono.empty();
				}).subscribe(u -> log.info(u.getNombre()));

	}

}
