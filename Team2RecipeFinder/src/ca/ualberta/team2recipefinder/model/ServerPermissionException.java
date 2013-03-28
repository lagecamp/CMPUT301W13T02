package ca.ualberta.team2recipefinder.model;

import java.lang.Exception;

/**
 * The exception to throw when a user attemps to change a recipe
 * that he/she does not own
 * @author cmput-301 team 2
 * @see	ca.ualberta.team2recipefinder.model.RemoteRecipes
 * @version	1.00 07/03/13
 */
public class ServerPermissionException extends Exception {
	
	/**
	 * Default constructor
	 */
	public ServerPermissionException() {
		super();
	}
	
	/**
	 * Constructor that also receives a message to be passed to the superclass
	 * @param str
	 */
	public ServerPermissionException(String str) {
		super(str);
	}
}