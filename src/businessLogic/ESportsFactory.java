/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import javax.naming.OperationNotSupportedException;

/**
 *
 * @author Andoni Sanz
 */
public class ESportsFactory {
    /**
     * UsersManager type for client of a RESTful web service. 
     */
    public static final String REST_WEB_ADMIN="REST_WEB_ADMIN";
    /**
     * UsersManager type for a manager producing fake data for test purposes. 
     */
    public static final String REST_WEB_ORGANIZER="REST_WEB_ORGANIZER";
    /**
     * UsersManager type for a manager producing fake data for test purposes. 
     */
    public static final String REST_WEB_PLAYER="REST_WEB_PLAYER";
    
    public static final String REST_WEB_ESPORTS="REST_WEB_ESPORTS";
    /**
     * Factory creation method. It returns different {@link UsersManager} interface implementing 
     * objects depending on the type parameter value.
     * @param type Type of implementation for object being returned.
     * @return An object implementing UsersManager according to type.
     * @throws OperationNotSupportedException If type is not supported.
     */
    public static ESportsManager getManager(String type) throws OperationNotSupportedException{
        //The object to be returned.
        ESportsManager manager=null;
        //Evaluate type parameter.
        switch(type){
            case REST_WEB_ADMIN:
                //If rest web client type is asked for, use UsersManagerImplementation.
                manager = new GameManagerImplementation();
                break;
            case REST_WEB_ORGANIZER:
                //If rest fake data test type is asked for, use UsersManagerTestDataGenerator.
                //manager=new EventManagerImplementation();
                break;
            case REST_WEB_PLAYER:
                //If rest fake data test type is asked for, use UsersManagerTestDataGenerator.
                //manager=new TeamManagerImplementation();
                break;
            case REST_WEB_ESPORTS:
                //If rest fake data test type is asked for, use UsersManagerTestDataGenerator.
                manager = new ESportsManagerImplementation();
                break;               
            default:
                //If type is not one of the types accepted.
                throw new OperationNotSupportedException("Users manager type not supported.");
        }
        return manager;
    }
}
