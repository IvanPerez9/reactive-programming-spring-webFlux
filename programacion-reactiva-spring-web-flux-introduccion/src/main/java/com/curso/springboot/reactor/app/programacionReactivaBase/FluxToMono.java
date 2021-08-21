package com.curso.springboot.reactor.app.programacionReactivaBase;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.curso.springboot.reactor.app.programacionReactivaBase.models.Usuario;

import reactor.core.publisher.Flux;

public class FluxToMono {

	private static final Logger log = LoggerFactory.getLogger(SubscribeAndErrors.class);
	
	public static void main(String[] args) {
		List<Usuario> listaString = new ArrayList<>();
		listaString.add(new Usuario("Ivan", "Perez"));
		listaString.add(new Usuario("Daniel", "Perez"));
		listaString.add(new Usuario("Dan", "Perez"));
		listaString.add(new Usuario("Daniel", "Peña"));
		
		Flux.fromIterable(listaString).subscribe(user -> log.info(user.getNombre()));
		
		// La lista como 1 objeto Mono
		Flux.fromIterable(listaString).collectList().subscribe(user -> log.info(user.toString()));
		
		// Recorremos 1 objeto pero como una lista
		// Lo hacemos en el observable
		Flux.fromIterable(listaString).collectList().subscribe(lista -> lista.forEach(System.out::println));
	}
	
}
