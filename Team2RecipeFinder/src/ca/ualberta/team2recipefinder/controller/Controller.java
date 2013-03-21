package ca.ualberta.team2recipefinder.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import ca.ualberta.team2recipefinder.model.DuplicateIngredientException;
import ca.ualberta.team2recipefinder.model.Ingredient;
import ca.ualberta.team2recipefinder.model.MyKitchen;
import ca.ualberta.team2recipefinder.model.Recipe;
import ca.ualberta.team2recipefinder.model.RecipeModel;
import ca.ualberta.team2recipefinder.model.RemoteRecipes;
import ca.ualberta.team2recipefinder.model.SearchResult;
import ca.ualberta.team2recipefinder.model.ServerPermissionException;
/**
 * Controller provides an interface for the Views and Model to communicate for one another. Uses two
 * singleton objects, one representing the applications model for recipes and the other representing
 * the applications model for ingredients.
 * @author cmput-301 team 2
 */
public class Controller {
	
	RecipeModel model;
	MyKitchen myKitchen;
	RemoteRecipes server;
	
	/**
	 * Constructor for controller. The models for ingredients and recipes must be
	 * explicitly provided.
	 * @param model the RecipeModel object
	 * @param myKitchen the MyKitchen object
	 * @param server the RemoteRecipes object
	 */
	public Controller(RecipeModel model, MyKitchen myKitchen, RemoteRecipes server) {
		this.model = model;
		this.myKitchen = myKitchen;
		this.server = server;
	}
	
