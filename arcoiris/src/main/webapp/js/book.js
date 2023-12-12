document.addEventListener("DOMContentLoaded", function() {
	loadCategory();
	getAllBooks();
	getAllCategorys();
});

/*---------------------- REST BOOKS ----------------------*/
function loadCategory(id) {
	if (id != undefined) {
		select = "#inputEditCategoryBook";
	} else {
		select = "#inputBookCategory";
	}
	$.ajax({
		type: "GET",
		url: "/arcoiris/rest/category/getAllCategorys",
		success: function(data) {
			categorys = JSON.parse(data);
			if (categorys != "") {

				$(select).html("");
				var option = document.createElement("option");
				option.setAttribute("value", "");
				option.innerHTML = ("Selecione");
				$(select).append(option);

				for (var i = 0; i < categorys.length; i++) {
					if (categorys[i].status == 1) {
						var option = document.createElement("option");
						option.setAttribute("value", categorys[i].id);

						if ((id != undefined) && (id == categorys[i].id)) {
							option.setAttribute("selected", "selected");
						}
						option.innerHTML = (categorys[i].name);
						$(select).append(option);
					}
				}

			} else {

				$(select).html("");

				var option = document.createElement("option");
				option.setAttribute("value", "");
				option.innerHTML = ("Cadastre uma categoria primeiro!");
				$(select).append(option);
				$(select).addClass("aviso");

			}

		},
		error: function(info) {
			alert("Erro ao buscar as categorias: " + info.status + " - " + info.statusText);

			$(select).html("");
			var option = document.createElement("option");
			option.setAttribute("value", "");
			option.innerHTML = ("Erro ao carregar categorias!");
			$(select).append(option);
			$(select).addClass("aviso");
		}
	});
}

function insertBook() {
	const titleBook = document.getElementById("inputBookTitle");
	const authorBook = document.getElementById("inputBookAuthor");
	const locationBook = document.getElementById("inputBookLocation");
	const categoryBook = document.getElementById("inputBookCategory");

	const book = {
		title: titleBook.value,
		author: authorBook.value,
		location: locationBook.value,
		category: categoryBook.value
	};

	if ((book.title == "") || (book.author == "") || (book.location == "") || (book.category == "")) {
		alert("Preencha todos os campos!");
	} else {
		$.ajax({
			type: "POST",
			url: "/arcoiris/rest/book/insert",
			data: JSON.stringify(book),
			success: function(msg) {
				alert(msg);
				$("#frmbook").trigger("reset");
			},
			error: function(info) {
				alert("Erro ao cadastrar um novo livro: " + info.status + " - " + info.statusText);
			}
		});
	}
}

function getAllBooks() {
	const title = document.getElementById("navBook").value;
	$.ajax({
		type: "GET",
		url: "/arcoiris/rest/book/getAllBooks",
		data: "title=" + title,
		success: function(dados) {
			dados = JSON.parse(dados);
			$("#listBooks").html(displayBooks(dados));
		},
		error: function(info) {
			alert("Erro ao buscar livros: " + info.status + " - " + info.statusText);
		}
	})
}

function getAllBooksCategory(fkcategory) {
	const category = document.getElementById("filterCategory" + fkcategory);
	const isChecked = category.checked;
	if (!isChecked) {
		return getAllBooks();
	}
	$.ajax({
		type: "GET",
		url: "/arcoiris/rest/book/getAllBooksCategory",
		data: "fkcategory=" + fkcategory,
		success: function(dados) {
			dados = JSON.parse(dados);
			$("#listBooks").html(displayBooks(dados));
		},
		error: function(info) {
			alert("Erro ao buscar livros: " + info.status + " - " + info.statusText);
		}
	})
}

