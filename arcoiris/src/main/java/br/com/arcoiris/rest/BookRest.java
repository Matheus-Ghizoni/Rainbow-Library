package br.com.arcoiris.rest;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.arcoiris.bd.Conexao;
import br.com.arcoiris.jdbc.JDBCBookDAO;
import br.com.arcoiris.jdbc.JDBCCategoryDAO;
import br.com.arcoiris.jdbc.JDBCUserDAO;
import br.com.arcoiris.modelo.Book;
import br.com.arcoiris.modelo.User;

import java.util.ArrayList;
import java.util.List;

@Path("book")
public class BookRest extends UtilRest {

	@POST
	@Path("/insert")
	@Consumes("application/*")
	public Response insert(String bookParam) {
		try {
			Book book = new Gson().fromJson(bookParam, Book.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();

			JDBCBookDAO jdbcBook = new JDBCBookDAO(conexao);
			boolean retorno = jdbcBook.insert(book);
			String msg = "";

			if (retorno) {
				msg = "Livro cadastrado com sucesso";
			} else {
				msg = "Erro ao cadastrar livro";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@GET
	@Path("/getAllBooks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBooks(@QueryParam("title") String title) {

		try {
			List<Book> listBooks = new ArrayList<Book>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCBookDAO jdbcBook = new JDBCBookDAO(conexao);
			listBooks = jdbcBook.getBooks(title);
			
			conec.fecharConexao();
			String json = new Gson().toJson(listBooks);
			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
	@GET
	@Path("/getAllBooksCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBooksCategory(@QueryParam("fkcategory") int fkcategory) {
		
		try {
			List<Book> listBooks = new ArrayList<Book>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCBookDAO jdbcBook = new JDBCBookDAO(conexao);
			listBooks = jdbcBook.getBooksCategory(fkcategory);
			conec.fecharConexao();
			String json = new Gson().toJson(listBooks);
			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
	@DELETE
	@Path("/deleteBook/{id}")
	@Consumes("application/*")
	public Response deleteBook(@PathParam("id") int id) {

		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCBookDAO jdbcBook = new JDBCBookDAO(conexao);

			boolean retorno = jdbcBook.delete(id);

			String msg = "";
			if (retorno) {
				msg = "Livro excluído com sucesso!";
			} else {
				msg = "Erro ao excluir livro.";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
	@PUT
	@Path("/edit")
	@Consumes("application/*")
	public Response editBook(String bookParam) {
		try {
			Book book = new Gson().fromJson(bookParam, Book.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCBookDAO jdbcBook = new JDBCBookDAO(conexao);
			boolean retorno = jdbcBook.edit(book);
		
			String msg = "";
			if (retorno){
				msg = "Livro alterado com sucesso!";
			}else{
				msg = "Erro ao alterar livro.";
			}

			conec.fecharConexao();
			return this.buildResponse(msg);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/getBookCode")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBookCode(@QueryParam("code") int code){
		
		try{
			Book book = new Book();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCBookDAO jdbcBook = new JDBCBookDAO(conexao);
			book = jdbcBook.getBook(code);
			conec.fecharConexao();
			return this.buildResponse(book);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/inactivate/{code}")
	@Consumes("application/*")
	public Response inactivate(@PathParam("code") int code) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCBookDAO jdbcBook = new JDBCBookDAO(conexao);
			
			boolean retorno = jdbcBook.inactivate(code);
			
			String msg = "";
			if (retorno){
				msg = "Status alterado com sucesso!";
			}else{
				msg = "Erro ao alterar status da livro.";
			}

			conec.fecharConexao();
			return this.buildResponse(msg);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}
