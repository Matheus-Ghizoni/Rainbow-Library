document.addEventListener("DOMContentLoaded", function() {
	getAllUsers();
});

/*---------------------- VALIDAR CPF ----------------------*/

function validarPrimeiroDigito(cpf) {
	let sum = 0;
	for (let i = 0; i < 9; i++) {
		sum += cpf[i] * (10 - i);
	}
	const resto = (sum * 10) % 11;
	if (resto < 10) {
		return cpf[9] == resto;
	}
	return cpf[9] == 0;
}

function validarSegundoDigito(cpf) {
	let sum = 0;
	for (let i = 0; i < 10; i++) {
		sum += cpf[i] * (11 - i);
	}
	const resto = (sum * 10) % 11;
	if (resto < 10) {
		return cpf[10] == resto;
	}
	return cpf[10] == 0;
}

function validarRepetido(cpf) {
	const primeiro = cpf[0];
	let diferente = false;
	for (let i = 1; i < cpf.length; i++) {
		if (cpf[i] != primeiro) {
			diferente = true;
		}
	}
	return diferente;
}

function validarCpf(cpf) {
	if (cpf.length != 11) {
		return false;
	}
	if (!validarRepetido(cpf)) {
		return false;
	}
	if (!validarPrimeiroDigito(cpf)) {
		return false;
	}
	if (!validarSegundoDigito(cpf)) {
		return false;
	}
	return true;
}

/*---------------------- REST USER ----------------------*/
function insert() {
	let user = {
		CPF: document.getElementById("inputCPF").value,
		name: document.getElementById("inputName").value,
		email: document.getElementById("inputEmail").value,
		password: btoa(document.getElementById("inputPassword").value),
		type: document.getElementById("inputType").value
	};
	
	if(!validarCpf(user.CPF)){
		alert('CPF inválido');
		return false;
	}

	console.log(user);

	if ((user.CPF == "") || (user.name == "") || (user.email == "") || (user.type == "") || (user.password == "")) {
		alert("Preencha todos os campos!");
	} else {
		$.ajax({
			type: "POST",
			url: "/arcoiris/rest/user/insert",
			data: JSON.stringify(user),
			success: function(msg) {
				alert(msg);
				$("#frmuser").trigger("reset");
			},
			error: function(info) {
				alert("Erro ao cadastrar um novo usuário: " + info.status + " - " + info.statusText);
			}
		});
	}

}

function getAllUsers() {
	$.ajax({
		type: "GET",
		url: "/arcoiris/rest/user/getAllUsers",
		success: function(data) {
			dados = JSON.parse(data);
			$("#listUsers").html(displayUsers(dados));
		},
		error: function(info) {
			alert("Erro ao buscar usuários: " + info.status + " - " + info.statusText);
		}
	})
}

function displayUsers(listUsers) {

	let tableUsers = "<table>" +
		"<tr>" +
		"<th>CPF</th>" +
		"<th>Nome</th>" +
		"<th>E-mail</th>" +
		"<th>Type</th>" +
		"<th>Status</th>" +
		"<th>Ações</th>" +
		"</tr>";

	if (listUsers != undefined && listUsers.length > 0) {

		for (var i = 0; i < listUsers.length; i++) {
			tableUsers += "<tr>" +
				"<td>" + listUsers[i].CPF + "</td>" +
				"<td>" + listUsers[i].name + "</td>" +
				"<td>" + listUsers[i].email + "</td>" +
				"<td>" + listUsers[i].type + "</td>" +
				"<td>";
			if (listUsers[i].status == 1) {
				tableUsers += "<label class='switch'>" +
					"<input onclick=\"inactivate('" + listUsers[i].CPF + "')\" id='status' type='checkbox' checked>" +
					"<span class='slider round'></span>" +
					"</label>"
			} else {
				tableUsers += "<label class='switch'>" +
					"<input onclick=\"inactivate('" + listUsers[i].CPF + "')\" id='status' type='checkbox'>" +
					"<span class='slider round'></span>" +
					"</label>"
			}
			tableUsers += "</td><td>" +
				"<button onclick=\"deleteUser('" + listUsers[i].CPF + "')\">Excluir</button>" +
				"<button onclick=\"getUser('" + listUsers[i].CPF + "')\">Editar</button>" +
				"</td>" +
				"</tr>"
		}

	} else if (listUsers == "") {
		tableUsers += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
	}
	tableUsers += "</table>";

	return tableUsers;
}

function deleteUser(id) {
	$.ajax({
		type: "DELETE",
		url: "/arcoiris/rest/user/deleteUser/" + id,
		success: function(msg) {
			alert(msg);
		},
		error: function(info) {
			alert("Erro ao deletar usuário: " + info.status + " - " + info.statusText);
		}
	})
}

function getUser(CPF) {
	$.ajax({
		type: "GET",
		url: "/arcoiris/rest/user/getUser",
		data: "CPF=" + CPF,
		success: function(user) {
			const ModalEdit = document.getElementById("modalEditUser");
			const inputEditPassword = document.getElementById("inputEditPassword");
			const inputEditCPF = document.getElementById("inputEditCPF");
			const inputEditName = document.getElementById("inputEditName");
			const inputEditEmail = document.getElementById("inputEditEmail");
			const inputEditType = document.getElementById("inputEditType");

			ModalEdit.style.display = "block";
			if (user.type == 1) {
				inputEditPassword.style.display = "block";
			} else {
				inputEditPassword.style.display = "none";
			}

			inputEditCPF.value = user.CPF;
			inputEditName.value = user.name;
			inputEditEmail.value = user.email;
			inputEditType.value = user.type;

		},
		error: function(info) {
			alert("Erro ao buscar usuários: " + info.status + " - " + info.statusText);
		}
	})
}

function editUser() {

	let user = {
		CPF: document.getElementById("inputEditCPF").value,
		name: document.getElementById("inputEditName").value,
		email: document.getElementById("inputEditEmail").value,
		password: document.getElementById("inputEditPassword").value,
		type: document.getElementById("inputEditType").value
	}

	$.ajax({
		type: "PUT",
		url: "/arcoiris/rest/user/editUser",
		data: JSON.stringify(user),
		success: function(msg) {
			alert(msg);
			ModalEdit.style.display = "none";
		},
		error: function(info) {
			alert(info);
		}
	});
}

function inactivate(CPF) {

	$.ajax({
		type: "PUT",
		url: "/arcoiris/rest/user/inactivate/" + CPF,
		success: function(msg) {
			alert(msg);
		},
		error: function(info) {
			alert("Erro alterar status do usuário: " + info.status + " - " + info.statusText);
		}
	});

}