package log;

import cache.CustomCache;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Logged
@Provider
public class RequestLoggingFilter implements ContainerRequestFilter {

    static Logger log = Logger.getLogger(RequestLoggingFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String data = "\n";
        data += "REQUEST_METHOD: " + requestContext.getMethod() + "\n";
        data += "URI_INFO: " + requestContext.getUriInfo() + "\n";
        data += "HEADERS: " + requestContext.getHeaders() + "\n";
        data += "MEDIA_TYPE: " + requestContext.getMediaType() + "\n";
        data += "ENTITY_STREAM: " + requestContext.getEntityStream();
        CustomCache.setCache(data);
    }
}