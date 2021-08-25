package com.curso.springboot.reactor.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.curso.springboot.reactor.app.handler.ProductoHandler;
import com.curso.springboot.reactor.app.models.documents.Producto;
import com.curso.springboot.reactor.app.models.services.ProductoService;

@Configuration
public class RouterFunctionConfig {

	@Autowired
	private ProductoService service;

	@Bean
	public RouterFunction<ServerResponse> routes() {
		return RouterFunctions.route(RequestPredicates.GET("/api/v2/productos"), request -> {
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll(), Producto.class);
		});
	}

	/*
	 * Igual pero con handler, desacoplado
	 */
	@Bean
	public RouterFunction<ServerResponse> routes2(ProductoHandler handler) {
		
		return RouterFunctions.route(RequestPredicates.GET("/api/v2/productos").or(RequestPredicates.GET("/api/v3/productos")), handler::lista)
				.andRoute(RequestPredicates.GET("/api/v2/productos/{id}"), handler::ver)
				.andRoute(RequestPredicates.POST("/api/v2/productos"), handler::crear)
				.andRoute(RequestPredicates.PUT("/api/v2/productos/{id}"), handler::editar)
				.andRoute(RequestPredicates.DELETE("/api/v2/productos/{id}"), handler::eliminar);
	}

}
