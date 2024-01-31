/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Andoni Sanz
 */
public class EmptyGameAlreadyAddedException extends Exception{
     
    public EmptyGameAlreadyAddedException() {
		super();
	}
	public EmptyGameAlreadyAddedException(String msg)
	{
		super(msg);
	}
}

