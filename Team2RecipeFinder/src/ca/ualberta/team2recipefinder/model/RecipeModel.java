/*
 * @RecipeModel.java        1.0 13/03/01
 *
 * The following source is protected by a GNU Public License
 */

package ca.ualberta.team2recipefinder.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ca.ualberta.team2recipefinder.views.View;

/**
 * RecipeModel Class:  It's main duty is to manage recipes.  It adds, removes, searches, and sorts recipes.
 * 
 * @author	CMPUT 301 Team 2
 *
 */
public class RecipeModel extends Model<View>
{
	private String path;
	private ArrayList<Recipe> recipes; 

	/**
	 * Constructor Method
	 * Creates a RecipeModel Object and loads the recipes from the specified file
	 */
	public RecipeModel(String path) {
		this.path = path;		
		this.recipes = load();
	}	


	/**
	 * Add a new Recipe to the Recipe Array List and then write it to the phone's database
	 * 
	 * @param  recipe A recipe that the chef wants to add to his phone
	 * @see    Recipe
	 */
	public void add(Recipe recipe) {
		recipes.add(recipe);
		sortRecipes();
		writeFile(recipes);
		notifyViews();
	}

	/**
	 * Remove the specified recipe from the phone's database
	 * 
	 * @param  recipe A recipe that the chef wants to remove from his phone
	 * @see    Recipe
	 */
	public void remove(Recipe recipe){
		//Searches for recipes which have equivalent attributes (name, author, procedure) as the specified recipe
		recipes.remove(recipe);
		/* for (int i = 0; i < recipes.size(); i++) {			   
			if (recipe.getRecipeID() == recipes.get(i).getRecipeID()) {
				recipes.remove(i);
			    break;
		    }		   
		} */
		writeFile(recipes);
		notifyViews();
	}


