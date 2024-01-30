/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import businessLogic.OrganizerManager;
import businessLogic.OrganizerManagerImplementation;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class OrganizerFactory {

    public static OrganizerManager getOrganizermanager() {
        return new OrganizerManagerImplementation();
    }
}
