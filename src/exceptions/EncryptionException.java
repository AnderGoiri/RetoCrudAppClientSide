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
public class EncryptionException extends Exception {

    /**
     * Creates a new instance of <code>EncryptionException</code> without detail
     * message.
     */
    public EncryptionException() {
    }

    /**
     * Constructs an instance of <code>EncryptionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EncryptionException(String msg) {
        super(msg);
    }
}
