package ca.ualberta.team2recipefinder.model;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

/**
 * Class that is used for representing a recipe that is on the
 * server. It used by the JSON parser
 * @author CMPUT 301 Team 2
 *
 */
public class ElasticSearchRecipe {
	private String name;
	private String procedure;
	private String author;
	private List<Ingredient> ingredients;
	private List<String> comments;
	private long id;
	private List<String> images;
	private String userId;
	
	/**
	 * Constructor that creates a new ElasticSearchRecipe based
	 * upon a Recipe
	 * @param recipe the recipe
	 */
	public ElasticSearchRecipe(Recipe recipe) {
		this.name = recipe.getName();
		this.procedure = recipe.getProcedure();
		this.author = recipe.getAuthor();
		this.ingredients = recipe.getIngredients();
		this.comments = recipe.getAllComments();
		this.id = recipe.getRecipeID();
		this.images = new LinkedList<String>();
		
		for (Bitmap image : recipe.getAllPhotos()) {
		    String img_str = convertImageToBase64(image);		    
			this.images.add(img_str);
		}
		
		this.userId = recipe.getUserId();
	}
	
	/**
	 * Converts the current instance of ElasticSearchRecipe
	 * into a Recipe
	 * @param serverId the id of this ElasticSearchRecipe on the server
	 * @return
	 */
	public Recipe toRecipe(String serverId) {
		List<Bitmap> images = new LinkedList<Bitmap>();
		
		for (String image : this.images) {
			Bitmap img_dr = getImageFromBase64(image);		    
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
	
	/**
	 * Converts a bitmap image to a Base64 string
	 * @param image the bitmap image
	 * @return the base64 string that corresponds to the image
	 */
	public static String convertImageToBase64(Bitmap image) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 20, stream);
		byte[] bitmapdata = stream.toByteArray();		
		return Base64.encodeToString(bitmapdata, Base64.URL_SAFE | Base64.NO_WRAP);
	}
	
	/**
	 * Converts a Base64 string into a bitmap image
	 * @param image the base64 string that corresponds to the image
	 * @return the bitmap image
	 */
	public static Bitmap getImageFromBase64(String image) {		
		byte[] imageData = Base64.decode(image, Base64.URL_SAFE | Base64.NO_WRAP);		
		return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
	}
}
