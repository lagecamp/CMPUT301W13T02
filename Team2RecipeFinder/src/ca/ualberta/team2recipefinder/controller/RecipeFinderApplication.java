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
			model = new RecipeModel();
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
			myKitchen = new MyKitchen();
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
			server = new RemoteRecipes();
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