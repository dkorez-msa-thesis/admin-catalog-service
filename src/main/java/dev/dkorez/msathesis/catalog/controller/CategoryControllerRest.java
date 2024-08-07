package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.dto.CategoryDto;
import dev.dkorez.msathesis.catalog.model.CategoryRequestDto;
import dev.dkorez.msathesis.catalog.service.CategoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("admin/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryControllerRest {
    @Inject
    private CategoryService categoryService;

    @GET
    public List<CategoryDto> getAll() {
        return categoryService.getCategories();
    }

    @GET
    @Path("/{id}")
    public CategoryDto getById(@PathParam("id") Long id) {
        return categoryService.getCategory(id);
    }

    @POST
    public Response create(CategoryRequestDto category) {
        categoryService.create(category);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public CategoryDto update(@PathParam("id") Long id, CategoryRequestDto category) {
        return categoryService.update(id, category);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        categoryService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
