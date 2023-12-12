package br.com.arcoiris.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.arcoiris.modelo.Book;
import br.com.arcoiris.modelo.Comment;

public class JDBCCommentDAO {

private Connection conexao;
	
	public JDBCCommentDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean insert(Comment comment) {

		String comando = "INSERT INTO comment (`comment`) VALUES (?)";
		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, comment.getComment());
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	public List<Comment> getComments() {
		
		String comando = "SELECT * FROM comment order by id desc";
		List<Comment> listComments = new ArrayList<Comment>();
		Comment commentObject = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				commentObject = new Comment();
				int id = rs.getInt("id");
				String comment = rs.getString("comment");
				
				commentObject.setId(id);
				commentObject.setComment(comment);
				
				listComments.add(commentObject);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listComments;
		
	}
	
	public boolean delete(int id) {
		String comando = "DELETE FROM comment WHERE id = ?";
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
