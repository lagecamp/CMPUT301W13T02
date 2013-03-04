package ca.ualberta.team2recipefinder;

import java.util.ArrayList;
import java.util.List;

public class Controller {
	
	RecipeModel model;
	IngredientList myKitchen;
	
	public Controller(RecipeModel model, IngredientList myKitchen) {
		this.model = model;
		this.myKitchen = myKitchen;
	}
	
	public List<Recipe> search(String[] keywords, boolean searchLocally, boolean searchFromWeb) {
		// for now, it only searches locally
		return model.searchRecipe(keywords);
	}
	
	public List<Recipe> searchWithIngredients(String[] keywords, boolean searchLocally, 
									  boolean searchFromWeb) {
		// for now, it only searches locally
		//return model.searchWithIngredients(keyowords, searchLocally, searchFromWeb);
		
		// stil working in  the search with ingredients
		return model.searchRecipe(keywords);
	}
	
	public Recipe getRecipe(int id) {
		return model.getRecipeById(id);
	}
	
	public List<Recipe> getRecipes() {
		return model.getAllRecipes();
	}
	
	public void addRecipe(Recipe r) {
		model.add(r);
	}
	
	public void replaceRecipe(Recipe r, int id) {
		// should replace recipe with given id with r
	}
}
