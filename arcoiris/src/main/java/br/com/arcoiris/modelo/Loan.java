package br.com.arcoiris.modelo;

import java.io.Serializable;
import java.util.Date;

public class Loan implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int bookCode;
	private String userCPF;
	private String startDate;
	private String deadlineDate;
	private String endDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookCode() {
		return bookCode;
	}

	public void setBookCode(int bookCode) {
		this.bookCode = bookCode;
	}

	public String getUserCPF() {
		return userCPF;
	}

	public void setUserCPF(String userCPF) {
		this.userCPF = userCPF;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getDeadlineDate() {
		return deadlineDate;
	}
	
	public void setDeadlineDate(String deadlineDate) {
		this.deadlineDate = deadlineDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
