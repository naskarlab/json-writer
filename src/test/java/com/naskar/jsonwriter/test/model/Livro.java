package com.naskar.jsonwriter.test.model;

public class Livro {
	private String titulo;
	private String editora;
	public Livro(String titulo, String editora) {
		super();
		this.titulo = titulo;
		this.editora = editora;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}
}
