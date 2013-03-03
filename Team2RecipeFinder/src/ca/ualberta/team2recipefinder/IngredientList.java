package ca.ualberta.team2recipefinder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class IngredientList extends Model<View>{
	private String filename="IngredientList.sav";
	
	public IngredientList(){
	}
	
	public ArrayList<Ingredient> load() {  
		ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();
		   
		try {  
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));  
			ingredientList = (ArrayList<Ingredient>) in.readObject();
		} 
		catch (IOException e) {  
			e.printStackTrace();  
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();  
		}
			
		return ingredientList;
	}
	   
	public void add(Ingredient ingredient){
		ArrayList<Ingredient> ingredientList=load();
		if(!ingredientList.contains(ingredient))
			ingredientList.add(ingredient);
		sort(ingredientList);
		write(ingredientList);
	}
	public void remove(Ingredient ingredient){
		ArrayList<Ingredient> ingredientList=load();
		ingredientList.remove(ingredient);
		write(ingredientList);
	}
	public boolean search(Ingredient ingredient){
		ArrayList<Ingredient> ingredientList=load();
		if(ingredientList.contains(ingredient))
			return true;
		return false;
	}
	
	private void write(ArrayList<Ingredient> ingredientList){
		try {  
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(ingredientList);
			out.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  	
	}
	
	private void sort(ArrayList<Ingredient> ingredientList){
		Ingredient temp;
		   
		for(int n = ingredientList.size()-1; n > 1; n--)
			for(int i = 0; i<n; i++){
				if(ingredientList.get(i).getType().compareToIgnoreCase(ingredientList.get(i+1).getType()) > 0){
					temp = ingredientList.get(i);
		   			ingredientList.set(i, ingredientList.get(i+1));
		   			ingredientList.set(i+1, temp);
		   		}				   
			}
	}
	
	public ArrayList<Ingredient> searchRecipe(String keyword) {
		ArrayList<Ingredient> matchingIngredients = new ArrayList<Ingredient>();
		ArrayList<Ingredient> ingredientList = load();
		   
		   
		for(int i = 0; i<ingredientList.size(); i++){
			if(ingredientList.get(i).getType().toLowerCase(Locale.ENGLISH).contains(keyword.toLowerCase(Locale.ENGLISH)) || ingredientList.get(i).getAuthor().toLowerCase(Locale.ENGLISH).contains(keyword.toLowerCase(Locale.ENGLISH)) || ingredientList.get(i).getProcedure().toLowerCase(Locale.ENGLISH).contains(keyword))
				matchingIngredients.add(ingredientList.get(i));
			}
		}
		   	   
		return matchingIngredients;
		   
	}
}