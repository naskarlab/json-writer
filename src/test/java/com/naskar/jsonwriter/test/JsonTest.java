package com.naskar.jsonwriter.test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.naskar.jsonwriter.Json;
import com.naskar.jsonwriter.test.model.Autor;
import com.naskar.jsonwriter.test.model.Livro;

/*
{
	"autor": {
		"nome": "rafael",
		"idade": 12,
		"livros": [
			{
				"titulo": "O mundo das coisas",
				"editora": "Fortaleza",
				"publicacao": "2017-03-01 00:00:00-0300"
			},
			{
				"titulo": "Porque não ?",
				"editora": "Fortaleza",
				"publicacao": "2017-04-02 00:00:00-0300"
			}
		]
	}
}
*/
public class JsonTest {
	
	private Date createDate(int year, int month, int dayOfMonth) {
		return Date.from(LocalDate.of(year, month, dayOfMonth)
			.atStartOfDay()
			.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	@Test
	public void testSuccessAutorLivroArray() {
		//Arrange
		String expected = "{\"autor\":{\"nome\":\"rafael\",\"idade\":12,\"livros\":[{\"titulo\":\"O mundo das coisas\",\"editora\":\"Fortaleza\",\"publicacao\":\"2017-03-01 00:00:00-0300\"},{\"titulo\":\"Porque não ?\",\"editora\":\"Fortaleza\",\"publicacao\":\"2017-04-02 00:00:00-0300\"}]}}";
		
		// Act
		String actual = new Json()
			.a("autor").v(autor -> autor
				.a("nome").v("rafael")
				.a("idade").v(12)
				.a("livros").v(
					livros -> livros
						.a("titulo").v("O mundo das coisas")
						.a("editora").v("Fortaleza")
						.a("publicacao").v(createDate(2017, 03, 01)),
					livros -> livros
						.a("titulo").v("Porque não ?")
						.a("editora").v("Fortaleza")
						.a("publicacao").v(createDate(2017, 04, 02))
					)
				)
			.build()
		;
		
		// Assert
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSuccessAutorLivroCollection() {
		//Arrange
		String expected = "{\"autor\":{\"nome\":\"rafael\",\"idade\":12,\"livros\":[{\"titulo\":\"O mundo das coisas\",\"editora\":\"Fortaleza\",\"publicacao\":\"2017-03-01 00:00:00-0300\"},{\"titulo\":\"Porque não ?\",\"editora\":\"Fortaleza\",\"publicacao\":\"2017-04-02 00:00:00-0300\"}]}}";
		
		List<Livro> livros = Arrays.asList(
				new Livro("O mundo das coisas", "Fortaleza", createDate(2017, 03, 01)), 
				new Livro("Porque não ?", "Fortaleza", createDate(2017, 04, 02)));
		
		// Act
		String actual = new Json()
			.a("autor").v(autor -> autor
				.a("nome").v("rafael")
				.a("idade").v(12)
				.a("livros").v(livros, (i, livro) -> i
					.a("titulo").v(livro.getTitulo())
					.a("editora").v(livro.getEditora())
					.a("publicacao").v(livro.getPublicacao()))
				)
			.build()
		;
		
		// Assert
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSuccessSimpleMap() {
		//Arrange
		String expected = "{\"livro\":{\"titulo\":\"O mundo das coisas\",\"editora\":\"Fortaleza\",\"publicacao\":\"2017-03-01 00:00:00-0300\"}}";
		
		Json json = new Json()
			.map(Livro.class, (i, livro) -> i
				.a("titulo").v(livro.getTitulo())
				.a("editora").v(livro.getEditora())
				.a("publicacao").v(livro.getPublicacao())
			);
			
		Livro livro = new Livro("O mundo das coisas", "Fortaleza", createDate(2017, 03, 01));
		
		// Act
		String actual = json.a("livro").v(livro).build();
		
		// Assert
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSuccessComplexMap() {
		//Arrange
		String expected = "{\"autor\":{\"nome\":\"rafael\",\"idade\":12,\"livros\":[{\"titulo\":\"O mundo das coisas\",\"editora\":\"Fortaleza\",\"publicacao\":\"2017-03-01 00:00:00-0300\"},{\"titulo\":\"Porque não ?\",\"editora\":\"Fortaleza\",\"publicacao\":\"2017-04-02 00:00:00-0300\"}]}}";
		
		Json json = new Json()
			.map(Autor.class, (i, autor) -> i
				.a("nome").v(autor.getNome())
				.a("idade").v(autor.getIdade())
				.a("livros").v(autor.getLivros()))
			.map(Livro.class, (i, livro) -> i
				.a("titulo").v(livro.getTitulo())
				.a("editora").v(livro.getEditora())
				.a("publicacao").v(livro.getPublicacao())
			);
			
		Autor autor = new Autor("rafael", 12, Arrays.asList(
				new Livro("O mundo das coisas", "Fortaleza", createDate(2017, 03, 01)), 
				new Livro("Porque não ?", "Fortaleza", createDate(2017, 04, 02))));
				
		// Act
		String actual = json.a("autor").v(autor).build();
		
		// Assert
		Assert.assertEquals(expected, actual);
	}

}
