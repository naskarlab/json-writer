package com.naskar.jsonwriter.test.model;

import java.util.Date;

public class Livro {
	private String titulo;
	private String editora;
	private Date publicacao;
	public Livro(String titulo, String editora, Date publicacao) {
		super();
		this.titulo = titulo;
		this.editora = editora;
		this.publicacao = publicacao;
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
	public Date getPublicacao() {
		return publicacao;
	}
	public void setPublicacao(Date publicacao) {
		this.publicacao = publicacao;
	}
}
