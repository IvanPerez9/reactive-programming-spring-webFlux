package com.curso.springboot.reactor.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.springboot.reactor.app.dao.Producto;
import com.curso.springboot.reactor.app.repository.ProductoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private ProductoRepository repo;

	@Override
	public Flux<Producto> findAll() {
		return repo.findAll();
	}

	@Override
	public Mono<Producto> findById(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<Producto> save(Producto producto) {
		return repo.save(producto);
	}

	@Override
	public Mono<Void> delete(Producto producto) {
		return repo.delete(producto);
	}

}
