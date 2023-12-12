document.addEventListener("DOMContentLoaded", function() {
	getAllCategorys();
});

/*---------------------- REST CATEGORYS ----------------------*/

function insertCategory() {
	const category = document.getElementById("inputCategory").value;
	if (category == "") {
		alert("Preencha todos os campos!");
	} else {
		$.ajax({
			type: "POST",
			url: "/arcoiris/rest/category/insert",
			data: category,
			success: function(msg) {
				alert(msg);
				$("#frmcategory").trigger("reset");
			},
			error: function(info) {
				alert("Erro ao cadastrar uma nova categoria: " + info.status + " - " + info.statusText);
			}
		});
	}
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

function displayCategorys(listCategorys) {
	let tableCategorys = "<table>" +
		"<tr>" +
		"<th>Nome</th>" +
		"<th>Status</th>" +
		"<th>Ações</th>" +
		"</tr>";

	if (listCategorys != undefined && listCategorys.length > 0) {

		for (var i = 0; i < listCategorys.length; i++) {
			tableCategorys += "<tr>" +
				"<td>" + listCategorys[i].name + "</td>" +
				"<td>";
			if (listCategorys[i].status == 1) {
				tableCategorys += "<label class='switch'>" +
					"<input onclick=\"inactivateCategory('" + listCategorys[i].id + "')\" id='statusCategory' type='checkbox' checked>" +
					"<span class='slider round'></span>" +
					"</label>"
			} else {
				tableCategorys += "<label class='switch'>" +
					"<input onclick=\"inactivateCategory('" + listCategorys[i].id + "')\" id='statusCategory' type='checkbox'>" +
					"<span class='slider round'></span>" +
					"</label>"
			}

			tableCategorys += "</td>" +
				"<td><button onclick='deleteCategory(" + listCategorys[i].id + ")'>Excluir</button>" +
				"<button onclick=\"getCategory(" + listCategorys[i].id + ")\">Editar</button>" +
				"</td>" +
				"</tr>"
		}

	} else if (listCategorys == "") {
		tableCategorys += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
	}
	tableCategorys += "</table>";

	return tableCategorys;
}

function deleteCategory(id) {
	$.ajax({
		type: "DELETE",
		url: "/arcoiris/rest/category/deleteCategory/" + id,
		success: function(msg) {
			alert(msg);
			getAllCategorys();
		},
		error: function(info) {
			alert("Erro ao deletar categoria: " + info.status + " - " + info.statusText);
		}
	})
}

function getCategory(id) {
	$.ajax({
		type: "GET",
		url: "/arcoiris/rest/category/getCategory",
		data: "id=" + id,
		success: function(category) {
			const ModalEditCategory = document.getElementById("modalEditCategory");
			const inputEditIdCategory = document.getElementById("inputEditIdCategory");
			const inputEditNameCategory = document.getElementById("inputEditNameCategory");

			ModalEditCategory.style.display = "block";
			inputEditIdCategory.value = category.id;
			inputEditNameCategory.value = category.name;

		},
		error: function(info) {
			alert("Erro ao buscar categoria: " + info.status + " - " + info.statusText);
		}
	})
}

function editCategory() {

	let category = {
		id: document.getElementById("inputEditIdCategory").value,
		name: document.getElementById("inputEditNameCategory").value,
	}

	$.ajax({
		type: "PUT",
		url: "/arcoiris/rest/category/edit",
		data: JSON.stringify(category),
		success: function(msg) {
			alert(msg);
			ModalEditCategory.style.display = "none";
		},
		error: function(info) {
			alert(info);
		}
	});
}

function inactivateCategory(id) {

	$.ajax({
		type: "PUT",
		url: "/arcoiris/rest/category/inactivate/" + id,
		success: function(msg) {
			alert(msg);
		},
		error: function(info) {
			alert("Erro alterar status da categoria: " + info.status + " - " + info.statusText);

		}
	});

}