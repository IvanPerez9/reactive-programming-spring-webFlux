package com.curso.springboot.reactor.client.app.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.curso.springboot.reactor.client.app.models.Producto;
import com.curso.springboot.reactor.client.app.services.ProductoService;

import reactor.core.publisher.Mono;

@Component
public class ProductoHandler {

	@Autowired
	private ProductoService service;
	
	public Mono<ServerResponse> lista (ServerRequest request){
		return ServerResponse.ok()
				.body(service.findAll(), Producto.class);
	}
	
	public Mono<ServerResponse> ver (ServerRequest request){
		String id = request.pathVariable("id");
		return service.findById(id).flatMap(p -> ServerResponse.ok()
				.syncBody(p))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
}
