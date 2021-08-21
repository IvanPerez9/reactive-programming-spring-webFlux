package com.curso.springboot.reactor.app.programacionReactivaBase;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

public class Contrapresion {

	private static final Logger log = LoggerFactory.getLogger(Contrapresion.class);

	public static void main(String[] args) {
		ejemploContraPresion2();
	}

	public static void ejemploContraPresion() {
		// Forma de implementar el subcriber, por sobrecarga de metodo, con la interfaz
		// sobreescribimos lo de por defecto en el subscriptor
		Flux.range(1, 10).log().subscribe(new Subscriber<Integer>() {

			private Subscription s;

			private Integer limit = 2;
			private Integer consumidos = 0;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				// s.request(Long.MAX_VALUE); // Envia la maxima prosible
				s.request(limit);
			}

			@Override
			public void onError(Throwable t) {

			}

			@Override
			public void onComplete() {

			}

			@Override
			public void onNext(Integer t) {
				log.info(t.toString()); // Como lo que solemos poner en el subscribe()
				consumidos++;
				if (consumidos == limit) {
					consumidos = 0;
					s.request(limit);
				}
			}
		});
	}

	/*
	 * Más simple
	 */
	public static void ejemploContraPresion2() {
		// no hace falta hacer la sobrecarga
		// log es para ver lo que va haciendo
		Flux.range(1, 10).log().limitRate(2).subscribe();
	}

}
