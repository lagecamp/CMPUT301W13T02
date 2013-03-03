

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Locale;



public class RecipeModel
{
  private static final String filename = "file.sav";
	private ArrayList<Recipe> recipes; 
	
	/*
	 * Constructor
	 */
	public RecipeModel(){
		this.recipes= new ArrayList<Recipe>();
	}
	
	
	/*
	 * Add a new Recipe to the Recipe Array List and then write it to the phone's database
	 */
	   public void add(Recipe recipe) {
		   this.recipes = load();
		   recipes.add(recipe);
		   sortRecipes();
		   writeFile(recipes);
	}
	   /*
	    * Remove the specified recipe
	    */
	   public void remove(Recipe recipe){
		   for(int i = 0; i<recipes.size(); i++){
			   if(recipe.getName() == recipes.get(i).getName()  && recipe.getAuthor() == recipes.get(i).getAuthor()  && recipe.getProcedure() == recipes.get(i).getProcedure()){
				   recipes.remove(i);
				   break;
			   }		   
		   }
		   recipes.remove(recipe);
		   writeFile(recipes);
	   }
	   
	   /*
	    * Write an arraylist of recipes to the phone's database
	    */
	   public static void writeFile(ArrayList<Recipe> recipes) {  
			try {  
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
				out.writeObject(recipes);
				out.close();  
			} catch (FileNotFoundException e) {  
				e.printStackTrace();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		} 
	   
	 /*
	  * Load an array of recipes from the phone's database 
	  */	   
	   public ArrayList<Recipe> load() {  
		   ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		   
		   try {  
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));  
				recipes = (ArrayList<Recipe>) in.readObject();
				

			} 
			catch (IOException e) {  
				e.printStackTrace();  
			} catch (ClassNotFoundException e) {  
				e.printStackTrace();  
			}
			
		   return recipes;
	   }
	   
	   /*
	    * Searches the phone's recipes for the specific keyword.  It looks at the recipe's name, procedure, author, and ingredients
	    */
	   public ArrayList<Recipe> searchRecipe(String keyword) {
		   //Array List of Recipes that match the keyword
		   ArrayList<Recipe> matchingrecipes = new ArrayList<Recipe>();
		   
		   
		   //Searches the Arrraylist and looks for any recipe name, author, procedure which contains the keyword
		   for(int i = 0; i<recipes.size(); i++){
			   if(recipes.get(i).getName().toLowerCase(Locale.ENGLISH).contains(keyword.toLowerCase(Locale.ENGLISH)) || recipes.get(i).getAuthor().toLowerCase(Locale.ENGLISH).contains(keyword.toLowerCase(Locale.ENGLISH)) || recipes.get(i).getProcedure().toLowerCase(Locale.ENGLISH).contains(keyword))
				   matchingrecipes.add(recipes.get(i));
			   else{
				   //Searches the Arraylist and looks for any ingredient that contains the keyword
				   for(int n = 0; n<recipes.get(i).getIngredients().length; n++)
					   if(recipes.get(i).getIngredients()[n].toLowerCase(Locale.ENGLISH).contains(keyword.toLowerCase(Locale.ENGLISH)))
						   matchingrecipes.add(recipes.get(i));
			   }
		   }
		   	   
		   return matchingrecipes;
		   
	   }
	   
	   /*
	    * Searches the database for recipes in which the user has every ingredient
	    */
	   /*public Recipe[] searchWithIngredient(String keyword){
		   IngredientModel im = new IngredientModel();
		   
		   
	   } */
	   /*
	    * Sorts the recipe in alphabetical order by the recipe's name
	    */
	   private void sortRecipes(){
		   Recipe temp;
		   
		   for(int n = recipes.size()-1; n > 1; n--)
		   		for(int i = 0; i<n; i++){
		   			if(recipes.get(i).getName().compareToIgnoreCase(recipes.get(i+1).getName()) > 0){
		   				temp = recipes.get(i);
		   				recipes.set(i, recipes.get(i+1));
		   				recipes.set(i+1, temp);
		   			}				   
		   		}
	   }
	   
	   
	   

		
	
	
	
}