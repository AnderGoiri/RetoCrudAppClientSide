/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Andoni
 */
public class NameAlreadyExistsException extends Exception {

    /**
     * Creates a new instance of <code>MaxCharException</code> without detail
     * message.
     */
    public NameAlreadyExistsException() {
    }

    /**
     * Constructs an instance of <code>MaxCharException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NameAlreadyExistsException(String msg) {
        super(msg);
    }
    
}

