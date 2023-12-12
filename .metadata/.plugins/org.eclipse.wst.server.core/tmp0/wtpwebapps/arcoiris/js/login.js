function ValidaDados() {
	
	var senhaembase64 = btoa (document.frmLogin.password.value);
	
	document.frmLogin.password.value = senhaembase64;
	
	return true;
	
}