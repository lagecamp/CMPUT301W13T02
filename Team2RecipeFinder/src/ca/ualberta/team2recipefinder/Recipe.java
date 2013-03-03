package ca.ualberta.team2recipefinder;


import java.io.Serializable;




public class Recipe implements Serializable
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String procedure;
	private String author;
	private String[] ingredients;
	private boolean onServer;
	
	public Recipe(){}
	
	public Recipe(String name, String procedure, String author, String[] ingredients, boolean onServer){
		this.name = name;
		this.procedure = procedure;
		this.author = author;
		this.ingredients = ingredients;
		this.onServer = onServer;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getProcedure(){
		return procedure;
	}
	
	public void setProcedure(String procedure){
		this.procedure = procedure;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
	
	public String[] getIngredients(){
		return ingredients;
	}
	
	public void setIngredients(String[] ingredients){
		this.ingredients = ingredients;
	}
	/*
	 * Returns the ingredients as a list
	 */
	public String getIngredientsToString(){
		String ingredientsList = new String();
		for(int i = 0; i<ingredients.length; i++){
			ingredientsList += ingredients[i] + ", ";
		}
		return ingredientsList;
	}
	

	
	public boolean getOnServer(){
		return onServer;
	}
	
	public void setOnServer(boolean onServer){
		this.onServer = onServer;
	}
	
	
	
}
