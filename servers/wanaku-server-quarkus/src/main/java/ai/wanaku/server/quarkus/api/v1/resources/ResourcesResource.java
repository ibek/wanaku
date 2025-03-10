package ai.wanaku.server.quarkus.api.v1.resources;

import ai.wanaku.api.types.ResourceReference;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
@Path("/api/v1/resources")
public class ResourcesResource {
    private static final Logger LOG = Logger.getLogger(ResourcesResource.class);

    @Inject
    ResourcesBean resourcesBean;

    @Path("/expose")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response expose(ResourceReference resource) {
        try {
            resourcesBean.expose(resource);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.errorf(e, "Failed to expose resource %s: %s", resource.getName(), e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to expose resource").build();
        }
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = SchemaType.ARRAY, implementation = ResourceReference.class))),
            @APIResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response list() {
        try {
            List<ResourceReference> list = resourcesBean.list();
            return Response.ok(list).build();
        } catch (Exception e) {
            LOG.errorf(e, "Failed to list resources: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to list resources").build();
        }
    }

    @Path("/remove")
    @PUT
    public Response remove(@QueryParam("resource") String resource) {
        try {
            resourcesBean.remove(resource);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.errorf(e, "Failed to remove resource %s: %s", resource, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to remove tool").build();
        }
    }
}
