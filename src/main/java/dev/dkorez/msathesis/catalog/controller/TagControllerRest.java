package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.dto.TagDto;
import dev.dkorez.msathesis.catalog.model.TagRequestDto;
import dev.dkorez.msathesis.catalog.service.TagService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("admin/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagControllerRest {
    @Inject
    private TagService tagService;

    @GET
    public List<TagDto> getAll() {
        return tagService.getTags();
    }

    @GET
    @Path("/{id}")
    public TagDto getById(@PathParam("id") Long id) {
        return tagService.getTag(id);
    }

    @POST
    public Response create(TagRequestDto tag) {
        tagService.create(tag);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public TagDto update(@PathParam("id") Long id, TagRequestDto tag) {
        return tagService.update(id, tag);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        tagService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
