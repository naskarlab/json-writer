package com.naskar.jsonwriter.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.naskar.jsonwriter.Json;
import com.naskar.jsonwriter.test.model.Autor;
import com.naskar.jsonwriter.test.model.Livro;

/*
{
	"autor": {
		"nome": rafael,
		"idade": 12,
		"livros": [
			{
				"titulo": "O mundo das coisas"
				"editora": "Fortaleza"
			},
			{
				"titulo": "Porque não ?"
				"editora": "Fortaleza"
			}
		]
	}
}
*/
public class JsonTest {
	
	@Test
	public void testSuccessAutorLivroArray() {
		//Arrange
		String expected = "{\"autor\":{\"nome\":\"rafael\",\"idade\":12,\"livros\":[{\"titulo\":\"O mundo das coisas\",\"editora\":\"Fortaleza\"},{\"titulo\":\"Porque não ?\",\"editora\":\"Fortaleza\"}]}}";
		
		// Act
		String actual = new Json()
			.a("autor").v(autor -> autor
				.a("nome").v("rafael")
				.a("idade").v(12)
				.a("livros").v(
					livros -> livros
						.a("titulo").v("O mundo das coisas")
						.a("editora").v("Fortaleza"),
					livros -> livros
						.a("titulo").v("Porque não ?")
						.a("editora").v("Fortaleza")
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
		String expected = "{\"autor\":{\"nome\":\"rafael\",\"idade\":12,\"livros\":[{\"titulo\":\"O mundo das coisas\",\"editora\":\"Fortaleza\"},{\"titulo\":\"Porque não ?\",\"editora\":\"Fortaleza\"}]}}";
		
		List<Livro> livros = Arrays.asList(
				new Livro("O mundo das coisas", "Fortaleza"), 
				new Livro("Porque não ?", "Fortaleza"));
		
		// Act
		String actual = new Json()
			.a("autor").v(autor -> autor
				.a("nome").v("rafael")
				.a("idade").v(12)
				.a("livros").v(livros, (i, livro) -> i
					.a("titulo").v(livro.getTitulo())
					.a("editora").v(livro.getEditora()))
				)
			.build()
		;
		
		// Assert
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSuccessSimpleMap() {
		//Arrange
		String expected = "{\"livro\":{\"titulo\":\"O mundo das coisas\",\"editora\":\"Fortaleza\"}}";
		
		Json json = new Json()
			.map(Livro.class, (i, livro) -> i
				.a("titulo").v(livro.getTitulo())
				.a("editora").v(livro.getEditora()));
			
		Livro livro = new Livro("O mundo das coisas", "Fortaleza");
		
		// Act
		String actual = json.a("livro").v(livro).build();
		
		// Assert
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSuccessComplexMap() {
		//Arrange
		String expected = "{\"autor\":{\"nome\":\"rafael\",\"idade\":12,\"livros\":[{\"titulo\":\"O mundo das coisas\",\"editora\":\"Fortaleza\"},{\"titulo\":\"Porque não ?\",\"editora\":\"Fortaleza\"}]}}";
		
		Json json = new Json()
			.map(Autor.class, (i, autor) -> i
				.a("nome").v(autor.getNome())
				.a("idade").v(autor.getIdade())
				.a("livros").v(autor.getLivros()))
			.map(Livro.class, (i, livro) -> i
				.a("titulo").v(livro.getTitulo())
				.a("editora").v(livro.getEditora()));
			
		Autor autor = new Autor("rafael", 12, Arrays.asList(
				new Livro("O mundo das coisas", "Fortaleza"), 
				new Livro("Porque não ?", "Fortaleza")));
				
		// Act
		String actual = json.a("autor").v(autor).build();
		
		// Assert
		Assert.assertEquals(expected, actual);
	}

}