	/**
	 * Searches the RecipeModel for recipes containing the given keywords. Can be instructed
	 * to search locally, the internet, or both.
	 * @param keywords to be search for
	 * @param searchLocally set to true if the search is to be conducted locally
	 * @param searchFromWeb set to true if the search is to be conducted on the web
	 * @return a list of recipes that resulted from the search
	 */
	public List<SearchResult> search(String[] keywords, boolean searchLocally, boolean searchFromWeb) {
		ArrayList<Recipe> results = new ArrayList<Recipe>();
		
		List<Recipe> localResults = new ArrayList<Recipe>();
		List<Recipe> remoteResults = new ArrayList<Recipe>();
		
		if (searchLocally) {
			localResults = model.searchRecipe(keywords);
		}
		
		if (searchFromWeb) {
			try {
				remoteResults = server.search(keywords);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
		return this.mergeResults(localResults, remoteResults);
	}
	
	/**
	 * Searches the RecipeModel for recipes containing the given ingredients. Can be instructed
	 * to search locally, the internet, or both.
	 * @param keywords corresponding to ingredients to be searched for
	 * @param searchLocally set to true if the search is to be conducted locally
	 * @param searchFromWeb set to true if the search is to be conducted on the web
	 * @return a list of recipes that resulted from the search
	 */
	public List<SearchResult> searchWithIngredients(String[] keywords, boolean searchLocally, 
									  boolean searchFromWeb) {
		List<Recipe> localResults = new ArrayList<Recipe>();
		List<Recipe> remoteResults = new ArrayList<Recipe>();
		
		if (searchLocally) {
			localResults = model.searchWithIngredient(keywords, myKitchen.getIngredients());
		}
		
		if (searchFromWeb) {
			try {
				remoteResults = server.searchWithIngredient(keywords, myKitchen.getIngredients());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
		return this.mergeResults(localResults, remoteResults);
	}
	
	/**
	 * Merge tow lists and encapsulates the results using the SearchResult class
	 * @param list1 the list of local results
	 * @param list2 the list of results from the server
	 */
	private List<SearchResult> mergeResults(List<Recipe> list1, List<Recipe> list2) {
		ArrayList<SearchResult> results = new ArrayList<SearchResult>();
		
		for (Recipe recipe : list1) {
			results.add(new SearchResult(recipe.getName(), recipe.getRecipeID(), recipe.getServerId(), SearchResult.SOURCE_LOCAL));
		}
		
		for (Recipe recipe : list2) {
			results.add(new SearchResult(recipe.getName(), recipe.getRecipeID(), recipe.getServerId(), SearchResult.SOURCE_REMOTE));
		}
		
		return results;
	}
	
	/**
	 * Returns a recipe that matches the given ID
	 * @param id of a recipe
	 * @return the recipe with given id
	 */
	public Recipe getRecipe(long id) {
		return model.getRecipeById(id);
	}
	
	/**
	 * Returns the recipe directly after the given ID
	 * @param id of a recipe
	 * @return the recipe with the id after the given id
	 */
	public Recipe getNextRecipe(long id){
		return model.getNextRecipeById(id);
	}
	
	/**
	 * Returns the recipe directly prior to the given ID
	 * @param id of a recipe
	 * @return the recipe with the id prior to the given ID
	 */
	public Recipe getPreviousRecipe(long id){
		return model.getPreviousRecipeById(id);
	}
	
	/**
	 * Returns the entire collection of recipes
	 * @return a list containing all recipes contained in the model
	 */
	public List<Recipe> getRecipes() {
		return model.getAllRecipes();
	}
	
	/**
	 * Adds a recipe to the model's collection
	 * @param r the recipe to be added
	 */
	public void addRecipe(Recipe r) {
		model.add(r);
	}
	
	/**
	 * Replaces the recipe with given id with a different recipe
	 * @param r the recipe that is to take the other recipes place
	 * @param id the id of the recipe that is to be replaced
	 */
	public void replaceRecipe(Recipe r, long id) {
		model.replaceRecipe(r, id);
		// should replace recipe with given id with r
	}
	
	/**
	 * Returns the entire collection of ingredients
	 * @return a list containing all ingredients contained in the model
	 */
	public List<Ingredient> getIngredients() {
		return myKitchen.getIngredients();
	}
	
	/**
	 * Removes the given ingredient from the collection
	 * @param ingredient to be removed
	 */
	public void deleteIngredient(Ingredient ingredient) {
		myKitchen.remove(ingredient);
	}
	
	/**
	 * Searches the collection of ingredients for the given keywords
	 * @param keywords the keywords to be searched for
	 * @return a list containing all the ingredients that contained a keyword
	 */
	public List<Ingredient> searchIngredient(String[] keywords) {
		return myKitchen.searchIngredient(keywords);
	}
	
	/**
	 * Adds the given ingredient to the collection
	 * @param ingredient to be added
	 */
	public void addIngredient(Ingredient ingredient) throws DuplicateIngredientException {
		myKitchen.add(ingredient);
	}
	
	/**
	 * Returns an ingredient of a given type
	 * @param type the type of ingredient desired
	 */
	public Ingredient getIngredient(String type) {
		return myKitchen.getIngredient(type);
	}
	
	/**
	 * Replaces an ingredient with a new one
	 * @param oldIngredient the ingredient to be replaced
	 * @param newIngredient a new ingredient
	 */
	public void replaceIngredient(Ingredient oldIngredient, Ingredient newIngredient) {
		myKitchen.replaceIngredient(oldIngredient, newIngredient);
	}
	
	/**
	 * Returns an ingredient from a specific recipe
	 * @param recipe the recipe which contains the desired ingredient
	 * @param type the type of ingredient desired
	 * @return
	 */
	public Ingredient getIngredient(Recipe recipe, String type) {
		return recipe.getIngredient(type);
	}
	
	/**
	 * Adds an ingredient to a recipe
	 * @param recipe the recipe that the ingredient is being added to
	 * @param ingredient the ingredient being added
	 * @throws DuplicateIngredientException thrown if the recipe already contains the given ingredient
	 */
	public void addIngredient(Recipe recipe, Ingredient ingredient) throws DuplicateIngredientException {
		recipe.addIngredient(ingredient);
	}
	
	/**
	 * Removes an ingredient from a recipe
	 * @param recipe the recipe that the ingredient is being removed from
	 * @param ingredient the ingredient to be removed
	 */
	public void deleteIngredient(Recipe recipe, Ingredient ingredient) {
		recipe.removeIngredient(ingredient);
	}
	
	/**
	 * Replaces an ingredient within a recipe with another ingredient
	 * @param recipe the recipe that contains an ingredient to be replace
	 * @param oldIngredient the ingredient being replace
	 * @param newIngredient the new ingredient
	 */
	public void replaceIngredient(Recipe recipe, Ingredient oldIngredient, Ingredient newIngredient) {
		recipe.replaceIngredient(oldIngredient, newIngredient);
	}
	
	/**
	 * Removes a recipe from the model's collection
	 * @param r the recipe to be removed
	 */
	public void deleteRecipe(Recipe r){
		model.remove(r);
	}
	
	/**
	 * Returns the recipe model
	 * @return the recipe model
	 */
	public RecipeModel getModel() {
		return this.model;
	}
	
	public boolean canPublish(Recipe recipe) {
		return server.canPublish(recipe);
	}
	
	public void publishRecipe(Recipe recipe) throws IOException {
		try {
			server.publishRecipe(recipe);
		} catch (ServerPermissionException e) {
			e.printStackTrace();
		}
	}
	
	public Recipe downloadRecipe(String serverId) throws IOException {
		return server.download(serverId);
	}
	
	public void postComment(String recipeId, String comment) throws IOException {
		server.postComment(recipeId, comment);
	}
}