package com.curso.springboot.reactor.app.services;

import com.curso.springboot.reactor.app.dao.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

	public Flux<Producto> findAll();
	
	public Mono<Producto> findById(String id);
	
	public Mono<Producto> save (Producto producto);
	
	public Mono<Void> delete (Producto producto);
	
}
