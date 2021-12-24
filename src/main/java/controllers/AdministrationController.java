package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.*;
import log.Logged;
import security.JWTService;
import services.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/admin")
@RolesAllowed({"ADMIN"})
public class AdministrationController {

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

    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Context
    HttpHeaders httpHeaders;

    ObjectMapper oM = new ObjectMapper().findAndRegisterModules().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);





    @POST
    @Path("profile/create")
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
                Profile profile = new Profile(firstName, lastName, age, Role.ADMIN, email, phoneNumber, password);
                profileService.createNewProfile(profile, district.getId());
                return Response.status(200).entity(oM.writeValueAsString(profile)).build();
            }
            else {
                return Response.status(201).entity(oM.writeValueAsString("District not found")).build();
            }
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }



    @POST
    @Path("district/create")
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

    @POST
    @Path("info/create")
    public Response saveClient(@FormParam("type") String type,
                               @FormParam("businessName") String businessName,
                               @FormParam("description") String description,
                               @FormParam("phoneNumber") String phoneNumber,
                               @FormParam("district_name") String districtName) {

        try {
            District district = districtService.getDistrictByName(districtName.trim().toLowerCase());
            if(district!=null) {
                BusinessInformation newB = new BusinessInformation(type, businessName, description.trim(), phoneNumber);
                businessService.createNewInfo(newB, district.getId());
                return Response.status(200).entity(oM.writeValueAsString(newB)).build();
            }
            else {
                return Response.status(201).entity(oM.writeValueAsString("District not found")).build();
            }
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("place/create")
    public Response saveVacancy(@FormParam("placeName") String placeName,
                                @FormParam("address") String address,
                                @FormParam("type") String type,
                                @FormParam("rating") double rating,
                                @FormParam("phone") String phone,
                                @FormParam("district_name") String districtName) {

        try {
            District district = districtService.getDistrictByName(districtName.trim().toLowerCase());
            if(district!=null) {
                Place newPlace = new Place(placeName, address,type, rating, phone);
                placeService.createNewPlace(newPlace, district.getId());
                return Response.status(200).entity(oM.writeValueAsString(newPlace)).build();
            }
            else {
                return Response.status(201).entity(oM.writeValueAsString("District not found")).build();
            }
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }




}
