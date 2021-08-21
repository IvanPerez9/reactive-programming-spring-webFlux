package com.curso.springboot.reactor.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.curso.springboot.reactor.app.dao.Producto;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {

	// El Dao y el tipo del ID  -> ReactiveMongoRepository
	
}
