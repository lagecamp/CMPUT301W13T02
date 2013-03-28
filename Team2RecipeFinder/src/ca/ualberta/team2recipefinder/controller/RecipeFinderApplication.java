/* RecipeFinderApplication
 * 
 * Last Edited: March 7, 2013
 * 
 * As suggested by prof. Hindle
 * 
 */


package ca.ualberta.team2recipefinder.controller;

import ca.ualberta.team2recipefinder.model.MyKitchen;
import ca.ualberta.team2recipefinder.model.RecipeModel;
import ca.ualberta.team2recipefinder.model.RemoteRecipes;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Application, where the singletons are, as suggested by prof Hindle
 * 
 * @author cmput-301 team 2
 * @see ca.ualberta.team2recipefinder.model.RecipeModel
 * @see ca.ualberta.team2recipefinder.model.MyKitchen
 * @see ca.ualberta.team2recipefinder.controller.Controller
 */
public class RecipeFinderApplication extends Application {
	private static final String MY_KITCHEN_FILE = "IngredientList1.sav";// the name of the file where the ingredient list will be stored
	private static final String LOCAL_RECIPES_FILE = "file2.sav"; // stores local recipes
	private static final String USER_ID_FILE = "user.sav"; // stores the user id on the server
	
	// Singleton
	transient private static Controller controller = null;

	/**
	 * Returns the singleton of the Controller
	 * 
	 * @return the Controller
	 * @see ca.ualberta.team2recipefinder.controller.Controller
	 */
	public static Controller getController() {
		if (controller == null) {			
			controller = new Controller(getModel(), getMyKitchen(), getServer());
		}
		return controller;
    }
	
	// Singleton
	transient private static RecipeModel model = null;

	/**
	 * Returns the singleton of the Model
	 * 
	 * @return the RecipeModel
	 * @see ca.ualberta.team2recipefinder.model.RecipeModel
	 */
	public static RecipeModel getModel() {
		if (model == null) {
			String path = RecipeFinderApplication.getAppContext().getFilesDir() + "/" + LOCAL_RECIPES_FILE;
			model = new RecipeModel(path);
		}
		return model;
    }
	
	// Singleton
	transient private static MyKitchen myKitchen = null;

	/**
	 * Returns the singleton of MyKitchen
	 * 
	 * @return MyKitchen
	 * @see ca.ualberta.team2recipefinder.model.MyKitchen
	 */
	public static MyKitchen getMyKitchen() {
		if (myKitchen == null) {
			String path = RecipeFinderApplication.getAppContext().getFilesDir() + "/" + MY_KITCHEN_FILE;
			myKitchen = new MyKitchen(path);
		}
		return myKitchen;
    }
			
	// Singleton
	transient private static RemoteRecipes server = null;

	/**
	 * Returns the singleton of the server
	 * 
	 * @return RemoteRecipes
	 * @see ca.ualberta.team2recipefinder.model.RemoteRecipes
	 */
	public static RemoteRecipes getServer() {
		if (server == null) {
			String path = RecipeFinderApplication.getAppContext().getFilesDir() + "/" + USER_ID_FILE;
			server = new RemoteRecipes(path);
		}
		return server;
    }
	
	
    transient private static Context context = null;
    
	/**
	 * Saves the app context
	 */
    @Override
    public void onCreate(){
        super.onCreate();
        context = super.getApplicationContext();
    }

	/**
	 * Returns the application context
	 * 
	 * @return the application context
	 */
    public static Context getAppContext() {
        return context;
    }
}