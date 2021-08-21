package com.curso.springboot.reactor.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.curso.springboot.reactor.app.dao.Producto;
import com.curso.springboot.reactor.app.services.ProductoService;

import reactor.core.publisher.Flux;

@Controller
public class ProductController {

	@Autowired
	private ProductoService productoService;
	
	// Con el {} permite mas de una ruta
	@GetMapping({"/listar", "/"})
	private String listar(Model model) {
		Flux<Producto> productos = productoService.findAll();
		
		model.addAttribute("productos", productos);
		model.addAttribute("titulo","Listado de productos");
		return "listar";
	}
	
	
	
}
