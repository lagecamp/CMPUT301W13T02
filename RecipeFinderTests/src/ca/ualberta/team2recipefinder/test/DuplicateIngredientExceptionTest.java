package ca.ualberta.team2recipefinder.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.ualberta.team2recipefinder.model.DuplicateIngredientException;

public class DuplicateIngredientExceptionTest {

	public void throwAnException(String msg) throws DuplicateIngredientException {
		throw new DuplicateIngredientException(msg);
	}
	
	
	@Test
	public void testConstructor() {
		boolean caughtException = false;
		
		try {
			throwAnException("test");
		}
		catch (DuplicateIngredientException e){
			caughtException = true;
			
			// ensure the Exception has ingredient type "test"
			assertEquals("test", e.getIngredientType());
		}
	

		// ensure a DuplicateIngredientException was caught
		assertTrue(caughtException);
		
	}
	
}
