package com.curso.springboot.reactor.app.models.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.curso.springboot.reactor.app.models.documents.Producto;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String>{

}
