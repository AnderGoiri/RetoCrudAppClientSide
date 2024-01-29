/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.Game;
import model.User;
import rest.AdminRestClient;
import rest.UserRestClient;

/**
 *
 * @author Andoni Sanz
 */
public class ESportsManagerImplementation implements ESportsManager{

     //REST users web client
    private UserRestClient webClient;
    private static final Logger LOGGER=Logger.getLogger("ESportsManagerImplementation");
    
    public ESportsManagerImplementation(){
        webClient=new UserRestClient();
    }
    
    @Override
    public void passwordRecovery(String mail) throws BusinessLogicException {
        //String =null;
        User u = new User();
        u.setEmail(mail);
        try{
            LOGGER.info("ESportsManagerImplementation: Creatign new password for user with email: "+ u.getEmail());
            //Ask webClient for all gamesdata.
            webClient.passwordRecovery_XML(u, new GenericType<User>() {});
            
            LOGGER.info("generated password: "+ u.getPassword());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "ESportsManagerImplementation: Exception creating password}",
                    ex.getMessage());
            throw new BusinessLogicException("Error recovering password\n"+ex.getMessage());
        }
    }
    
}
