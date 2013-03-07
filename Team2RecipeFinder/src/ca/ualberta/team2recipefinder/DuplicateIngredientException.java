package ca.ualberta.team2recipefinder;

import java.lang.Exception;

/**
 * The exception to throw when adding an ingredient of which the type already exists in the ingredient list
 * @author lxian
 * @see	ca.ualberta.team2recipefinder.Ingredient
 * @see	ca.ualberta.team2recipefinder.IngredientList
 * @version	1.00 07/03/13
 */
public class DuplicateIngredientException extends Exception{
	private String ingredientType;// the type of the ingredient causes the exception
	public DuplicateIngredientException(){
		super();
		ingredientType="";
	}
	public DuplicateIngredientException(String str){
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