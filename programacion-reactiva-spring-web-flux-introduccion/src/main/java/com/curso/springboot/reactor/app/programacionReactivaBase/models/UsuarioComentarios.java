package com.curso.springboot.reactor.app.programacionReactivaBase.models;

public class UsuarioComentarios {

	private Usuario user;
	
	private Comentarios comentarios;

	public UsuarioComentarios(Usuario user, Comentarios comentarios) {
		this.user = user;
		this.comentarios = comentarios;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Comentarios getComentarios() {
		return comentarios;
	}

	public void setComentarios(Comentarios comentarios) {
		this.comentarios = comentarios;
	}

	@Override
	public String toString() {
		return "UsuarioComentarios [user=" + user + ", comentarios=" + comentarios + "]";
	}
	
}