	/**
	 *  Write an arraylist of recipes to the phone's database
	 * @param recipes
	 */
	private void writeFile(ArrayList<Recipe> recipes) {  
		try {  
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(recipes);
			out.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	} 

	/**
	 *  Load an array of recipes from the phone's database 
	 * 
	 * @return Return an ArrayList of Recipes from the phone's database
	 */
	private ArrayList<Recipe> load() {  
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();

		try {  
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));  
			recipes = (ArrayList<Recipe>) in.readObject();
			in.close();
		} catch (IOException e) {  
			e.printStackTrace();  
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();  
		}			
		return recipes;
	}

	/**
	 * Searches the phone's recipes for the specific keyword.  It looks at the recipe's name, procedure, author, and ingredients
	 * @param keywords An array of strings filled with keywords
	 * @return Returns an ArrayList of type Recipe that match the keywords
	 */
	public ArrayList<Recipe> searchRecipe(String[] keywords) {
		ArrayList<Recipe> matchingrecipes = new ArrayList<Recipe>();		   

		for (int k = 0; k<keywords.length; k++){							//Searches the Arrraylist and looks for any recipe name, author, procedure which contains the keywords
			for (int i = 0; i<recipes.size(); i++) {
				if ((recipes.get(i).getName()
						.toLowerCase(Locale.ENGLISH)
						.contains(keywords[k]
								.toLowerCase(Locale.ENGLISH)) 
								|| recipes.get(i).getAuthor()
								.toLowerCase(Locale.ENGLISH)
								.contains(keywords[k]
										.toLowerCase(Locale.ENGLISH)) 
										|| recipes.get(i).getProcedure().
										toLowerCase(Locale.ENGLISH).contains(keywords[k])) 
										&& !matchingrecipes.contains(recipes.get(i))) {
					matchingrecipes.add(recipes.get(i));						
				}
				else {		   				
					for (int n = 0; n<recipes.get(i).getIngredients().size(); n++) {		//Searches the Arraylist and looks for any ingredient that contains the keyword
						if ((recipes.get(i)
								.getIngredients().get(n)
								.getType().toLowerCase(Locale.ENGLISH)
								.contains(keywords[k].toLowerCase(Locale.ENGLISH)))  
								&& !matchingrecipes.contains(recipes.get(i))) {
							matchingrecipes.add(recipes.get(i));
						}
					}
				}
			}
		}		   	   
		return matchingrecipes;		   
	}


	/**
	 * Searches the database for recipes in which the user has every ingredient
	 * @param keywords An array of strings filled with keywords
	 * @param kitchenIngredients Ingredients that you have in your kitchen
	 * @return Returns an ArrayList of type Recipe that match the keywords and ingredients from My Kitchen
	 */
	public ArrayList<Recipe> searchWithIngredient(String[] keywords, List<Ingredient> kitchenIngredients) {		   
		ArrayList<Recipe> matchingRecipes = new ArrayList<Recipe>();			   
		matchingRecipes = searchRecipe(keywords);				//Call the local search method to find recipes that match the keywords
		ArrayList<Recipe> matchingIngredientRecipes = new ArrayList<Recipe>();   

		for (int i = 0; i<matchingRecipes.size(); i++) {
			if(kitchenHasAllIngredients(matchingRecipes.get(i), kitchenIngredients)) {
				matchingIngredientRecipes.add(matchingRecipes.get(i));
			}
		}		   
		return matchingIngredientRecipes;		   
	} 

	private boolean kitchenHasAllIngredients(Recipe recipe, List<Ingredient> kitchenIngredients) {
		for (int i = 0; i<recipe.getIngredients().size(); i++) {
			if(!kitchenHasIngredient(recipe.getIngredients().get(i), kitchenIngredients)) {
				return false;
			}
		}
		return true;
	}

	private boolean kitchenHasIngredient(Ingredient recipeIngredient, List<Ingredient> kitchenIngredients) {
		for (int i = 0; i<kitchenIngredients.size(); i++) {
			if (kitchenIngredients.get(i).getType().equalsIgnoreCase(recipeIngredient.getType())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sorts the recipe in alphabetical order by the recipe's name	    * 
	 */
	private void sortRecipes(){
		Recipe temp;

		for (int n = recipes.size()-1; n >= 1; n--) {								//Sorts all recipes using bubble sort
			for (int i = 0; i<n; i++) {
				if (recipes.get(i).getName().compareToIgnoreCase
						(recipes.get(i+1).getName()) > 0) {
					temp = recipes.get(i);
					recipes.set(i, recipes.get(i+1));
					recipes.set(i+1, temp);
				}				   
			}
		}
	}


	/**
	 * Returns a single recipe
	 * @param index
	 * @return
	 */
	public Recipe getRecipe(int index) {
		return this.recipes.get(index);
	}


	/**
	 * Returns all recipes
	 * @return Returns a List of type Recipes
	 */
	public List<Recipe> getAllRecipes() {
		return this.recipes;
	}

	/**
	 * Finds and returns the recipe specified by the ID
	 * @param id Recipe's ID
	 * @return Returns a Recipe object specified by the recipe ID
	 */
	public Recipe getRecipeById(long id) {
		Recipe r = null;

		for (int i = 0; i < recipes.size(); i++) {			   
			if (id == recipes.get(i).getRecipeID()) {
				r = recipes.get(i);
			}
		}		   
		return r;
	}

	/**
	 * Finds the next recipe in the list
	 * @param id Recipe's ID that is used to find the next recipe in the list
	 * @return A Recipe that is the next recipe on the list
	 */
	public Recipe getNextRecipeById(long id){
		Recipe r = new Recipe();

		for (int i = 0; i < recipes.size(); i++) {			   
			if (id == recipes.get(i).getRecipeID()) {				  
				if (i == recipes.size()-1){					//loops to beginning of list if they click 'rightButton' on the last item in a list
					r = recipes.get(0);
				}
				else {
					r = recipes.get(i+1);
				}
			}
		}		   
		return r;
	}

	/**
	 * Finds the previous recipe in the list
	 * @param id Recipe's ID that is used to find the previous recipe in the list
	 * @return A Recipe that is the previous recipe on the list
	 */
	public Recipe getPreviousRecipeById(long id){
		Recipe r = new Recipe();

		for (int i = 0; i < recipes.size(); i++) {			   
			if (id == recipes.get(i).getRecipeID()) {				  
				if (i == 0){									//loops to beginning of list if they click 'rightButton' on the last item in a list
					r = recipes.get(recipes.size()-1);
				}
				else {
					r = recipes.get(i-1);
				}
			}
		}		   
		return r;
	}	   

	/**
	 * Replaces the recipe specified by the ID with a new recipe
	 * @param r A recipe that will replace the old recipe
	 * @param id The ID for the old recipe that will be replaced
	 */
	public void replaceRecipe(Recipe r, long id) {
		Recipe old = getRecipeById(id);

		old.setName(r.getName());
		old.setProcedure(r.getProcedure());
		sortRecipes();
		writeFile(recipes);
		notifyViews();
	}

	/**
	 * Returns a recipe using its serverId
	 * @param serverId
	 * @return
	 */
	public Recipe getRecipeByServerId(String serverId) {
		Recipe r = null;

		for (int i = 0; i < recipes.size(); i++) {			   
			if (serverId.equals(recipes.get(i).getServerId())) {
				r = recipes.get(i);
			}
		}		   
		return r;
	}
	
	/**
	 * Removes all the recipes
	 */
	public void removeAll() {
		this.getAllRecipes().removeAll(this.getAllRecipes());
	}
}
