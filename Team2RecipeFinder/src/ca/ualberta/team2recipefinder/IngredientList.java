package ca.ualberta.team2recipefinder;

import java.util.ArrayList;

public class IngredientList{
	private ArrayList<Ingredient> ingredientList;
	
	public IngredientList(){
		ingredientList=new ArrayList<Ingredient>();
	}
	
	public void add(Ingredient ingredient){
		if(!ingredientList.contains(ingredient))
			ingredientList.add(ingredient);
	}
	public void remove(Ingredient ingredient){
		ingredientList.remove(ingredient);
	}
	public boolean search(Ingredient ingredient){
		if(ingredientList.contains(ingredient))
			return true;
		return false;
	}
}