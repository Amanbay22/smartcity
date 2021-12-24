package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.District;
import entities.Jobseeker;
import entities.Place;
import entities.Vacancy;
import log.Logged;
import services.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/info")
@RolesAllowed({"USER", "ADMIN"})
@Logged
public class ProfileController {

    @EJB
    private ProfileService profileService;
    @EJB
    private PlaceService placeService;
    @EJB
    private DistrictService districtService;
    @EJB
    private VacancyService vacancyService;
    @EJB
    private BusinessInformationService businessService;
    @EJB
    private JobService jobService;

    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Context
    HttpHeaders httpHeaders;

    ObjectMapper oM = new ObjectMapper().findAndRegisterModules().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);



    @GET
    @Path("district/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDistrictById(@PathParam("id") Long id)
    {
        try {
            return Response.status(200).entity(oM.writeValueAsString(districtService.getDistrictById(id))).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }


    @GET
    @Path("profile/{id}")
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

    @GET
    @Path("place/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaceByName(@PathParam("name") String name) {
        try {
            return  Response
                    .status(Response.Status.OK)
                    .entity(oM.writeValueAsString(placeService.getPlaceByName(name)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            String message = "Place not found";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(message)
                    .build();
        }
    }

    @GET
    @Path("info/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfileById(@PathParam("name") String name) {
        try {
            return  Response
                    .status(Response.Status.OK)
                    .entity(oM.writeValueAsString(businessService.getInfoByName(name)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            String message = "Info not found";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(message)
                    .build();
        }
    }

    @GET
    @Path("vacancy/byDistrict/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response vacancyByDistrict(@PathParam("name") String name) {
        try {
            return  Response
                    .status(Response.Status.OK)
                    .entity(oM.writeValueAsString(vacancyService.getVacancyByCompany(name)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            String message = "Info not found";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(message)
                    .build();
        }
    }


    @POST
    @Path("vacancy/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveVacancy(@FormParam("vacancyName") String vacancyName,
                                @FormParam("description") String description,
                                @FormParam("priceFrom") int priceFrom,
                                @FormParam("priceTo") int priceTo,
                                @FormParam("companyName") String companyName) {

        try {
            Vacancy vacancy = new Vacancy(vacancyName, description, priceFrom, priceTo, companyName);
            vacancyService.createNewVacancy(vacancy);
            return Response.status(200).entity(oM.writeValueAsString(vacancy)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("jobseeker/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveJob(@FormParam("degree") String degree,
                                @FormParam("about") String about,
                                @FormParam("yearJob") int yearJob) {

        try {
            Jobseeker jobseeker = new Jobseeker(degree, about, yearJob);
            jobService.createNewJobseeker(jobseeker);
            return Response.status(200).entity(oM.writeValueAsString(jobseeker)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
