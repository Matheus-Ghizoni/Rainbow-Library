package br.com.arcoiris.rest;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import br.com.arcoiris.jdbc.JDBCCommentDAO;
import br.com.arcoiris.jdbc.JDBCUserDAO;
import br.com.arcoiris.modelo.Book;
import br.com.arcoiris.modelo.Comment;
import br.com.arcoiris.modelo.User;

import java.util.ArrayList;
import java.util.List;

@Path("comment")
public class CommentRest extends UtilRest {
	
	@POST
	@Path("/insert")
	@Consumes("application/*")
	public Response insert(String commentParam) {
		try {
			Comment comment = new Gson().fromJson(commentParam, Comment.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();

			JDBCCommentDAO jdbcComment = new JDBCCommentDAO(conexao);
			boolean retorno = jdbcComment.insert(comment);
			String msg = "";

			if (retorno) {
				msg = "Comentário salvo com sucesso";
			} else {
				msg = "Erro ao salvar comentário";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@GET
	@Path("/getAllComments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllComments() {

		try {
			List<Comment> listComments = new ArrayList<Comment>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();

			JDBCCommentDAO jdbcComment = new JDBCCommentDAO(conexao);
			listComments = jdbcComment.getComments();

			conec.fecharConexao();
			String json = new Gson().toJson(listComments);
			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

	@DELETE
	@Path("/deleteComment/{id}")
	@Consumes("application/*")
	public Response deleteComment(@PathParam("id") int id) {

		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCommentDAO jdbcComment = new JDBCCommentDAO(conexao);

			boolean retorno = jdbcComment.delete(id);

			String msg = "";
			if (retorno) {
				msg = "Comentário excluído com sucesso!";
			} else {
				msg = "Erro ao excluir comentário.";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
}