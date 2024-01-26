/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class MaxCharException extends Exception {

     /**
     * Creates a new instance of <code>MaxCharException</code> without detail
     * message.
     */
    public MaxCharException() {
    }

    /**
     * Constructs an instance of <code>MaxCharException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public MaxCharException(String msg) {
        super(msg);
    }
    
}
