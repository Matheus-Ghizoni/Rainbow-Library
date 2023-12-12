package br.com.arcoiris.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.arcoiris.modelo.Book;
import br.com.arcoiris.modelo.User;

public class JDBCUserDAO {

	private Connection conexao;

	public JDBCUserDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public boolean insert(User user) {

		String comando = "INSERT INTO user (cpf, name, email, password, type) VALUES (?,?,?,?,?)";
		PreparedStatement p;

		try {

			p = this.conexao.prepareStatement(comando);

			p.setString(1, user.getCPF());
			p.setString(2, user.getName());
			p.setString(3, user.getEmail());
			p.setString(4, user.getPassword());
			p.setInt(5, user.getType());

			p.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public List<User> getUsers() {

		String comando = "SELECT * FROM user order by name";
		List<User> listUsers = new ArrayList<User>();
		User user = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				user = new User();
				String cpf = rs.getString("CPF");
				String name = rs.getString("name");
				String email = rs.getString("email");
				int type = rs.getInt("type");
				int status = rs.getInt("status");

				user.setCPF(cpf);
				user.setName(name);
				user.setEmail(email);
				user.setType(type);
				user.setStatus(status);

				listUsers.add(user);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listUsers;

	}

	public User getUser(String CPF) {

		String comando = "SELECT * FROM user where CPF = ?";
		User user = null;
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setString(1, CPF);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				user = new User();
				String cpf = rs.getString("CPF");
				String name = rs.getString("name");
				String email = rs.getString("email");
				int type = rs.getInt("type");

				user.setCPF(cpf);
				user.setName(name);
				user.setEmail(email);
				user.setType(type);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return user;

	}

	public boolean delete(String cpf) {
		String comando = "DELETE FROM user WHERE CPF = ?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, cpf);
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean edit(User user) {

		String comando = "UPDATE user SET name=?, email=?, password=?, type=? WHERE CPF=?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, user.getName());
			p.setString(2, user.getEmail());
			p.setString(3, user.getPassword());
			p.setInt(4, user.getType());
			p.setString(5, user.getCPF());
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean inactivate(String CPF) {

		String comando = "UPDATE user SET status = CASE WHEN status = 0 THEN 1 ELSE 0 END where CPF = ?";
		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, CPF);
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
}
