package ca.ualberta.team2recipefinder.model;

import java.lang.Exception;

/**
 * The exception to throw when adding an ingredient of which the type already exists in the ingredient list
 * @author cmput-301 team 2
 * @see	ca.ualberta.team2recipefinder.model.Ingredient
 * @see	ca.ualberta.team2recipefinder.IngredientList
 * @version	1.00 07/03/13
 */
public class DuplicateIngredientException extends Exception {
	private String ingredientType;// the type of the ingredient causes the exception
	
	/**
	 * Default constructor. Creates a DuplicateIngredientException with
	 * a blank ingredient type.
	 */
	public DuplicateIngredientException() {
		super();
		ingredientType = "";
	}
	
	/**
	 * Creates a DuplicateIngredientException with
	 * the specified ingredient type.
	 * @param str the desired ingredient type
	 */
	public DuplicateIngredientException(String str) {
		super(str+" already exits.");
		ingredientType=str;
	}
	
	/**
	 * Returns the ingredient type causes this exception
	 * @return ingredient type
	 */
	public String getIngredientType(){
		return ingredientType;
	}
}