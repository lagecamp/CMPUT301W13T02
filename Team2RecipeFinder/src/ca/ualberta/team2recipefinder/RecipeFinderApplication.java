package ca.ualberta.team2recipefinder;

import android.app.Application;
import android.content.Context;

// Application, where the important singletons are (as suggested by professor Abram Hindle)
public class RecipeFinderApplication extends Application {
	// Singleton
	transient private static Controller controller = null;

	public static Controller getController() {
		if (controller == null) {
			controller = new Controller(getModel(), getMyKitchen());
		}
		return controller;
    }
	
	// Singleton
	transient private static RecipeModel model = null;

	public static RecipeModel getModel() {
		if (model == null) {
			model = new RecipeModel();
		}
		return model;
    }
	
	// Singleton
	transient private static MyKitchen myKitchen = null;

	public static MyKitchen getMyKitchen() {
		if (myKitchen == null) {
			myKitchen = new MyKitchen();
		}
		return myKitchen;
    }
	
    transient private static Context context = null;
    
    @Override
    public void onCreate(){
        super.onCreate();
        context = super.getApplicationContext();
    }

    // returns application context
    public static Context getAppContext() {
        return context;
    }
}