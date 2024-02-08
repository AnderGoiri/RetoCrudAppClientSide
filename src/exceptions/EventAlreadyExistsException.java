/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class EventAlreadyExistsException extends Exception {

    /**
     * Creates a new instance of <code>EventAlreadyExists</code> without detail
     * message.
     */
    public EventAlreadyExistsException() {
    }

    /**
     * Constructs an instance of <code>EventAlreadyExists</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EventAlreadyExistsException(String msg) {
        super(msg);
    }
}
