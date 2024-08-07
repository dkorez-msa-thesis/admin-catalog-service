package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.dto.ProductDto;
import dev.dkorez.msathesis.catalog.model.ProductRequestDto;
import dev.dkorez.msathesis.catalog.service.ProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("admin/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductControllerRest {
    @Inject
    private ProductService productService;

    @GET
    public List<ProductDto> getAll() {
        return productService.getProducts();
    }

    @GET
    @Path("/{id}")
    public ProductDto getById(@PathParam("id") Long id) {
        return productService.getProduct(id);
    }

    @POST
    public Response create(ProductRequestDto product) {
        productService.create(product);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public ProductDto update(@PathParam("id") Long id, ProductRequestDto product) {
        return productService.update(id, product);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        productService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
