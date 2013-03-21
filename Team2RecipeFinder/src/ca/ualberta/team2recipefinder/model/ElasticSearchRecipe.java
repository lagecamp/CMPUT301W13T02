package ca.ualberta.team2recipefinder.model;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

public class ElasticSearchRecipe {
	public String name;
	public String procedure;
	public String author;
	public List<Ingredient> ingredients;
	public List<String> comments;
	public long id;
	public List<String> images;
	public String userId;
	
	public ElasticSearchRecipe(Recipe recipe) {
		this.name = recipe.getName();
		this.procedure = recipe.getProcedure();
		this.author = recipe.getAuthor();
		this.ingredients = recipe.getIngredients();
		this.comments = recipe.getAllComments();
		this.id = recipe.getRecipeID();
		this.images = new LinkedList<String>();
		
		for (Drawable image : recipe.getAllPhotos()) {
		    String img_str = convertImageToBase64(image);		    
			this.images.add(img_str);
		}
		
		this.userId = recipe.getUserId();
	}
	
	public Recipe toRecipe(String serverId) {
		List<Drawable> images = new LinkedList<Drawable>();
		
		for (String image : this.images) {
		    Drawable img_dr = getImageFromBase64(image);		    
			images.add(img_dr);
		}
		
		Recipe recipe = new Recipe(this.id, this.ingredients, this.comments, images);
		recipe.setName(this.name);
		recipe.setProcedure(this.procedure);
		recipe.setAuthor(this.author);		
		recipe.setUserId(userId);
		recipe.setServerId(serverId);
		recipe.setOnServer(true);
		
		return recipe;
	}
	
	public static String convertImageToBase64(Drawable image) {
		Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] bitmapdata = stream.toByteArray();		
		return Base64.encodeToString(bitmapdata, Base64.DEFAULT);
	}
	
	public static Drawable getImageFromBase64(String image) {		
		byte[] imageData = Base64.decode(image, Base64.DEFAULT);		
		return new BitmapDrawable(BitmapFactory.decodeByteArray(imageData, 0, imageData.length));
	}
}
