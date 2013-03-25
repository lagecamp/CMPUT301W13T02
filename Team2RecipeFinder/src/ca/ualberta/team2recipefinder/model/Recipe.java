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

import android.graphics.Bitmap;
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
	private List<SerializableImage> images;
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
		images = new ArrayList<SerializableImage>();
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
	public Recipe(long id, List<Ingredient> ingredients, List<String> comments, List<Bitmap> images) {
		this.id = id;
	 	this.userId = "";
	 	this.serverId = "";
	 	this.ingredients = ingredients;
	 	this.comments = comments;
	 	this.images = toSerializableImage(images);
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
	
	public void removeComment(String oldComment) {
		comments.remove(oldComment);
	}
	
	public void replaceComment(String oldComment, String newComment) {
		comments.remove(oldComment);
		comments.add(newComment);
	}
	
	/**
	 * 
	 * @param onServer True or false depending on whether the recipe is on the server
	 */
	public void setOnServer(boolean onServer){
		this.onServer = onServer;
		notifyViews();
	}
	
	public List<Bitmap> getAllPhotos() {
		return fromSerializableImage(images);
	}
	
	public void addPhoto(Bitmap image) {
		SerializableImage s_image = new SerializableImage();
		s_image.setImage(image);
		images.add(s_image);
	}
	
	public void removePhoto(int index) {
		images.remove(index);
	}
	
	public Bitmap getPhoto(int index) {
		Bitmap photo;
		try {
			photo = images.get(index).getImage();
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
	
	private List<SerializableImage> toSerializableImage(List<Bitmap> list) {
		ArrayList<SerializableImage> result = new ArrayList<SerializableImage>();
		
		for (Bitmap image : list) {
			SerializableImage s_image = new SerializableImage();
			s_image.setImage(image);
			result.add(s_image);
		}
		
		return result;
	}
	
	private List<Bitmap> fromSerializableImage(List<SerializableImage> list) {
		ArrayList<Bitmap> result = new ArrayList<Bitmap>();
		
		for (SerializableImage s_image : list) {
			Bitmap image = s_image.getImage();
			result.add(image);
		}
		
		return result;
	}
	
	public String getEmailText() {
		String str;
		str = "Recipe: " + name + "\n\n" ;
		str += "Author: " + author + "\n\n" ;
		str += "Ingredients: \n";
		for(Ingredient ingredient : ingredients ) {
			str += "\t" + ingredient.toString() + "\n";
		}
		str += "\nProcedure: \n";
		String[] procedureByLine = procedure.split("\n");
		for(String p : procedureByLine) {
			str += "\t" + p + "\n";
		}
		str += "\n\n\nThis e-mail is sent from Recipe Finder.";
		return str;
	}
	
	public String getEmailHtml() {
		String str;
		str = "<h4><b>Recipe: </b>" + name + "</h4><br/>" ;
		str += "<b>Author: </b>" + author + "<br/><br/>" ;
		str += "<h5>Ingredients: </h5><ul>";
		for(Ingredient ingredient : ingredients ) {
			str += "<li>" + ingredient.toString() + "</li>";
		}
		str += "</ul><h4>Procedure: </h4>";
		String[] procedureByLine = procedure.split("\n");
		for(String p : procedureByLine) {
			str += "\t" + p + "<br/>";
		}
		str += "<br/><br/><i>This e-mail is sent from <a href='https://github.com/lagecamp/CMPUT301W13T02'>Recipe Finder</a>.</i>";
		return str;
	}
	
}
