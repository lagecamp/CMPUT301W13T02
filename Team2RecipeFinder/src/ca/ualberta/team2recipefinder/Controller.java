package ca.ualberta.team2recipefinder;

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
		return model.searchRecipe(keywords, searchLocally, searchFromWeb);
	}
	
	public void searchWithIngredients(String[] keywords, boolean searchLocally, 
									  boolean searchFromWeb) {
		// for now, it only searches locally
		return model.searchWithIngredients(keyowords, searchLocally, searchFromWeb);
	}
	
	public Recipe getRecipe(int i) {
		
		return new Recipe();
	}
	
	public Recipe[] getRecipes() {
		
		return null;
	}
}
