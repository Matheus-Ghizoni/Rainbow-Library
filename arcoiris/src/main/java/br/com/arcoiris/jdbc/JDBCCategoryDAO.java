package br.com.arcoiris.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.arcoiris.modelo.Book;
import br.com.arcoiris.modelo.Category;

public class JDBCCategoryDAO {

	private Connection conexao;

	public JDBCCategoryDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public boolean insert(String category) {

		String comando = "INSERT INTO category (name) VALUES (?)";
		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, category);
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public List<Category> getCategorys() {

		String comando = "SELECT * FROM category order by id desc";
		List<Category> listCategorys = new ArrayList<Category>();
		Category category = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				category = new Category();
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int status = rs.getInt("status");

				category.setId(id);
				category.setName(name);
				category.setStatus(status);

				listCategorys.add(category);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listCategorys;

	}

	public boolean delete(int id) {
		String comando = "DELETE FROM category WHERE id = ?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean edit(Category category) {

		String comando = "UPDATE category SET name=? WHERE id=?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, category.getName());
			p.setInt(2, category.getId());
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Category getCategory(int idParam) {

		String comando = "SELECT * FROM category where id = ?";
		Category category = null;
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, idParam);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				category = new Category();
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int status = rs.getInt("status");

				category.setId(id);
				category.setName(name);
				category.setStatus(status);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return category;

	}

	public boolean inactivate(int id) {

		String comando = "UPDATE category SET status = CASE WHEN status = 0 THEN 1 ELSE 0 END where id = ?";
		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

}
