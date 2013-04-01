package ca.ualberta.team2recipefinder.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import ca.ualberta.team2recipefinder.controller.RecipeFinderApplication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This class encapsulates all the requests to/from the server that stores
 * recipes remotely. Some of the code was based upon Chenlei's code
 * showed in the lab
 * @author cmput-301 team 2
 *
 */
public class RemoteRecipes {
	/**
	 * The link to the remote database
	 */
	private final String REMOTE_DB_LINK = "http://cmput301.softwareprocess.es:8080/cmput301w13t02/";

	private final String USERS = "user/";
	private final String RECIPES = "recipe/";

	private HttpClient httpclient = new DefaultHttpClient();
	private Gson gson = new Gson();
	
	private String userId = "";
	private String path;
	
	/**
	 * Default constructor
	 */
	public RemoteRecipes(String path) {
		this.path = path;
	}
	
	/**
	 * Opens the local file and returns the id of the local user. If the user does not
	 * have an id, a new id will be generated for him
	 * @return
	 * @throws IOException
	 */
	private String getUserId() throws IOException {
		if (!this.userId.equals("")) {
			return this.userId;
		}
		
		String userId = "";

		try {  
			BufferedReader  in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));  
			userId = in.readLine();
			in.close();
		} catch (IOException e) {  
			// in case of exception, the user has no id yet and
			// a new id should be generated for him
			userId = saveUserId();
		}

		this.userId = userId;
		return userId;
	}

	/**
	 * Save the user id in the file
	 * @return
	 * @throws IOException
	 */
	private String saveUserId() throws IOException {
		String userId;
		
		userId = generateUserId("user");
		
		try {  
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));  
			out.write(userId);
			out.close();
			Log.d("server", "file saved");
		} catch (IOException e) {  
			e.printStackTrace();
		}

		return userId;
	}

	/**
	 * Go to the server and create a new user. An id
	 * will be automatically generated by the server
	 * @param userName
	 * @return
	 * @throws IOException
	 */
	private String generateUserId(String userName) throws IOException {
		HttpPost httpPost = new HttpPost(REMOTE_DB_LINK + USERS);
		StringEntity stringentity = null;

		try {
			stringentity = new StringEntity(" { \"user\" : \"" + userName + "\" } ");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		httpPost.setHeader("Accept","application/json");
		httpPost.setEntity(stringentity);
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} 

		String status = response.getStatusLine().toString();
		Log.d("server", status);

		String json = getEntityContent(response);

		Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<Object>>(){}.getType();
		ElasticSearchResponse<Object> esResponse = gson.fromJson(json, elasticSearchResponseType);

		return esResponse._id;
	}
	
	/**
	 * Returns true if the user has permission to publish the recipe
	 * @param recipe
	 * @return
	 */
	public boolean canPublish(Recipe recipe) {
		return (recipe.getUserId().equals("") || recipe.getUserId().equals(userId));
	}

	/**
	 * Publish a recipe
	 * @param recipe the recipe
	 * @throws IOException 
	 * @throws ServerPermissionException
	 */
	public void publishRecipe(Recipe recipe) throws IOException, ServerPermissionException {
		String userId = getUserId();
		
		// if the recipe has a user already (and this user is not the local user),
		// the local user cannot publish it
		if (!canPublish(recipe)) {
			throw new ServerPermissionException("You are not allowed to publish this recipe!"); 
		}
		
		// assign the local user as owner of the recipe
		recipe.setUserId(userId);
		
		ElasticSearchRecipe esRecipe = new ElasticSearchRecipe(recipe);
		
		// if the recipe is not on the server,
		// post it
		if (recipe.getServerId().equals("")) {						
			HttpPost httpPost = new HttpPost(REMOTE_DB_LINK + RECIPES);
			StringEntity stringentity = null;
	
			try {
				stringentity = new StringEntity(gson.toJson(esRecipe));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	
			httpPost.setHeader("Accept","application/json");
			httpPost.setEntity(stringentity);
			HttpResponse response = null;
	
			try {
				response = httpclient.execute(httpPost);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} 
	
			String status = response.getStatusLine().toString();
			Log.d("server", status);
	
			String json = getEntityContent(response);
	
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<Object>>(){}.getType();
			ElasticSearchResponse<Object> esResponse = gson.fromJson(json, elasticSearchResponseType);
	
			recipe.setServerId(esResponse._id);
		}
		// if it is on the server already, update it
		else {
			HttpPut httpPut = new HttpPut(REMOTE_DB_LINK + RECIPES + recipe.getServerId());
			
			StringEntity stringentity = null;
			
			try {
				stringentity = new StringEntity(gson.toJson(esRecipe));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	
			httpPut.setHeader("Accept","application/json");
			httpPut.setEntity(stringentity);
			HttpResponse response = null;
	
			try {
				response = httpclient.execute(httpPut);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} 
	
			String status = response.getStatusLine().toString();
			Log.d("server", status);
	
			String json = getEntityContent(response);
	
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<Object>>(){}.getType();
			ElasticSearchResponse<Object> esResponse = gson.fromJson(json, elasticSearchResponseType);
		}
	}

	/**
	 * Add a comment to a recipe that is already on the server
	 * @param recipeId the id of the recipe
	 * @param comment the comment
	 * @throws IOException
	 */
	public void postComment(String recipeId, String comment) throws IOException {		
		HttpPost request = new HttpPost(REMOTE_DB_LINK + RECIPES + recipeId + "/_update");
		String query = 	"{\"script\" : \"ctx._source.comments += comment\",\"params\" : {\"comment\" : \"" + comment + "\"}}";
		
		StringEntity stringentity = null;		
		try {
			stringentity = new StringEntity(query);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		request.setHeader("Accept","application/json");
		request.setEntity(stringentity);
		
		HttpResponse response = null;
		try {
			response = httpclient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		
		String status = response.getStatusLine().toString();
		Log.d("server", status);
	}

	/**
	 * Search for a recipe on the server
	 * @param keywords list of keywords
	 * @return the recipes that match the search criteria
	 * @throws IOException
	 */
	public List<Recipe> search(String[] keywords) throws IOException {
		String query_str = "";
		
		// create query string
		for (int i = 0; i < keywords.length; i++) {
			query_str += keywords[i] + " OR ";
		}
		
		query_str = query_str.substring(0, query_str.length() - 4);	
		
		HttpPost searchRequest = new HttpPost(REMOTE_DB_LINK + RECIPES + "/_search");
		String query = 	"{\"query\" : {\"query_string\" : {\"fields\" : [ \"name\", \"procedure\", \"ingredients.type\"],\"query\" : \"" + query_str + "\"}}}";
		
		StringEntity stringentity = null;		
		try {
			stringentity = new StringEntity(query);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		searchRequest.setHeader("Accept","application/json");
		searchRequest.setEntity(stringentity);
		
		HttpResponse response = null;
		try {
			response = httpclient.execute(searchRequest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		
		String status = response.getStatusLine().toString();
		Log.d("server", status);

		String json = getEntityContent(response);

		Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<ElasticSearchRecipe>>(){}.getType();
		ElasticSearchSearchResponse<ElasticSearchRecipe> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
		
		// return recipes
		LinkedList<Recipe> results = new LinkedList<Recipe>();
		for (ElasticSearchResponse<ElasticSearchRecipe> r : esResponse.getHits()) {
			results.add(r.getSource().toRecipe(r._id));
		}
		
		return results;
	}

	/**
	 * Search for recipes on the server that match the ingredients
	 * that are provided
	 * @param keywords list of keywords
	 * @param kitchenIngredients the ingredients you have
	 * @return list of recipes that match the criteria
	 * @throws IOException
	 */
	public List<Recipe> searchWithIngredient(String[] keywords,
			List<Ingredient> kitchenIngredients) throws IOException {
		String query_str = "(";
		
		// create query string (for keywords)
		for (int i = 0; i < keywords.length; i++) {
			query_str += keywords[i] + " OR ";
		}						
		
		query_str = query_str.substring(0, query_str.length() - 4);	
		query_str += ")";
		
		if (kitchenIngredients.size() > 0) {
			query_str += " AND (";
		}
		
		// create query string (for ingredients)
		for (Ingredient ingredient : kitchenIngredients) {
			query_str += "ingredients.type:" + ingredient.getType() + " OR ";
		}
		
		if (kitchenIngredients.size() > 0) {
			query_str = query_str.substring(0, query_str.length() - 4);	
			query_str += ")";
		}
				
		HttpPost searchRequest = new HttpPost(REMOTE_DB_LINK + RECIPES + "/_search");
		String query = 	"{\"query\" : {\"query_string\" : {\"fields\" : [ \"name\", \"procedure\"], \"query\" : \"" + query_str + "\"}}}";
		
		StringEntity stringentity = null;		
		try {
			stringentity = new StringEntity(query);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		searchRequest.setHeader("Accept","application/json");
		searchRequest.setEntity(stringentity);
		
		HttpResponse response = null;
		try {
			response = httpclient.execute(searchRequest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		
		String status = response.getStatusLine().toString();
		Log.d("server", status);

		String json = getEntityContent(response);

		Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<ElasticSearchRecipe>>(){}.getType();
		ElasticSearchSearchResponse<ElasticSearchRecipe> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
		
		// return recipes
		LinkedList<Recipe> results = new LinkedList<Recipe>();
		for (ElasticSearchResponse<ElasticSearchRecipe> r : esResponse.getHits()) {
			results.add(r.getSource().toRecipe(r._id));
		}
		
		return results;		
	}

	/**
	 * Adds a picture to the recipe
	 * @param recipeId the id of the recipe
	 * @param image the image you want to add to the recipe
	 * @throws IOException 
	 */
	public void postPicture(String recipeId, Bitmap image) throws IOException {
		String image_str = ElasticSearchRecipe.convertImageToBase64(image);
		
		HttpPost request = new HttpPost(REMOTE_DB_LINK + RECIPES + recipeId + "/_update");
		String query = 	"{\"script\" : \"ctx._source.images += image\",\"params\" : {\"image\" : \"" + image_str + "\"}}";
		
		StringEntity stringentity = null;		
		try {
			stringentity = new StringEntity(query);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		request.setHeader("Accept","application/json");
		request.setEntity(stringentity);
		
		HttpResponse response = null;
		try {
			response = httpclient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		
		String status = response.getStatusLine().toString();
		Log.d("server", status);
	}

	/**
	 * Downloads a recipe from the server
	 * @param recipeId the id of the recipe
	 * @return the recipe
	 * @throws IOException
	 */
	public Recipe download(String recipeId) throws IOException {
		HttpGet getRequest = new HttpGet(REMOTE_DB_LINK + RECIPES + recipeId);
		getRequest.addHeader("Accept","application/json");
		HttpResponse response = httpclient.execute(getRequest);

		String status = response.getStatusLine().toString();
		Log.d("server", status);

		String json = getEntityContent(response);

		Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<ElasticSearchRecipe>>(){}.getType();
		ElasticSearchResponse<ElasticSearchRecipe> esResponse = gson.fromJson(json, elasticSearchResponseType);
		ElasticSearchRecipe esRecipe = esResponse.getSource();
		
		return esRecipe.toRecipe(recipeId);
	}

	/**
	 * Returns the content of an entity
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));
		String output;		
		String json = "";
		while ((output = br.readLine()) != null) {
			json += output;
		}
		return json;
	}


}
