/* RecipeFinderApplication
 * 
 * Last Edited: March 7, 2013
 * 
 * As suggested by prof. Hindle
 * 
 */


package ca.ualberta.team2recipefinder;

import android.app.Application;
import android.content.Context;

/**
 * Application, where the singletons are, as suggested by prof Hindle
 * 
 * @author cmput-301 team 2
 * @see ca.ualberta.team2recipefinder.RecipeModel
 * @see ca.ualberta.team2recipefinder.MyKitchen
 * @see ca.ualberta.team2recipefinder.Controller
 */
public class RecipeFinderApplication extends Application {
	// Singleton
	transient private static Controller controller = null;

	/**
	 * Returns the singleton of the Controller
	 * 
	 * @return the Controller
	 * @see ca.ualberta.team2recipefinder.Controller
	 */
	public static Controller getController() {
		if (controller == null) {
			controller = new Controller(getModel(), getMyKitchen());
		}
		return controller;
    }
	
	// Singleton
	transient private static RecipeModel model = null;

	/**
	 * Returns the singleton of the Model
	 * 
	 * @return the RecipeModel
	 * @see ca.ualberta.team2recipefinder.RecipeModel
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
	 * @see ca.ualberta.team2recipefinder.MyKitchen
	 */
	public static MyKitchen getMyKitchen() {
		if (myKitchen == null) {
			myKitchen = new MyKitchen();
		}
		return myKitchen;
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