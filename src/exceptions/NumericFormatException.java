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
public class NumericFormatException extends Exception {

    /**
     * Creates a new instance of <code>NumericFormatException</code> without
     * detail message.
     */
    public NumericFormatException() {
    }

    /**
     * Constructs an instance of <code>NumericFormatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NumericFormatException(String msg) {
        super(msg);
    }
}
