package com.curso.springboot.reactor.app.programacionReactivaBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.curso.springboot.reactor.app.programacionReactivaBase.models.Comentarios;
import com.curso.springboot.reactor.app.programacionReactivaBase.models.Usuario;
import com.curso.springboot.reactor.app.programacionReactivaBase.models.UsuarioComentarios;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Ejemplo2FlatMapYZipWith {

	private static final Logger log = LoggerFactory.getLogger(Ejemplo2FlatMapYZipWith.class);
	
	/*
	 * Vamos a tener dos flujos, por eso el flatmap
	 */
	public static void main(String[] args) {
		zipWithRangos();
	}

	public static void flatMap() {
		Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Ivan", "Perez"));
		Mono<Comentarios> comentariosMono = Mono.fromCallable(() -> {
			Comentarios comments = new Comentarios();
			comments.addComentario("Hola");
			comments.addComentario("Que tal");
			return comments;
		});

		// A partir de usuario, aplanar para que tenga usuarioComentario
		// Combinar ambos monos para sacar usuario con comentarios
		usuarioMono.flatMap(user -> comentariosMono.map(comment -> new UsuarioComentarios(user, comment)))
				.subscribe(userComment -> System.out.println(userComment.toString()));
	}

	public static void zipWith() {
		Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Ivan", "Perez"));
		Mono<Comentarios> comentariosMono = Mono.fromCallable(() -> {
			Comentarios comments = new Comentarios();
			comments.addComentario("Hola");
			comments.addComentario("Que tal");
			return comments;
		});

		// Mezclo 1, con 2 y ultimo parametro es la combinacion
		// La combinacion como interfaz funcional
		Mono<UsuarioComentarios> pruebaUsuarioComentarios = usuarioMono.zipWith(comentariosMono,
				(user, comment) -> new UsuarioComentarios(user, comment));

		System.out.println(pruebaUsuarioComentarios.block().toString()); // Esto se puede hacer con subscribe como antes
	}

	public static void zipWith2() {
		Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Ivan", "Perez"));
		Mono<Comentarios> comentariosMono = Mono.fromCallable(() -> {
			Comentarios comments = new Comentarios();
			comments.addComentario("Hola");
			comments.addComentario("Que tal");
			return comments;
		});

		// Mezcla solo con 1 , y deja una tupla
		// Lo deja en una tupla, luego nos podemos subscribir o hacer block
		Mono<UsuarioComentarios> pruebaUsuarioComentarios = usuarioMono.zipWith(comentariosMono).map(tupla -> {
			Usuario u = tupla.getT1();
			Comentarios c = tupla.getT2();
			return new UsuarioComentarios(u, c);
		});

		System.out.println(pruebaUsuarioComentarios.block().toString());
	}

	public static void zipWithRangos() {

		Flux.just(1, 2, 3, 4).zipWith(Flux.range(0, 4),
				(numeros, rangos) -> String.format("Primer Flux: %d, Segundo flux: %d", numeros , rangos))
				.subscribe(p -> log.info(p), e -> log.error(e.getMessage()));

		// Subscribe, primero si va bien y luego log de la excepcion
	}

}