function displayBooks(listBooks) {
	let tableBooks = "<table>" +
		"<tr>" +
		"<th>Codigo</th>" +
		"<th>Título</th>" +
		"<th>Autor</th>" +
		"<th>Localização</th>" +
		"<th>Categoria</th>" +
		"<th>Status</th>" +
		"<th>Ações</th>" +
		"</tr>";

	if (listBooks != undefined && listBooks.length > 0) {

		for (var i = 0; i < listBooks.length; i++) {
			tableBooks += "<tr>" +
				"<td>" + listBooks[i].code + "</td>" +
				"<td>" + listBooks[i].title + "</td>" +
				"<td>" + listBooks[i].author + "</td>" +
				"<td>" + listBooks[i].location + "</td>" +
				"<td>" + listBooks[i].namecategory + "</td>" +
				"<td>";
			if (listBooks[i].status == 1) {
				tableBooks += "<label class='switch'>" +
					"<input onclick=\"inactivateBook(" + listBooks[i].code + ")\" id='statusBook' type='checkbox' checked>" +
					"<span class='slider round'></span>" +
					"</label>"
			} else {
				tableBooks += "<label class='switch'>" +
					"<input onclick=\"inactivateBook(" + listBooks[i].code + ")\" id='statusBook' type='checkbox'>" +
					"<span class='slider round'></span>" +
					"</label>"
			}
			tableBooks += "</td>" +
				"<td><button onclick='deleteBook(" + listBooks[i].code + ")'>Excluir</button>" +
				"<button onclick=\"getBookCode(" + listBooks[i].code + ")\">Editar</button></td>" +
				"</tr>"
		}

	} else if (listBooks == "") {
		tableBooks += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
	}
	tableBooks += "</table>";

	return tableBooks;
}

function getCategory(id) {
	$.ajax({
		type: "GET",
		url: "/arcoiris/rest/category/getCategory",
		data: "id=" + id,
		success: function(category) {
			return category
		},
		error: function(info) {
			alert("Erro ao buscar categoria: " + info.status + " - " + info.statusText);
		}
	})
}

function deleteBook(id) {
	$.ajax({
		type: "DELETE",
		url: "/arcoiris/rest/book/deleteBook/" + id,
		success: function(msg) {
			alert(msg);
		},
		error: function(info) {
			alert("Erro ao deletar livro: " + info.status + " - " + info.statusText);
		}
	})
}

function getAllCategorys() {
	$.ajax({
		type: "GET",
		url: "/arcoiris/rest/category/getAllCategorys",
		success: function(data) {
			dados = JSON.parse(data);
			$("#listCategorys").html(displayCategorys(dados));
		},
		error: function(info) {
			alert("Erro ao buscar categorias: " + info.status + " - " + info.statusText);
		}
	})
}

function displayCategorys(categorys) {
	let tableCategorys = "<table>"
	for (var i = 0; i < categorys.length; i++) {
		tableCategorys += "<tr>" +
			"<td>" +
			"<input type='checkbox' id='filterCategory" + categorys[i].id + "' onclick='getAllBooksCategory(" + categorys[i].id + ")'>" + categorys[i].name +
			"</td>";
	}
	tableCategorys += "</table>";

	return tableCategorys;
}

function getBookCode(code) {
	$.ajax({
		type: "GET",
		url: "/arcoiris/rest/book/getBookCode",
		data: "code=" + code,
		success: function(book) {
			const ModalEditBook = document.getElementById("modalEditBook");
			const inputEditCodeBook = document.getElementById("inputEditCodeBook");
			const inputEditTitleBook = document.getElementById("inputEditTitleBook");
			const inputEditAuthorBook = document.getElementById("inputEditAuthorBook");
			const inputEditLocationBook = document.getElementById("inputEditLocationBook");

			ModalEditBook.style.display = "block";

			inputEditCodeBook.value = book.code;
			inputEditTitleBook.value = book.title;
			inputEditAuthorBook.value = book.author;
			inputEditLocationBook.value = book.location;
			loadCategory(book.fkcategory);

		},
		error: function(info) {
			alert("Erro ao buscar livro: " + info.status + " - " + info.statusText);
		}
	})
}

function editBook() {

	let book = {
		code: document.getElementById("inputEditCodeBook").value,
		title: document.getElementById("inputEditTitleBook").value,
		author: document.getElementById("inputEditAuthorBook").value,
		location: document.getElementById("inputEditLocationBook").value,
		fkcategory: document.getElementById("inputEditCategoryBook").value
	}

	$.ajax({
		type: "PUT",
		url: "/arcoiris/rest/book/edit",
		data: JSON.stringify(book),
		success: function(msg) {
			alert(msg);
			ModalEditBook.style.display = "none";
		},
		error: function(info) {
			alert(info);
		}
	});
}

function inactivateBook(code) {
	console.log(code);
	$.ajax({
		type: "PUT",
		url: "/arcoiris/rest/book/inactivate/" + code,
		success: function(msg) {
			alert(msg);
		},
		error: function(info) {
			alert("Erro alterar status do livro: " + info.status + " - " + info.statusText);

		}
	});
}





