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
import br.com.arcoiris.jdbc.JDBCCommentDAO;
import br.com.arcoiris.modelo.Book;
import br.com.arcoiris.modelo.Category;

import java.util.ArrayList;
import java.util.List;

@Path("category")
public class CategoryRest extends UtilRest{
	
	@POST
	@Path("/insert")
	@Consumes("application/*")
	public Response insert(String category) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();

			JDBCCategoryDAO jdbcCategory = new JDBCCategoryDAO(conexao);
			boolean retorno = jdbcCategory.insert(category);
			String msg = "";

			if (retorno) {
				msg = "Categoria cadastrada com sucesso";
			} else {
				msg = "Erro ao cadastrar categoria";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@GET
	@Path("/getAllCategorys")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCategorys() {

		try {
			List<Category> listCategorys = new ArrayList<Category>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCCategoryDAO jdbcCategory = new JDBCCategoryDAO(conexao);
			listCategorys = jdbcCategory.getCategorys();
			
			conec.fecharConexao();
			String json = new Gson().toJson(listCategorys);
			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
	@DELETE
	@Path("/deleteCategory/{id}")
	@Consumes("application/*")
	public Response deleteCategory(@PathParam("id") int id) {

		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoryDAO jdbcCategory = new JDBCCategoryDAO(conexao);

			boolean retorno = jdbcCategory.delete(id);

			String msg = "";
			if (retorno) {
				msg = "Categoria excluída com sucesso!";
			} else {
				msg = "Erro ao excluir categoria.";
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
	public Response edit(String categoryParam) {
		try {
			Category category = new Gson().fromJson(categoryParam, Category.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCCategoryDAO jdbcCategory = new JDBCCategoryDAO(conexao);
			boolean retorno = jdbcCategory.edit(category);
		
			String msg = "";
			if (retorno){
				msg = "Categoria alterada com sucesso!";
			}else{
				msg = "Erro ao alterar categoria.";
			}

			conec.fecharConexao();
			return this.buildResponse(msg);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/getCategory")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategory(@QueryParam("id") int id){
		
		try{
			Category category = new Category();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCCategoryDAO jdbcCategory = new JDBCCategoryDAO(conexao);
			category = jdbcCategory.getCategory(id);
			conec.fecharConexao();
			return this.buildResponse(category);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/inactivate/{id}")
	@Consumes("application/*")
	public Response inactivate(@PathParam("id") int id) {
		try {
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoryDAO jdbcCategory = new JDBCCategoryDAO(conexao);
			
			boolean retorno = jdbcCategory.inactivate(id);
			
			String msg = "";
			if (retorno){
				msg = "Status alterado com sucesso!";
			}else{
				msg = "Erro ao alterar status da categoria.";
			}

			conec.fecharConexao();
			return this.buildResponse(msg);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}
