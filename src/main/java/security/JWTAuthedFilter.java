package security;



import entities.Profile;
import entities.Role;
import services.ProfileService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//@Provider
public class JWTAuthedFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    @Inject
    private ProfileService profileService;

    @Inject
    private JWTService jwtService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String token = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        Method method = resourceInfo.getResourceMethod();
        if (!method.isAnnotationPresent(PermitAll.class)) {
            if (method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("Access blocked for all users !!").build());
                return;
            }
            String payload = "";
            String email = "";
            String password = "";
            try {
            token = token.split(" ")[1];
            payload = jwtService.valid(token);

            String[] parts = payload.split(",");
            email = parts[0].split(":")[1].substring(1, parts[0].split(":")[1].length()-1);
            password = parts[1].split(":")[1].substring(1,parts[0].split(":")[1].length()-1);

            } catch (Exception e) {
                requestContext
                        .abortWith(Response.status(Response.Status.UNAUTHORIZED)
                                .build());
            }
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

                if (!isUserAllowed(email, password, rolesSet)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("You cannot access this resource").build());
                    return;
                }
            }

        }
    }


    private boolean isUserAllowed( String email,  String password,  Set<String> rolesSet) {
        boolean isAllowed = false;

        Profile profile = profileService.getProfileByEmailAndPassword(email, password);

        if (!(profile == null)) {
            Role role = profile.getRole();

            if (rolesSet.contains(role.toString())) {
                isAllowed = true;
            }
        }
        return isAllowed;
    }

}
