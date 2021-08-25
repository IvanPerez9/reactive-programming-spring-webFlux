package com.curso.springboot.reactor.app;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.curso.springboot.reactor.app.models.documents.Producto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProgramacionReactivaSpringWebFluxApirestApplicationTests {

	@Autowired
	private WebTestClient client;
	
	@Test
	void listarTest() {
		
		client.get()
			.uri("/api/v2/productos")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBodyList(Producto.class);
		
	}
	
	@Test
	void verTest() {
		
		client.get()
			.uri("/api/v2/productos")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			;
	}

}
