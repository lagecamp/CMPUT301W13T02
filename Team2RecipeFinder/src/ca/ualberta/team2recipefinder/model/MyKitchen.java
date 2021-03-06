package ca.ualberta.team2recipefinder.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import ca.ualberta.team2recipefinder.controller.RecipeFinderApplication;
import ca.ualberta.team2recipefinder.views.View;

/**
 * MyKitchen manipulates a list of ingredients. It stores the list to the file <code>filename</code>.
 * It can add/remove/replace ingredients to/from the list. It can sort the list by the <code>type</code> of the <code>Ingredient</code>
 * And it also provides the functionality of searching ingredients by keywords
 * @author cmput-301 team 2
 * @version 1.0 07/03/13
 * @see	ca.ualberta.team2recipefinder.model.Ingredient
 * @see	ca.ualberta.team2recipefinder.model.Recipe
 */
public class MyKitchen extends Model<View> {
	private String path;// the path to the file
	
	ArrayList<Ingredient> ingredientList;// the list of the ingredients
	
	public MyKitchen(String path) {
		this.path = path;		
		ingredientList = load();
	}

	/**
	 * Loads the ingredient list from file
	 * @return the ingredient list loaded
	 */
	private ArrayList<Ingredient> load() {  
		ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();
		   
		try {  
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));  
			ingredientList = (ArrayList<Ingredient>) in.readObject();
			in.close();
		} 
		catch (IOException e) {  
			e.printStackTrace();  
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();  
		}
			
		return ingredientList;
	}
	   
	/**
	 * Adds a new ingredient to the list
	 * @param the ingredient to add
	 * @throws DuplicateIngredientException 
	 */
	public void add(Ingredient ingredient) throws DuplicateIngredientException {		
		for(int n = 0; n < ingredientList.size(); n++) {
			if(ingredientList.get(n).getType().equalsIgnoreCase(ingredient.getType())) {
				throw new DuplicateIngredientException(ingredient.getType());
			}
		}
		ingredientList.add(ingredient);
		
		sort();
		write(ingredientList);
		notifyViews();
	}
	
	/**
	 * Removes an ingredient from the list
	 * @param ingredient to remove
	 */
	public void remove(Ingredient ingredient) {
		ingredientList.remove(ingredient);
		write(ingredientList);
		notifyViews();
	}
	
	/**
	 * Writes the ingredientList back to file
	 * @param ingredientList to write
	 */
	private void write(ArrayList<Ingredient> ingredientList) {
		try {  
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(ingredientList);
			out.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  	
	}
	
	/**
	 * Sort the ingredient List by type in dictionary order
	 * @param ingredientList to sort
	 */
	private void sort() {
		Collections.sort(ingredientList);
	}
	
	/**
	 * Get the ingredient object with the wanted type
	 * @param type of the ingredient looking for
	 * @return	the ingredient with the matched type or null if no such a ingredient is found
	 */
	public Ingredient getIngredient(String type) {		
		for (Ingredient ingredient : ingredientList) {
			if (ingredient.getType().equals(type)) {
				return ingredient;
			}
		}
		
		return null;
	}
	
	/**
	 * Replaces one old ingredient with a new one
	 * @param oldIngredient
	 * @param newIngredient
	 */
	public void replaceIngredient(Ingredient oldIngredient, Ingredient newIngredient) {
		// only replace if they represent the same ingredient
		if (oldIngredient.getType().equals(newIngredient.getType())) {
			ingredientList.remove(oldIngredient);
			ingredientList.add(newIngredient);
			sort();
			write(ingredientList);
			notifyViews();
		}
		else {
			// error case
		}
	}
	
	/**
	 * Searches for the ingredients contains some keywords and returns a list of all matched ingredients
	 * @param keywords 
	 * @return
	 */
	public ArrayList<Ingredient> searchIngredient(String[] keywords) {
		ArrayList<Ingredient> matchingIngredients = new ArrayList<Ingredient>();
		   
		
		for(int n = 0; n < keywords.length; n++){
			for(int i = 0; i < ingredientList.size(); i++){
				if(ingredientList.get(i).getType().toLowerCase(Locale.ENGLISH).contains(keywords[n].toLowerCase(Locale.ENGLISH)) || ingredientList.get(i).getUnit().toLowerCase(Locale.ENGLISH).contains(keywords[n].toLowerCase(Locale.ENGLISH))){
					matchingIngredients.add(ingredientList.get(i));
				}
			}
		}
		   	   
		return matchingIngredients;
		   
	}
	
	
	/**
	 * Returns a list with all the ingredients in MyKitchen
	 * @param keywords 
	 * @return All the ingredients in MyKitchen
	 */
	public List<Ingredient> getIngredients() {
		return ingredientList;
	}
}
