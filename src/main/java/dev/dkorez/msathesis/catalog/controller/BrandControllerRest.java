package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.dto.BrandDto;
import dev.dkorez.msathesis.catalog.model.BrandRequestDto;
import dev.dkorez.msathesis.catalog.service.BrandService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("admin/brands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BrandControllerRest {
    @Inject
    private BrandService brandService;

    @GET
    public List<BrandDto> getAll() {
        return brandService.getBrands();
    }

    @GET
    @Path("/{id}")
    public BrandDto getById(@PathParam("id") Long id) {
        return brandService.getBrand(id);
    }

    @POST
    public Response create(BrandRequestDto brand) {
        brandService.create(brand);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public BrandDto update(@PathParam("id") Long id, BrandRequestDto brand) {
        return brandService.update(id, brand);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        brandService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
