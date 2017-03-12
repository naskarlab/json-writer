package com.naskar.jsonwriter.test.model;

import java.util.List;

public class Autor {
	private String nome;
	private Integer idade;
	private List<Livro> livros;
	
	public Autor(String nome, Integer idade, List<Livro> livros) {
		super();
		this.nome = nome;
		this.idade = idade;
		this.livros = livros;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	public List<Livro> getLivros() {
		return livros;
	}
	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}
}
