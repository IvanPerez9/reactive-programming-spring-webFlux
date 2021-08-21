package com.curso.springboot.reactor.app.programacionReactivaBase;

import java.util.ArrayList;
import java.util.List;

import com.curso.springboot.reactor.app.programacionReactivaBase.models.Usuario;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IterableList {

	public static void main(String[] args) {
		
	}
	
	public static void convertirStringAListaUsers() {
		List<String> listaString = new ArrayList<>();
		listaString.add("Ivan Perez");
		listaString.add("Daniel peña");
		listaString.add("Daniel elTravieso");
		listaString.add("Pepe manolo");
		
		Flux<String> usuario = Flux.fromIterable(listaString);
		List<Usuario> listaUsuario =  usuario.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1])).collectList().block();
		
		listaUsuario.forEach(user -> System.out.println(user.getNombre().toUpperCase()));
	}
	
	public static void convertirListaString() {
		List<Usuario> listaString = new ArrayList<>();
		listaString.add(new Usuario("Ivan", "Perez"));
		listaString.add(new Usuario("Daniel", "Perez"));
		listaString.add(new Usuario("Dan", "Perez"));
		listaString.add(new Usuario("Daniel", "Peña"));
		
		Flux.fromIterable(listaString)
			.map(user -> user.getNombre().toUpperCase())
			.flatMap( nombre -> {
				if (nombre.equalsIgnoreCase("Daniel")) {
					return Mono.just(nombre);
				}
				return Mono.empty();
			}).subscribe();
	}
	
}
