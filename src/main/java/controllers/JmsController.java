package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.BusinessInformation;
import entities.District;
import entities.Vacancy;
import mq.MessageQueue;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/mq")
@RolesAllowed({"USER", "ADMIN"})
public class JmsController {
    @EJB
    private MessageQueue mq;

    @POST
    @Path("/addVacancy")
    @Produces(MediaType.APPLICATION_JSON)
    public void addVacancy(@FormParam("vacancyName") String vacancyName,
                                @FormParam("description") String description,
                                @FormParam("priceFrom") int priceFrom,
                                @FormParam("priceTo") int priceTo,
                                @FormParam("companyName") String companyName) throws JsonProcessingException, JMSException {

            Vacancy vacancy = new Vacancy(vacancyName, description, priceFrom, priceTo, companyName);
            mq.sendMessage(new ObjectMapper().writeValueAsString(vacancy));
    }

    @GET
    @Path("/getVacancy")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOneVacancy() throws JMSException {
        return mq.receiveMessage();
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAll() throws JMSException {
        return mq.receiveAll();
    }
}
