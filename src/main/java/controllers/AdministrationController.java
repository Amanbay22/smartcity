package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.District;
import entities.Profile;
import entities.Role;
import log.Logged;
import security.JWTService;
import services.DistrictService;
import services.ProfileService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/admin")
public class AdministrationController {
    @Inject
    private JWTService jwtService;
    @EJB
    private ProfileService profileService;
    @EJB
    private DistrictService districtService;

    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Context
    HttpHeaders httpHeaders;

    ObjectMapper oM = new ObjectMapper().findAndRegisterModules().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

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


    @GET
    @Path("profile/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfileById(@PathParam("id") Long id) {
        try {
            return  Response
                    .status(Response.Status.OK)
                    .entity(oM.writeValueAsString(profileService.getProfileById(id)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            String message = "User not found";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(message)
                    .build();
        }
    }

    @POST
    @Path("profile/create")
    @PermitAll
    public Response saveClient(@FormParam("first_name") String firstName,
                               @FormParam("last_name") String lastName,
                               @FormParam("phone") String phoneNumber,
                               @FormParam("email") String email,
                               @FormParam("password") String password,
                               @FormParam("district_name") String districtName,
                               @FormParam("age") int age) {

        try {
            District district = districtService.getDistrictByName(districtName.trim().toLowerCase());
            if(district!=null) {
                Profile profile = new Profile(firstName, lastName, age, Role.USER, email, phoneNumber, password, district);
                profileService.createNewProfile(profile);
                return Response.status(200).entity(oM.writeValueAsString(profile)).build();
            }
            else {
                return Response.status(201).entity(oM.writeValueAsString("District not found")).build();
            }
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @RolesAllowed({"USER"})
    @Path("district/{id}")
    @Logged
    public Response getDistrictById(@PathParam("id") Long id)
    {
        try {
            return Response.status(200).entity(oM.writeValueAsString(districtService.getDistrictById(id))).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("district/create")
    @PermitAll
    public Response createDistrict(@FormParam("name") String name,
                               @FormParam("population") int population) {

        try {
            District district = new District(name, population);
            districtService.createNewDistrict(district);
            return Response.status(200).entity(oM.writeValueAsString(districtService)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
