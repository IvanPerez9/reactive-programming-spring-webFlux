package com.curso.springboot.reactor.client.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.curso.springboot.reactor.client.app.handler.ProductoHandler;

@Configuration
public class RouterConfig {
	
	@Bean
	public RouterFunction<ServerResponse> rutas (ProductoHandler handler){
		return RouterFunctions.route(RequestPredicates.GET("/api/client"), handler::lista)
				.andRoute(RequestPredicates.GET("/api/client"), handler::ver);
	}
	
}
