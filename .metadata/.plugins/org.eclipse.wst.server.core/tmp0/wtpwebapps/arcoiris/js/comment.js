document.addEventListener("DOMContentLoaded", function() {
	getAllComments();
});

function getAllComments() {
	$.ajax({
		type: "GET",
		url: "/arcoiris/rest/comment/getAllComments",
		success: function(data) {
			dados = JSON.parse(data);
			$("#listComments").html(displayComments(dados));
		},
		error: function(info) {
			alert("Erro ao buscar comentários: " + info.status + " - " + info.statusText);
		}
	})
}

function displayComments(listComments) {
	let tableComments = "";

	if (listComments != undefined && listComments.length > 0) {

		for (var i = 0; i < listComments.length; i++) {
			tableComments += "<textarea disabled style='resize: none'>" + listComments[i].comment +"</textarea> <button onclick='deleteComment("+listComments[i].id+")'>Excluir</button> </br>"
		}

	} else if (listBooks == "") {
		tableComments += "<h3>Nenhum registro encontrado</h3>";
	}

	return tableComments;
}

function deleteComment(id) {
		$.ajax({
		type: "DELETE",
		url: "/arcoiris/rest/comment/deleteComment/" + id,
		success: function(msg) {
			alert(msg);
		},
		error: function(info) {
			alert("Erro ao deletar comentários: " + info.status + " - " + info.statusText);
		}
	})
}