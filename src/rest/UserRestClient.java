/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Andoni Sanz
 */
public class UserRestClient {
    
    private final WebTarget webTarget;
    private final Client client;
    //Get URI from properties' values file.
    private static final String BASE_URI = 
            ResourceBundle.getBundle("config.parameters")
                          .getString("RESTful.baseURI");

    /**
     * Construct a UserRESTClient. It creates a RESTful web client and establishes
     * the path of the WebTarget object associated to the client.
     */
    public UserRestClient() {
        //Create RESTful client
        client = javax.ws.rs.client.ClientBuilder.newClient();
        //Establish the path of the WebTarget object associated to the client
        webTarget = client.target(BASE_URI).path("entity.user");
    }
    /**
     * Get a user's entity XML representation from the user RESTful web service and 
     * return it as a generic type object.
     * @param responseType The Class object of the returning instance. 
     * @param id The id of the instance in the server side. 
     * @return The object containing the data.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        //Set the path for the request.
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        //Make request and return data from the response
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * Get a list of user's entities XML representation from the user RESTful web service and 
     * return it as a generic type object.
     * @param responseType The Class object of the returning instance.
     * @return A generic type, normally a list, containing the data.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        //Make request and return data from the response
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
}
