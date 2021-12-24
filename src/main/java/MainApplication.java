import database.HibernateSessionFactoryUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

@Path("/")
public class MainApplication extends Application {
    @GET
    @Path("/")
    public String hello(){
        HibernateSessionFactoryUtil.getSessionFactory().openSession();
        return "Hello";
    }
}
