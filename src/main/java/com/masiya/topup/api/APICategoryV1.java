package com.masiya.topup.api;

import java.util.List;

import javax.inject.Inject;
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

import com.masiya.topup.TopupResponseCollection;
import com.masiya.topup.manager.CategoryManager;
import com.masiya.topup.model.Category;

@Path("/v1/categories")
public class APICategoryV1 {
	
	@Inject
	CategoryManager mngrCategory;

	public APICategoryV1() {
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Category addCategory(Category category) {
		return mngrCategory.add(category);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Category updateCategory(@PathParam("id") Integer id, Category category) {
		category.setCategoryId(id);
		return mngrCategory.update(category);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCategory(@PathParam("id") Integer id) {
		boolean bRes = mngrCategory.delete(id);
		Response resp = Response.status(Response.Status.BAD_REQUEST).build();
		if(bRes) {
			resp = Response.status(Response.Status.NO_CONTENT).build();
		}
		return resp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TopupResponseCollection<Category> getCategories(@QueryParam("search") String search) {
		List<Category> lst = mngrCategory.selectAll(search);
		TopupResponseCollection<Category> trc = new TopupResponseCollection<Category>(lst);
		return trc;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Category getCategoryById(@PathParam("id") Integer id) {
		return mngrCategory.selectById(id);
	}

}
