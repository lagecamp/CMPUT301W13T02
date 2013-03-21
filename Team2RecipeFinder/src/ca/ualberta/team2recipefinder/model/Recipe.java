/*
* @RecipeModel.java 1.0 13/03/01
*
* The following source is protected by a GNU Public License
*/
package ca.ualberta.team2recipefinder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.team2recipefinder.views.View;

import android.graphics.drawable.Drawable;

/**
* Recipe Class: This class represents our recipe objects. All recipes have a Name, Procedure, Ingredient List, and Author.
* The class is able to set and get any of these attributes.
*
* @author CMPUT 301 Team 2
*
*/
public class Recipe extends Model<View> implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String name;
	private String procedure;
	private String author;
	private List<Ingredient> ingredients;
	private List<String> comments;
	private boolean onServer;
	private long id;
	private List<Drawable> images;
	private String userId;
	private String serverId;
	
	/**
	 * Default Recipe Constructor
	 * Creates a Recipe with initialized but empty values
	 */
	public Recipe() {
		name = new String();
		procedure = new String();
		author = new String();
		ingredients = new ArrayList<Ingredient>();
		onServer = false;
		id = System.currentTimeMillis();
		comments = new ArrayList<String>();
		images = new ArrayList<Drawable>();
	 	this.userId = "";
	 	this.serverId = "";
	}
	
	/**
	 * 
	 * @param name Name of the Recipe being added
	 * @param procedure Procedure of the Recipe
	 * @param author Author of the Recipe
	 * @param ingredients A list of Ingredient objects
	 * @param onServer Whether the recipe is on the server
	 */
	public Recipe(String name, String procedure, String author, List<Ingredient> ingredients, boolean onServer){
		this.name = name;
		this.procedure = procedure;
		this.author = author;
		this.ingredients = ingredients;
		this.onServer = onServer;
	 	this.id = System.currentTimeMillis();
	 	this.userId = "";
	 	this.serverId = "";
	}
	
	/**
	 * Creates a recipe with a specific id
	 * @param id
	 */
	public Recipe(long id, List<Ingredient> ingredients, List<String> comments, List<Drawable> images) {
		this.id = id;
	 	this.userId = "";
	 	this.serverId = "";
	 	this.ingredients = ingredients;
	 	this.comments = comments;
	 	this.images = images;
	}
	
	/**
	 * 
	 * @return Returns a long which corresponds to a recipe ID
	 */
	public long getRecipeID(){
		return id;
	}
	
	/**
	 * 
	 * @return Returns a string corresponding to the recipe's name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return Returns a string of the name
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * 
	 * @param name The name of the Recipe
	 */
	public void setName(String name){
		this.name = name;
		notifyViews();
	}
	
	/**
	 * 
	 * @return Returns the recipe's procedure
	 */
	public String getProcedure(){
		return procedure;
	}
	
	/**
	 * 
	 * @param procedure The procedure of the recipe
	 */
	public void setProcedure(String procedure){
		this.procedure = procedure;
		notifyViews();
	}
	
	/**
	 * 
	 * @return Returns the recipe's author
	 */
	public String getAuthor(){
		return author;
	}
	
	/**
	 * 
	 * @param author The author's name for the recipe
	 */
	public void setAuthor(String author){
		this.author = author;
		notifyViews();
	}
	
	/**
	 * 
	 * @return Returns the list of ingredients from the recipe
	 */
	public List<Ingredient> getIngredients(){
		return ingredients;
	}
	
	/**
	 * 
	 * @param ingredient Ingredient that will be removed
	 */
	public void removeIngredient(Ingredient ingredient) {
		ingredients.remove(ingredient);
		notifyViews();
	}
	
	/**
	 * 
	 * @param oldIngredient Old Ingredient to be deleted
	 * @param newIngredient New Ingredient to be added
	 */
	public void replaceIngredient(Ingredient oldIngredient, Ingredient newIngredient) {
		ingredients.remove(oldIngredient);
		ingredients.add(newIngredient);
		notifyViews();
	}
	
	/**
	 * Add ingredient to recipe
	 * @param ingredient Ingredient to be added
	 * @throws DuplicateIngredientException 
	 */
	public void addIngredient(Ingredient ingredient) throws DuplicateIngredientException {
		boolean alreadyThere = false;
		for (int n = 0; n < ingredients.size(); n++) {
			if (ingredients.get(n).getType().equalsIgnoreCase(ingredient.getType())) {
				alreadyThere = true;
				throw new DuplicateIngredientException(ingredient.getType());
			}
		}
		if (!alreadyThere){
			ingredients.add(ingredient);
			notifyViews();
		}
	}
	
	/**
	 * 
	 * @param type The ingredient name
	 * @return
	 */
	public Ingredient getIngredient(String type) {
		for (Ingredient ingredient : ingredients) {
			if (ingredient.getType().equals(type)) {
				return ingredient;
			}
		}		
		return null;
	}
	
	/**
	 * 
	 * @return Returns the boolean to say whether it is on the server
	 */
	public boolean getOnServer(){
		return onServer;
	}
	
	/**
	 * 
	 * @param onServer True or false depending on whether the recipe is on the server
	 */
	public void setOnServer(boolean onServer){
		this.onServer = onServer;
		notifyViews();
	}
	
	public List<Drawable> getAllPhotos() {
		return images;
	}
	
	public void addPhotos(Drawable image) {
		images.add(image);
	}
	
	public void removePhoto(int index) {
		images.remove(index);
	}
	
	public Drawable getPhoto(int index) {
		Drawable photo;
		try {
			photo = images.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return photo;	
	}
	
	public void addComment(String comment) {
		comments.add(comment);
	}
	
	public List<String> getAllComments() {
		return comments;
	}
	
	public String getComment(int index) {
		String comment;
		try {
			comment = comments.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return comment;
	}
	
	public boolean hasPhotos() {
		if (images.size() == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	
	public String getServerId() {
		return this.serverId;
	}
}