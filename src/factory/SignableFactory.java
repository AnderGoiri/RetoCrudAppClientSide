/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import businessLogic.SignableImplementation;

/**
 *
 * @author Andoni Sanz
 */
public class SignableFactory {

    public static Signable getSignable() {
        return new SignableImplementation();
    }
}
