package ca.ualberta.team2recipefinder.model;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class ElasticSearchRecipe {
	public String name;
	public String procedure;
	public String author;
	public List<Ingredient> ingredients;
	public List<String> comments;
	public long id;
	public List<Drawable> images;
	public String userId;
	
	public ElasticSearchRecipe(Recipe recipe) {
		this.name = recipe.getName();
		this.procedure = recipe.getProcedure();
		this.author = recipe.getAuthor();
		this.ingredients = recipe.getIngredients();
		this.comments = recipe.getAllComments();
		this.id = recipe.getRecipeID();
		this.images = recipe.getAllPhotos();
		/* this.images = new LinkedList<String>();
		
		for (Drawable image : recipe.getAllPhotos()) {
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
		    byte[] image=stream.toByteArray();
		    System.out.println("byte array:"+image);
		    String img_str = Base64.encodeToString(image, 0);
		    
			this.images.add(image)
		} */
		
		this.userId = recipe.getUserId();
	}
	
	public Recipe toRecipe(String serverId) {
		Recipe recipe = new Recipe(this.id, this.ingredients, this.comments, this.images);
		recipe.setName(this.name);
		recipe.setProcedure(this.procedure);
		recipe.setAuthor(this.author);		
		recipe.setUserId(userId);
		recipe.setServerId(serverId);
		
		return recipe;
	}
}
