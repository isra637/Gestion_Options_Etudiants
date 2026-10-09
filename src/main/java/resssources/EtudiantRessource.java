package resssources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/etudiants")
public class EtudiantRessource {

    private EtudiantBusiness etudiantBusiness = new EtudiantBusiness();
    private OptionBusiness optionBusiness = new OptionBusiness();

    // 1) Création d'un nouvel étudiant
    // POST /etudiants → 201 Created
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        boolean added = etudiantBusiness.addEtudiant(etudiant);
        if (added) {
            return Response.status(Response.Status.CREATED)
                    .entity(etudiant)
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // 2) Récupération de la liste de tous les étudiants
    // GET /etudiants → 200 OK
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Etudiant> getAll() {
        return etudiantBusiness.getAllEtudiants();
    }

    // 3) Récupération d'un étudiant par identifiant
    // GET /etudiants/{identifiant} → 200 OK
    @GET
    @Path("/{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = etudiantBusiness.getEtudiantByIdentifiant(identifiant);
        if (etudiant != null) {
            return Response.ok(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 6) Récupérer tous les étudiants d'une UE (option) donnée
    // GET /etudiants/option?codeOption=1 → 200 OK (XML) | 404 Not Found
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Etudiant> etudiants = etudiantBusiness.getEtudiantsByOption(option);
        return Response.ok(new EtudiantList(etudiants)).build();
    }

    // 5) Mise à jour d'un étudiant
    // PUT /etudiants/{identifiant} → 200 OK
    @PUT
    @Path("/{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant etudiant) {
        boolean updated = etudiantBusiness.updateEtudiant(identifiant, etudiant);
        if (updated) {
            return Response.ok(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 6) Suppression d'un étudiant
    // DELETE /etudiants/{identifiant} → 200 OK
    @DELETE
    @Path("/{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        boolean deleted = etudiantBusiness.deleteEtudiant(identifiant);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
