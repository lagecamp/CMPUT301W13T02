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
		return model.searchRecipe(keywords, searchLocally, searchFromWeb);
	}
	
	public List<Recipe> searchWithIngredients(String[] keywords, boolean searchLocally, 
									  boolean searchFromWeb) {
		return model.searchWithIngredient(keywords, searchLocally, searchFromWeb);
		
		/*// stil working in  the search with ingredients
		// for now, it will search without considering the ingredients
		return model.searchRecipe(keywords);*/
	}
	
	public Recipe getRecipe(long id) {
		return model.getRecipeById(id);
	}
	
	public Recipe getNextRecipe(long id){
		return model.getNextRecipeById(id);
	}
	
	public Recipe getPreviousRecipe(long id){
		return model.getPreviousRecipeById(id);
	}
	
	public List<Recipe> getRecipes() {
		return model.getAllRecipes();
	}
	
	public void addRecipe(Recipe r) {
		model.add(r);
	}
	
	public void replaceRecipe(Recipe r, long id) {
		model.replaceRecipe(r, id);
		// should replace recipe with given id with r
	}
	
	public List<Ingredient> getIngredients() {
		return myKitchen.getIngredients();
	}
	
	public void deleteIngredient(Ingredient ingredient) {
		myKitchen.remove(ingredient);
	}
	
	public List<Ingredient> searchIngredient(String[] keywords) {
		return myKitchen.searchIngredient(keywords);
	}
	
	public void addIngredient(Ingredient ingredient) {
		myKitchen.add(ingredient);
	}
	
	public Ingredient getIngredient(String type) {
		return myKitchen.getIngredient(type);
	}
	
	public void replaceIngredient(Ingredient oldIngredient, Ingredient newIngredient) {
		myKitchen.replaceIngredient(oldIngredient, newIngredient);
	}
	
	public Ingredient getIngredient(Recipe recipe, String type) {
		return recipe.getIngredient(type);
	}
	
	public void addIngredient(Recipe recipe, Ingredient ingredient) {
		recipe.addIngredient(ingredient);
	}
	
	public void deleteIngredient(Recipe recipe, Ingredient ingredient) {
		recipe.removeIngredient(ingredient);
	}
	
	public void replaceIngredient(Recipe recipe, Ingredient oldIngredient, Ingredient newIngredient) {
		recipe.replaceIngredient(oldIngredient, newIngredient);
	}
	
	public void deleteRecipe(Recipe r){
		model.remove(r);
	}
	
	public RecipeModel getModel() {
		return this.model;
	}
}
