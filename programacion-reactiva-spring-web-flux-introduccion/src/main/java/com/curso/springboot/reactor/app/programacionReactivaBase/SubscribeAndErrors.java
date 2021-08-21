package com.curso.springboot.reactor.app.programacionReactivaBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

public class SubscribeAndErrors {

	private static final Logger log = LoggerFactory.getLogger(SubscribeAndErrors.class);
	
	public static void main(String[] args) {
		Flux<String> nombres = Flux.just("Ivan", "", "Daniel").doOnNext(e -> {
			if (e.isEmpty()) {
				throw new RuntimeException("Nombre en vacio");
			}
			System.out.println(e);
		});

		// referencia de metodo en vez de lambda, y manejo del error anterior
		nombres.subscribe(log::info, error -> log.error(error.getMessage(), new Runnable() {

			@Override
			public void run() {
				log.info("Ha finalizado la ejecucion del flux");
			}
		}));
	}

}
