package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.dto.SpecificationDto;
import dev.dkorez.msathesis.catalog.model.SpecsRequestDto;
import dev.dkorez.msathesis.catalog.service.SpecificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("admin/specification")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpecificationControllerRest {
    @Inject
    private SpecificationService specificationService;

    @GET
    public List<SpecificationDto> getAll() {
        return specificationService.getSpecifications();
    }

    @GET
    @Path("/{id}")
    public SpecificationDto getById(@PathParam("id") Long id) {
        return specificationService.getSpecification(id);
    }

    @POST
    public Response create(SpecsRequestDto spec) {
        specificationService.create(spec);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public SpecificationDto update(@PathParam("id") Long id, SpecsRequestDto spec) {
        return specificationService.update(id, spec);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        specificationService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
