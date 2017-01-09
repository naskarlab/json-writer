# JSON Writer

Simple classes for create json using method chaining.


## Examples


```
String expected = 
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

// Case 1
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
		

// Case 2:
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
```