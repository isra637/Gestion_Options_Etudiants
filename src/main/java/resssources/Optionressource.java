package resssources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
public class Optionressource {

    private OptionBusiness optionBusiness = new OptionBusiness();

    // 1) Création d'une nouvelle option
    // POST /options → 201 Created
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {
        boolean added = optionBusiness.addOption(option);
        if (added) {
            return Response.status(Response.Status.CREATED)
                    .entity(option)
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // 2) Récupération de la liste de toutes les options
    // GET /options → 200 OK
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Option> getList(@QueryParam("domaine") String domaine) {
        // 3) Récupération par domaine si paramètre présent
        // GET /options?domaine=Mathématiques → 200 OK
        if (domaine != null && !domaine.isEmpty()) {
            return optionBusiness.getOptionsByDomaine(domaine);
        }
        return optionBusiness.getListeOptions();
    }

    // Récupération d'une option par son code
    // GET /options/{code} → 200 OK
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("code") int code) {
        Option option = optionBusiness.getOptionByCode(code);
        if (option != null) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 6) Récupération d'une option ayant un code donné (alias /UE/{code})
    // GET /options/UE/{code} → 200 OK | 404 Not Found
    @GET
    @Path("/UE/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUECode(@PathParam("code") int code) {
        Option option = optionBusiness.getOptionByCode(code);
        if (option != null) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // Mise à jour d'une option
    // PUT /options/{code} → 200 OK
    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option option) {
        boolean updated = optionBusiness.updateOption(code, option);
        if (updated) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // Suppression d'une option
    // DELETE /options/{code} → 200 OK
    @DELETE
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOption(@PathParam("code") int code) {
        boolean deleted = optionBusiness.deleteOption(code);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
