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
import br.com.arcoiris.modelo.Loan;

public class JDBCLoanDAO {
	private Connection conexao;

	public JDBCLoanDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public boolean insert(Loan loan) {

		String comando = "INSERT INTO loan (fkbook, fkuser, startDate, deadlineDate) VALUES (?,?,?,?)";
		PreparedStatement p;

		try {
			
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, loan.getBookCode());
			p.setString(2, loan.getUserCPF());
			p.setString(3, loan.getStartDate());
			p.setString(4, loan.getDeadlineDate());
			p.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	public List<Loan> getLoans() {
		String comando = "SELECT * FROM loan order by id desc";
		
		List<Loan> listLoans = new ArrayList<Loan>();
		Loan loan = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				loan = new Loan();

				int id = rs.getInt("id");
				int bookCode = rs.getInt("fkbook");
				String userCPF = rs.getString("fkuser");
				String startDate = rs.getString("startDate");
				String deadlineDate = rs.getString("deadlineDate");
				String endDate = rs.getString("endDate");

				loan.setId(id);
				loan.setBookCode(bookCode);
				loan.setUserCPF(userCPF);
				loan.setStartDate(startDate);
				loan.setDeadlineDate(deadlineDate);
				loan.setEndDate(endDate);

				listLoans.add(loan);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listLoans;

	}
}
