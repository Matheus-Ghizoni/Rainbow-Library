package br.com.arcoiris.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import br.com.arcoiris.modelo.User;

public class JDBCAutenticaDAO {
	
	private Connection conexao;
	public JDBCAutenticaDAO (Connection conexao) {
		
		this.conexao = conexao;
	}
	
	public boolean consultar (User dadosautentica) {
		
		try {
			String comando = "SELECT * FROM user WHERE cpf = '"+dadosautentica.getCPF()+"' and password = '"+dadosautentica.getPassword()+"' and type = 1 and status = 1";
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
			return false;
		}
	}
}