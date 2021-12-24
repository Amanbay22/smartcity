package controllers;

import entities.Profile;
import security.JWTService;
import services.ProfileService;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/auth")
public class AuthController {

    @Inject
    private JWTService jwtService;
    @EJB
    private ProfileService profileService;

    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Context
    HttpHeaders httpHeaders;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @PermitAll
    @Path("/getJWT")
    public Response JWTAuthorization(@FormParam("email") String email,
                                     @FormParam("password") String password) {
        Profile profile = profileService.getProfileByEmailAndPassword(email, password);
        if(profile == null) {
            return Response.status(500)
                    .entity("Incorected data")
                    .build();
        }
        else{
            String JWT_User = jwtService.generateJWTToken(profile);
            return Response.ok().entity(JWT_User).build();
        }

    }

}
