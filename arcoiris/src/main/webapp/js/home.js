$(document).ready(function() {
	$("header").load("/arcoiris/pages/general/header.html");
});

// # Fale Conosco # //
document.addEventListener("DOMContentLoaded", function() {
	const openModalButton = document.getElementById("openModalButton");
	const modal = document.getElementById("modal");

	// Abrir a modal
	openModalButton.addEventListener("mouseenter", function() {
		modal.style.left = "0vh";
		openModalButton.style.left = "27vh";

	});

	// Fechar a modal
	modal.addEventListener("mouseleave", function() {
		modal.style.left = "-100vh";
		openModalButton.style.left = "-16.6vh"
	});
});

function insertComment() {
	const commentObject = {
		comment: document.getElementById("inputComment").value
	}
	if (commentObject.comment == "") {
		alert("Preencha todos os campos!");
	} else {
		$.ajax({
			type: "POST",
			url: "/arcoiris/rest/comment/insert",
			data: JSON.stringify(commentObject),
			success: function(msg) {
				alert(msg);
				$("#form-faleconosco").trigger("reset");
			},
			error: function(info) {
				alert("Erro ao cadastrar um novo comentário: " + info.status + " - " + info.statusText);
			}
		});
	}
}