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
public class TextFormatException extends Exception {

     /**
     * Creates a new instance of <code>MaxCharException</code> without detail
     * message.
     */
    public TextFormatException() {
    }

    /**
     * Constructs an instance of <code>MaxCharException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public TextFormatException(String msg) {
        super(msg);
    }
    
}

