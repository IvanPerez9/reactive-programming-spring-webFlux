package com.curso.springboot.reactor.app.models.controllers;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.curso.springboot.reactor.app.models.documents.Producto;
import com.curso.springboot.reactor.app.models.services.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	@Autowired
	private ProductoService service;

	@Value("${config.uploads.path}")
	private String path;

//	@GetMapping
//	public Flux<Producto> lista1() {
//		return service.findAll();
//	}

	/*
	 * Es igual que el de arriba, pero con ResponseEntity
	 */
	@GetMapping
	public Mono<ResponseEntity<Flux<Producto>>> lista2() {
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll()));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<Producto>> verPorId(@PathVariable String id) {
		return service.findById(id).map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<Producto>> crear(@RequestBody Producto producto) {
		if (producto.getCreateAt() == null) {
			producto.setCreateAt(new Date());
		}

		return service.save(producto)
				.map(p -> ResponseEntity.created(URI.create("/api/productos/".concat(producto.getId())))
						.contentType(MediaType.APPLICATION_JSON).body(p));

		/*
		 * 
		 * return Optional.ofNullable(response).map((Mono<PSD2AggregationResponseDto>
		 * mono) -> mono.block()) .map((PSD2AggregationResponseDto responseMap) -> Mono
		 * .just(ResponseEntity.status(responseMap.getCode()).body(responseMap)))
		 * .orElse(Mono.empty());
		 * 
		 */
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Producto>> editar(@PathVariable String id, @RequestBody Producto producto) {

		return service.findById(id).flatMap(p -> {
			p.setNombre(producto.getNombre());
			p.setPrecio(producto.getPrecio());
			p.setCategoria(producto.getCategoria());
			return service.save(p)
					.map(x -> ResponseEntity.created(URI.create("/api/productos/".concat(producto.getId())))
							.contentType(MediaType.APPLICATION_JSON).body(x))
					.defaultIfEmpty(ResponseEntity.notFound().build());
		});
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id) {
		return service.findById(id).flatMap(p -> {
			return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

	/*
	 * Ojo con la ruta que tenemos en el properties, nuestro path @value
	 */
	@PostMapping("/upload/{id}")
	public  Mono<ResponseEntity<Producto>> upload (@PathVariable String id, @RequestPart FilePart file){
		return service.findById(id).flatMap(p -> {
			p.setFoto(UUID.randomUUID().toString() + "-" + file.filename()
					.replace(" ", "")
					.replace(":", "")
					.replace("\\", ""));
			
					return file.transferTo(new File(path + p.getFoto())).then(service.save(p));
		}).map(x -> ResponseEntity.ok(x)).defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
