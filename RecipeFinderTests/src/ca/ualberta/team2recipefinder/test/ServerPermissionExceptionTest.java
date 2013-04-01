package ca.ualberta.team2recipefinder.test;

import junit.framework.TestCase;
import ca.ualberta.team2recipefinder.model.DuplicateIngredientException;
import ca.ualberta.team2recipefinder.model.ServerPermissionException;

public class ServerPermissionExceptionTest extends TestCase {

	private void throwAnException(String str) throws ServerPermissionException {
		throw new ServerPermissionException(str);
	}
	
	private void throwAnException() throws ServerPermissionException {
		throw new ServerPermissionException();
	}	
	
	public void testServerPermissionException() {
		boolean caughtException = false;
		
		try {
			throwAnException();
		}
		catch (ServerPermissionException e){
			caughtException = true;
			}
	
		// ensure a ServerPermissionException was caught
		assertTrue(caughtException);
		
	}

	public void testServerPermissionExceptionString() throws ServerPermissionException {
		boolean caughtException = false;
		
		try {
			throwAnException("test");
		}
		catch (ServerPermissionException e){
			caughtException = true;
			}
	
		// ensure a ServerPermissionException was caught
		assertTrue(caughtException);
		
	}

}
